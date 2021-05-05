import java.util.Vector;

/**
 * Calendar class is in charge of scheduling and managing tasks, it will also update the file after every task change.
 * Because this class has a list of all the tasks it is also in charge of interfacing with the Report class to generate reports.
 */
public class Calendar {
  private Vector<Task> _listOfTasks;
  private String _filename;

  public Calendar( String filename ) {
    _listOfTasks = new Vector<Task>();
    _filename = filename;
  }

  public boolean scheduleTask( Task newTask ) {
  }

  public Task getTask( String taskName ) {
    return task;
  }

  public boolean editTask( String taskName ) {

  }

  public boolean deleteTask( String taskName ) {

  }

  public Vector<Task> getAllTasks() {
    return _listOfTasks;
  }

  public boolean updateFile() {
  }

  public boolean writePartSchedule( String filename, int date, int frequency ) {

  }

  public String generateFullReport() {

  }

  public String generatePartReport() {

  }
}
