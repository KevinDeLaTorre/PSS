import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;

/**
 * Calendar class is in charge of scheduling and managing tasks, it will also update the file after every task change.
 * Because this class has a list of all the tasks it is also in charge of interfacing with the Report class to generate reports.
 */
public class Calendar {
  private HashMap<Integer, ArrayList<Task>> _listOfTasks;
  private DataFile _file;
  private Set<Integer> _keys;

  /**
   * Constructor for Calendar
   * @param file DataFile that we will be writing to.
   */
  public Calendar( DataFile file ) {
    _listOfTasks = new HashMap<Integer, ArrayList<Task>>();
    _file = file;
    _keys = _listOfTasks.keySet();
    scheduleBulkTasks( _file.getTaskList() );
  }

  /**
   * Schedules a task into the PSS system.
   * @param newTask
   * @return result Returns whether the scheduling was a success or not.
   */
  public boolean scheduleTask( Task newTask ) {
    // Check for name uniqueness
    if ( !newTask.isRecurringTask() ) {
      try {
        getTask( newTask.getName() ); 
        return false; // If getTask succeeds and doesn't throw TaskNotFoundException then a task exists with that name
      } catch ( TaskNotFoundException e ) {}
    }

    // Check for time overlap
    if ( checkConflict( newTask ) ) {
      return false; // If conflict is found return false
    }

    // If first time adding to date, create key in table
    if ( _keys.contains( newTask.getStartDate() ) == false ) { 
      _listOfTasks.put( newTask.getStartDate(), new ArrayList<Task>() );
      _keys = _listOfTasks.keySet(); // Update keys
    }

    _listOfTasks.get( newTask.getStartDate() ).add( newTask ); // Add task to listOfTasks
    Collections.sort(_listOfTasks.get( newTask.getStartDate())); // Sort arraylist by date/time after adding new task in

    if ( newTask.isRecurringTask() ) {
      RecurringTask tmpTask = (RecurringTask) newTask;
      int nextday = getDayAfterDuration( tmpTask.getStartDate(), tmpTask.getFrequency() );
      if ( nextday <= tmpTask.getEndDate() ) { // If next recurring task day is valid schedule the next one
        RecurringTask nextTask = new RecurringTask(
          tmpTask.getName(), 
          tmpTask.getType(), 
          tmpTask.getStartTime(), 
          nextday, 
          tmpTask.getDuration(), 
          tmpTask.getEndDate(), 
          tmpTask.getFrequency());
        scheduleTask( nextTask );
      }
    }
    updateFile();
    return true;
  }

  /**
   * Checks if there's a conflict with given task.
   * @param task Task to check
   * @return Returns true if there is a conflict and false if there isn't
   */
  private boolean checkConflict( Task task ) {
    int key = task.getStartDate();
    if ( _keys.contains( key ) ) {
      double taskStart = task.getStartTime();
      double taskEnd = ( taskStart + task.getDuration() );
      for ( int i = 0; i < _listOfTasks.get( key ).size(); i++ ) {
        Task tmpTask = _listOfTasks.get( key ).get( i );
        double tmpTaskStart = tmpTask.getStartTime();
        double tmpTaskEnd = ( tmpTaskStart + tmpTask.getDuration() );
        // Check if newtask startTime is within timeslot of other task
        if ( taskStart >= tmpTaskStart && taskStart < tmpTaskEnd ) {
          return handleConflict( tmpTask, task );
        } else if ( taskEnd >= tmpTaskStart && taskEnd <= tmpTaskEnd ) { // Check if newtask endTime is within timeslot of other task
          return handleConflict( tmpTask, task );
        } else if ( taskStart < tmpTaskStart && taskEnd > tmpTaskEnd ) { // Check if other task is within newtask timeslot
          return handleConflict( tmpTask, task );
        }
      }
    }
    return false;
  }

  private boolean handleConflict( Task one, Task two ) {
    // If a recurring task conflicts with an antitask, delete that single recurring task and put in the antitask
    // RECURRING x ANTI
    if ( one.isRecurringTask() && two.getType().equals( "Cancellation" ) ){ 
      deleteTaskOnDate( one.getName(), one.getStartDate() );
      return false;
    // If Anti task conflicts with another anti task, report confliction
    // ANTI x ANTI
    } else if ( one.getType().equals( "Cancellation" ) && two.getType().equals( "Cancellation" ) ){
      return false;
    // If an antitask conflicts with other non-recurring task then that means other task is transient so allow it
    // ANTI x TASK
    } else if ( one.getType().equals( "Cancellation" ) && !two.isRecurringTask() ) { 
      return false;
    // TASK x RECURRING
    } else if ( !one.isRecurringTask() && two.isRecurringTask() ) {
      return true;
    }
    // Anything else is a genuine conflict so report it
    return true;
  }

