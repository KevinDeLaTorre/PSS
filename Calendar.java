import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
/**
 * Calendar class is in charge of scheduling and managing tasks, it will also update the file after every task change.
 * Because this class has a list of all the tasks it is also in charge of interfacing with the Report class to generate reports.
 */
public class Calendar {
  private HashMap<Integer, ArrayList<Task>> _listOfTasks;
  private DataFile _file;

  public Calendar( DataFile file ) {
    _listOfTasks = new HashMap<Integer, ArrayList<Task>>();
    _file = file;
  }

  /**
   * Schedules a task into the PSS system.
   * @param newTask
   * @return result Returns whether the scheduling was a success or not.
   */
  public boolean scheduleTask( Task newTask ) {
    // Check for name uniqueness
    if ( contains( newTask.getName() ) ) {
      return false;
    }

      

    sortList();
    return true;
  }

  /**
   * Searches the list of task and returns a Task object if the given taskname is found or throws an exception if not.
   * @param taskName
   * @return Task Returns the task if found.
   * @throws TaskNotFoundException
   */
  public Task getTask( String taskName ) throws TaskNotFoundException {
    // Search list for taskname
    for ( int i = 0; i < _listOfTasks.size(); i++ ) { // Search all keys in hash table
      for ( int j = 0; j < _listOfTasks.get( i ).size(); j++ ) { // Search through arraylist of key
        if ( _listOfTasks.get( i ).get( j ).getName() == taskName ) {
          return _listOfTasks.get( i ).get( j );
        }
      }
    }
    throw new TaskNotFoundException( "Task not found." );
  }

  public boolean editTask( String taskName ) {
    return false;
  }

  /**
   * Searches listOfTasks for task and if found deletes it from list
   * @param taskName Name of task to search
   * @return Returns true/false depending on if task was successfully deleted or wasn't found.
   */
  public boolean deleteTask( String taskName ) {
    for ( int i = 0; i < _listOfTasks.size(); i++ ) { // Search all keys in hash table
      for ( int j = 0; j < _listOfTasks.get( i ).size(); j++ ) { // Search through arraylist of key
        if ( _listOfTasks.get( i ).get( j ).getName() == taskName ) {
          _listOfTasks.get( i ).remove( _listOfTasks.get( i ).get( j ) ); // If task found delete 
          return true;
        }
      }
    }
    return false; // If task not found return false
  }

  public ArrayList<Task> getAllTasks() {
    ArrayList<Task> tmpList = new ArrayList<Task>();
    for ( int i = 0; i < _listOfTasks.size(); i++ ) {
      for ( int j = 0; j < _listOfTasks.get( i ).size(); j++ ) {
        tmpList.add( _listOfTasks.get( i ).get( j ) );
      }
    }
    return tmpList;
  }

  public boolean updateFile() {
    if ( _file.updateTasks( _listOfTasks ) ) {
      return _file.writeToFile();
    } else {
      return false;
    }
  }

  public boolean writePartSchedule( String filename, int date, int frequency ) {
    DataFile tmpFile = new DataFile( filename );
    return tmpFile.writeToFile();
  }

  public String generateFullReport() {
    // TODO: implement report
    return "FULL REPORT GOES HERE";
  }

  public String generatePartReport() {
    // TODO: implement report
    return "PARTIAL REPORT GOES HERE";
  }

  private void sortList() {
    for ( int i = 0; i < _listOfTasks.size(); i++ ) {
      Collections.sort(_listOfTasks.get( i ));
    }
  }

  /**
   * Helper class that checks if the list of tasks contains a task searched by name.
   * @param name
   * @return result Returns whether the list of tasks contains the task or not.
   */
  private boolean contains( String name ) {
    try { 
      getTask( name );
      return true;
    } catch ( TaskNotFoundException e ) {
      return false;
    }
  }
}