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
    _listOfTasks = new ArrayList<Task>();
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
    for ( int i = 0; i < _listOfTasks.size(); i++ ) {
      if ( _listOfTasks.get( i ).getName() == taskName ) {
        return _listOfTasks.get( i );
      }
    }
    throw new TaskNotFoundException( "Task not found." );
  }

  public boolean editTask( String taskName ) {
    return false;
  }

  public boolean deleteTask( String taskName ) {
    return _listOfTasks.remove( getTask( taskName ) );
  }

  public ArrayList<Task> getAllTasks() {
    return _listOfTasks;
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
    for ( int date : _listOfTasks ) {
      Collections.sort(_listOfTasks.get( date ));
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