  /**
   * Allows scheduling of multiple task when given an arraylist of tasks, if there's a task conflict it will still schedule the rest of the tasks.
   * @param tasks ArrayList containing tasks to schedule.
   * @return success Returns true if all tasks schedules successfully and false if at least one failed.
   */
  public boolean scheduleBulkTasks( ArrayList<Task> tasks ) {
    boolean success = true;
    for ( Task newTask : tasks ) {
      if ( scheduleTask( newTask ) == false ) {
        success = false;
      }
    }
    return success;
  }

  /**
   * Searches the list of task and returns a Task object if the given taskname is found or throws an exception if not.
   * @param taskName
   * @return Task Returns the task if found.
   * @throws TaskNotFoundException
   */
  public Task getTask( String taskName ) throws TaskNotFoundException {
    if ( taskName == null ) {
      System.out.println( "Name null " );
    }
    // Search list for taskname
    if ( _listOfTasks.size() != 0 ) {
      for ( int key : _keys ) { // Search all keys in hash table
        for ( int j = 0; j < _listOfTasks.get( key ).size(); j++ ) { // Search through arraylist of key
          if ( _listOfTasks.get( key ).get( j ).getName().equals( taskName ) ) { // If taskname is found return task
            return _listOfTasks.get( key ).get( j );
          }
        }
      }
    }
    throw new TaskNotFoundException( "Task not found." );
  }

  /**
   * Give this method a task with the same name as one already in the system and it'll replace it with the updated one.
   * @param updatedTask Updated task of the one you want to edit, has to have same name of task in listOfTasks.
   * @return success Returns true if scheduled task successfully
   */
  public boolean editTask( String oldTaskName, Task updatedTask ) {
    try {
      Task tmp = getTask( oldTaskName ).clone();
      deleteTask( oldTaskName, true );
      if ( !scheduleTask( updatedTask ) ) {
        scheduleTask( tmp );
        return false;
      } else {
        return true;
      }
    } catch ( TaskNotFoundException e ) {
      return false;
    }
  }

  /**
   * Searches listOfTasks for task and if found deletes it from list
   * @param taskName Name of task to search
   * @param singleRecurring If true delete all recurring tasks of given name, if false just first encountered.
   * @return Returns true/false depending on if task was successfully deleted or wasn't found.
   */
  public boolean deleteTask( String taskName, boolean singleRecurring ) {
    for ( int key : _keys ) { // Search all keys in listOfTasks
      for ( int j = 0; j < _listOfTasks.get( key ).size(); j++ ) { // Search through arraylist of key
        if ( _listOfTasks.get( key ).get( j ).getName().equals( taskName ) ) {
          boolean isRecurringTask = _listOfTasks.get( key ).get( j ).isRecurringTask();
          _listOfTasks.get( key ).remove( _listOfTasks.get( key ).get( j ) ); // If task found delete 
          if ( isRecurringTask && !singleRecurring ) {
            deleteTask( taskName, false );
          }
          return true;
        }
      }
    }
    updateFile();
    return false; // If task not found return false
  }

  public boolean deleteTaskOnDate( String taskName, int date ) {
    int key = date;
    for ( int j = 0; j < _listOfTasks.get( key ).size(); j++ ) { // Search through arraylist of key
      if ( _listOfTasks.get( key ).get( j ).getName().equals( taskName ) ) {
        _listOfTasks.get( key ).remove( _listOfTasks.get( key ).get( j ) ); // If task found delete 
        return true;
      }
    }
    updateFile();
    return false; // If task not found return false
  }

  /**
   * Returns all tasks in the current list of tasks
   * @return Returns an ArrayList containing all the tasks we have currently.
   */
  public ArrayList<Task> getAllTasks() {
    ArrayList<Task> tmpList = new ArrayList<Task>();
    for ( int key : _keys ) { // Go through entire hashtable and get an arraylist of all tasks
      for ( int j = 0; j < _listOfTasks.get( key ).size(); j++ ) {
        tmpList.add( _listOfTasks.get( key ).get( j ) );
      }
    }
    Collections.sort( tmpList ); // Sorts all the arraylists by date/time
    return tmpList;
  }

  /**
   * Returns a partial list of the whole list.
   * @param startDate Initial start date
   * @param duration  Length in days of the partial list to get.
   * @return Returns an ArrayList of the partial list.
   */
  public ArrayList<Task> getPartTasks( int startDate, int duration ) {
    ArrayList<Task> tmpList = new ArrayList<Task>();
    int date = startDate;
    for ( int i = 0; i < duration; i++ ) {
      if ( _keys.contains( date ) ) { // If date in listOfTasks get all tasks from that date
        for ( int j = 0; j < _listOfTasks.get( date ).size(); j++ ) { // Search through all of arraylist of given date
          tmpList.add( _listOfTasks.get( date ).get( j ) );
        }
      }
      date = getNextDay( date );
    }

    return tmpList;
  }

