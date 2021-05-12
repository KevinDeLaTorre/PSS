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
    scheduleBulkTasks( file.getTaskList() );
  }

  /**
   * Schedules a task into the PSS system.
   * @param newTask
   * @return result Returns whether the scheduling was a success or not.
   */
  public boolean scheduleTask( Task newTask ) {
    // Check for name uniqueness
    try {
      getTask( newTask.getName() ); 
      return false; // If getTask succeeds and doesn't throw TaskNotFoundException then a task exists with that name
    } catch ( TaskNotFoundException e ) {}

    // If first time adding to date, create key in table
    if ( _keys.contains( newTask.getStartDate() ) == false ) { 
      _listOfTasks.put( newTask.getStartDate(), new ArrayList<Task>() );
      _keys = _listOfTasks.keySet(); // Update keys
    }

    // TODO: Check if time overlap
    

    _listOfTasks.get( newTask.getStartDate() ).add( newTask ); // Add task to listOfTasks
    Collections.sort(_listOfTasks.get( newTask.getStartDate())); // Sort arraylist by date/time after adding new task in
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
          if ( _listOfTasks.get( key ).get( j ).getName() == taskName ) { // If taskname is found return task
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
  public boolean editTask( Task updatedTask ) {
    deleteTask( updatedTask.getName() );
    return scheduleTask( updatedTask );
  }

  /**
   * Searches listOfTasks for task and if found deletes it from list
   * @param taskName Name of task to search
   * @return Returns true/false depending on if task was successfully deleted or wasn't found.
   */
  public boolean deleteTask( String taskName ) {
    for ( int key : _keys ) { // Search all keys in listOfTasks
      for ( int j = 0; j < _listOfTasks.get( key ).size(); j++ ) { // Search through arraylist of key
        if ( _listOfTasks.get( key ).get( j ).getName() == taskName ) {
          _listOfTasks.get( key ).remove( _listOfTasks.get( key ).get( j ) ); // If task found delete 
          return true;
        }
      }
    }
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

  private void sortList() {
    for ( int i = 0; i < _listOfTasks.size(); i++ ) {
      Collections.sort(_listOfTasks.get( i ));
    }
  }
}