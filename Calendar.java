import java.util.Vector;
import java.util.Collections;

/**
 * Calendar class is in charge of scheduling and managing tasks, it will also update the file after every task change.
 * Because this class has a list of all the tasks it is also in charge of interfacing with the Report class to generate reports.
 */
public class Calendar {
  private Vector<Task> _listOfTasks;
  private DataFile _file;

  public Calendar( DataFile file ) {
    _listOfTasks = new Vector<Task>();
    _file = file;
  }

  public boolean scheduleTask( Task newTask ) {
    if ( contains( newTask.getName() ) ) {
      return false;
    }

    return true;
  }

  public Task getTask( String taskName ) throws TaskNotFoundException {
    for ( int i = 0; i < _listOfTasks.size(); i++ ) {
      if ( _listOfTasks.get( i ).getName() == taskName ) {
        return _listOfTasks.get( i );
      }
    }
    throw new TaskNotFoundException( "Task not found." );
  }

  public boolean editTask( String taskName ) {
  }

  public boolean deleteTask( String taskName ) {
    return _listOfTasks.remove( getTask( taskName ) );
  }

  public Vector<Task> getAllTasks() {
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

  }

  public String generateFullReport() {

  }

  public String generatePartReport() {
  }

  private boolean contains( String name ) {
    try { 
      getTask( name );
      return true;
    } catch ( TaskNotFoundException e ) {
      return false;
    }
  }
}