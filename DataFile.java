import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class is in charge of reading/writing to task file including jsonifying
 * the list of tasks or turning json into task objects.
 */
public class DataFile {
  private String _filename;
  private Vector<Task> _listOfTasks;

  /**
   * This constructor should be used when trying to write to a file, so give it a
   * list of tasks and interface that way.
   * 
   * @param filename
   * @param listOfTasks
   */
  public DataFile(String filename, Vector<Task> listOfTasks) {
    _filename = filename;
    _listOfTasks = listOfTasks;
  }

  /**
   * This constructor should be used when trying to read from a file, give it a
   * file name and it will process it into a Vector
   * 
   * @param filename JSON file with task data
   */
  public DataFile(String filename) {
    _filename = filename;
    readFile();
  }

  /**
   * Reads a JSON task file processes it into tasks, then sets this objects
   * listOfTasks to the processed tasks returns the result.
   * 
   * @return listOfTasks Returns the processed tasks from the file.
   */
  private Vector<Task> readFile() {
    _listOfTasks = (Vector) jsonToTask().clone();
    return _listOfTasks;
  }

  /**
   * Converts the listOfTasks to JSON format to write to file.
   * 
   * @return output Returns the resulting JSON string to write to file.
   */
  private String taskToJson() {
    JSONArray jsonArray = new JSONArray();
    Iterator<Task> iterator = _listOfTasks.iterator();

    // TODO: Still need a way to check whether the task is recurring, transient, or
    // anti task.
    while (iterator.hasNext()) {
      JSONObject jsonObj = new JSONObject();
      Task taskObj = iterator.next();
      jsonObj.put("Name", taskObj.getName());
      // jsonObj.put("Type", taskObj.getType());
      jsonObj.put("StartTime", taskObj.getStartTime());
      jsonObj.put("StartDate", taskObj.getStartDate());
      jsonObj.put("Duration", taskObj.getDuration());
      jsonArray.add(jsonObj);
    }
    return jsonArray.toJSONString();
  }

  /**
   * Converts json file into Tasks
   * 
   * @return output Returns a vector of Tasks
   */
  private Vector<Task> jsonToTask() {
    Vector<Task> tasks = new Vector<Task>();
    JSONParser parser = new JSONParser();

    try {
      JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(_filename));
      Iterator<JSONObject> iterator = jsonArray.iterator();

      // TODO: Still need a way to check whether the task is recurring, transient, or
      // anti task.
      while (iterator.hasNext()) {
        JSONObject jsonObj = iterator.next();
        String name = (String) jsonObj.get("Name");
        String type = (String) jsonObj.get("Type");
        double startTime = (double) jsonObj.get("StartTime");
        int startDate = (int) jsonObj.get("StartDate");
        double duration = (double) jsonObj.get("Duration");
        Task t = new Task(name, type, startTime, startDate, duration);
        tasks.add(t);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tasks;
  }

  /**
   * Writes _listOfTasks to _filename
   * 
   * @return success Returns true if write was successful
   */
  public boolean writeToFile() {
    try (FileWriter file = new FileWriter(_filename)) {
      file.write(taskToJson());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Updates _listOfTasks with new list given
   * 
   * @param newTasks A Vector<Task> with new tasks to replace whats going to be
   *                 written.
   * @return success Returns true if _listOfTasks was updated.
   */
  public boolean updateTasks(Vector<Task> newTasks) {
    _listOfTasks = newTasks;
  }
}
