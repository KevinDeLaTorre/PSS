/**
 * This class is in charge of reading/writing to task file including jsonifying the list of tasks or turning json into task objects.
 */
public class DataFile {
  private String _filename;
  private Vector<Task> _listOfTasks;

  /**
   * This constructor should be used when trying to write to a file, so give it a list of tasks and interface that way.
   * @param filename
   * @param listOfTasks
   */
  public DataFile( String filename, Vector<Task> listOfTasks ) {
    _filename = filename;
    _listOfTasks = listOfTasks;
  }

  /**
   * This constructor should be used when trying to read from a file, give it a file name and it will process it into a Vector
   * @param filename JSON file with task data
   */
  public DataFile( String filename ) {
    _filename = filename;
    readFile();
  }

  /**
   * Reads a JSON task file processes it into tasks, then sets this objects listOfTasks to the processed tasks returns the result.
   * @return listOfTasks  Returns the processed tasks from the file.
   */
  private Vector<Task> readFile() {

  }

  /**
   * Converts the listOfTasks to JSON format to write to file.
   * @return output Returns the resulting JSON string to write to file.
   */
  private String taskToJson() {

  }

  /**
   * Converts json file into Tasks
   * @return output Returns a vector of Tasks
   */
  private Vector<Task> jsonToTask() {

  }

  /**
   * Writes _listOfTasks to _filename
   * @return success Returns true if write was successful
   */
  public boolean writeToFile() {

  }

  /**
   * Updates _listOfTasks with new list given
   * @param newTasks A Vector<Task> with new tasks to replace whats going to be written.
   * @return success  Returns true if _listOfTasks was updated.
   */
  public boolean updateTasks( Vector<Task> newTasks ) {
    _listOfTasks = newTasks;
  }
}