  public Task getTaskFromDate( String taskName, int date ) throws TaskNotFoundException{
    if ( !_keys.contains( date ) ) {
      throw new TaskNotFoundException( "Date doesn't exist." );
    }
    ArrayList<Task> tmpList = getPartTasks( date, 1 );
    for ( int i = 0; i < tmpList.size(); i++ ) {
      if ( tmpList.get( i ).getName().equals( taskName ) ) {
        return tmpList.get( i );
      }
    }
    throw new TaskNotFoundException( "Task not found. " );
  }

  /**
   * Updates the json file with current list of tasks
   * @return Returns boolean if it was successful or not.
   */
  public boolean updateFile() {
    if ( _file.updateTasks( getAllTasks() ) ) {
      return _file.writeToFile();
    } else {
      return false;
    }
  }

  public boolean writePartSchedule( String filename, int date, int duration ) {
    ArrayList<Task> tasks = getPartTasks( date, duration );
    DataFile tmpFile = new DataFile( filename, tasks );
    return tmpFile.writeToFile();
  }

  /**
   * Generate a full report with all of the tasks
   * @return Returns a string of table of the full schedule
   */
  public String generateReport() {
    Report report = new Report(getAllTasks());
    return report.generateFullSchedule();
  }

  /**
   * Generate a partial report with some of the tasks that bound by two different dates or a specific date
   * @param fromDate The beginning date
   * @param toDate The ending date
   * @return Returns a string of table of the partial schedule
   */
  public String generatePartReport(int fromDate, int toDate) {
    Report report = new Report(getAllTasks());
    return report.generatePartSchedule(fromDate, toDate);
  }

  public String generateSingleTaskReport( Task task ) {
    ArrayList<Task> tmp = new ArrayList<Task>();
    tmp.add( task );
    Report report = new Report( tmp );
    return report.generateFullSchedule();
  }

  /**
   * Returns next valid date when given an integer date
   * @param date Original date in integer YYYYMMDD format
   * @return Returns the next valid day after the given date.
   */
  private int getNextDay( int date ) {
    String tmpString = String.valueOf( date ); // Date should be in format YYYYMMDD
    int year = Integer.parseInt(tmpString.substring(0, 4)); // YYYY
    int month = Integer.parseInt(tmpString.substring(4, 6)); // MM
    int day = Integer.parseInt(tmpString.substring(6, 8)); // DD
    
    tmpString = "";
    boolean changeMonth = false;

    if ( month == 12 && day == 31 ) { // In case of a new years switch reset month and day to 1 and increase year by 1
      tmpString += String.valueOf(year+1) + "0101";
      return Integer.parseInt( tmpString );
    } else if ( month == 2 && day == 28 ) { // If february and last day change month at 28
      changeMonth = true;
    } else if ( day == 30 && ( month == 4 || month == 6 || month == 9 || month == 11 ) ) { // If in last day of 30 day month change month
      changeMonth = true;
    } else if ( day == 31 ) { // if any other month and current day is 31
      changeMonth = true;
    }

    if ( changeMonth ) { // If there is a month change, increase month by 1 and reset day to 1
      month += 1;
      day = 1;
    } else {
      day += 1; // If there isn't a month change then just increase day by 1 and return.
    }

    String tmpMonth = "";
    if ( month < 10 ) { // If month is below 10, in order to fit the YYYYMMDD format we need a 0 in front of the integer
      tmpMonth = "0" + String.valueOf( month );
    } else {
      tmpMonth = String.valueOf( month );
    }

    String tmpDay = "";
    if ( day < 10 ) { // If day is below 10, in order to fit the YYYYMMDD format we need a 0 in front of the integer

      tmpDay = "0" + String.valueOf( day );
    } else {
      tmpDay = String.valueOf( day );
    }

    tmpString += String.valueOf(year) + tmpMonth + tmpDay;

    return Integer.parseInt( tmpString );
  }

  /**
   * Returns a valid date after a given duration has passed
   * @param date      Starting date
   * @param duration   Duration in days
   * @return          Returns the date after the given duration in days
   */
  public int getDayAfterDuration( int date, int duration ) {
    int tmpDate = date;

    for ( int i = 0; i < duration; i++ ) {
      tmpDate = getNextDay( tmpDate );
    }

    return tmpDate;
  }

  /**
   * Sorts the hashtable
   */
  private void sortList() {
    for ( int i = 0; i < _listOfTasks.size(); i++ ) {
      Collections.sort(_listOfTasks.get( i ));
    }
  }
}