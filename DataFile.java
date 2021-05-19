import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

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
  private ArrayList<Task> _listOfTasks;

  /**
   * This constructor should be used when trying to write to a file, so give it a
   * list of tasks and interface that way.
   * @param filename
   * @param listOfTasks
   */
  public DataFile(String filename, ArrayList<Task> listOfTasks) {
    _filename = filename;
    _listOfTasks = listOfTasks;
  }

  /**
   * This constructor should be used when trying to read from a file, give it a
   * file name and it will process it into a ArrayList
   * @param filename JSON file with task data
   */
  public DataFile(String filename, boolean reading ) {
    _filename = filename;
    if ( reading ) {
      readFile();
    } else {
      _listOfTasks = new ArrayList<Task>();
      writeToFile();
    }
  }

  /**
   * Reads a JSON task file processes it into tasks, then sets this objects
   * listOfTasks to the processed tasks returns the result. 
   * @return listOfTasks Returns the processed tasks from the file.
   */
  private boolean readFile() {
    _listOfTasks = (ArrayList<Task>) jsonToTask().clone();
    return true;
  }

  /**
   * Converts the listOfTasks to JSON format to write to file.
   * @return output Returns the resulting JSON string to write to file.
   */
  @SuppressWarnings("unchecked")
  private String taskToJson() {
    JSONArray jsonArray = new JSONArray();
    Iterator<Task> iterator = _listOfTasks.iterator();

    while (iterator.hasNext()) {
      JSONObject jsonObj = new JSONObject();
      Task taskObj = iterator.next();
      jsonObj.put("Name", taskObj.getName());
      jsonObj.put("Type", taskObj.getType());

      // All of the number values are converted to String when writing onto Json file, so when reading the Json
      // file, we don't have to worry about casting different type of data
      if (taskObj.isRecurringTask()) {
        jsonObj.put("StartDate", taskObj.getStartDate());
        jsonObj.put("StartTime", taskObj.getStartTime());
        jsonObj.put("Duration", taskObj.getDuration());
        jsonObj.put("EndDate", ((RecurringTask) taskObj).getEndDate());
        jsonObj.put("Frequency", ((RecurringTask) taskObj).getFrequency());
      }
      else {
        jsonObj.put("Date", taskObj.getStartDate());
        jsonObj.put("StartTime", taskObj.getStartTime());
        jsonObj.put("Duration", taskObj.getDuration());
      }
      jsonArray.add(jsonObj);
    }
    return jsonArray.toJSONString();
  }

  /**
   * Converts json file into Tasks
   * @return output Returns a vector of Tasks
   */
  private ArrayList<Task> jsonToTask() {
    ArrayList<Task> taskList = new ArrayList<Task>();
    JSONParser parser = new JSONParser();

    try {
      JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(_filename));
      Iterator<JSONObject> iterator = jsonArray.iterator();

      while (iterator.hasNext()) {
        Task task;
        JSONObject jsonObj = iterator.next();
        String name = (String) jsonObj.get("Name");
        String type = (String) jsonObj.get("Type");
        double startTime = 0;
        try {
          startTime = (double)jsonObj.get("StartTime");
        } catch ( Exception e ) {
          startTime = Double.valueOf((long)jsonObj.get("StartTime"));
        }
        double duration = 0;
        try {
          duration = (double)jsonObj.get("Duration");
        } catch ( Exception e ) {
          duration = Double.valueOf((long)jsonObj.get("Duration"));
        }

        if (jsonObj.containsKey("Frequency")) {
          int endDate = (int)(long)jsonObj.get("EndDate");
          int frequency = (int)(long) jsonObj.get("Frequency");
          int startDate = (int)(long) jsonObj.get("StartDate");
          try {
            task = new RecurringTask(name, type, startTime, startDate, duration, endDate, frequency);
            taskList.add(task);
          } catch ( RestrictionCheckFailedException e ) {}
        }
        else {
          int date = (int)(long)jsonObj.get("Date");
          if ( type.equals( "Cancellation" ) ) {
            try {
              task = new AntiTask(name, type, startTime, date, duration );
              taskList.add(task);
            } catch ( RestrictionCheckFailedException e ) { System.out.println( "ERROR ANTI" );}
          } else {
            try {
              task = new TransientTask(name, type, startTime, date, duration);
              taskList.add(task);
            } catch ( RestrictionCheckFailedException e ) {System.out.println( "ERROR TRANSIENT" );}
          }
        }
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
    return taskList;
  }

  /**
   * Writes _listOfTasks to _filename
   * @return success Returns true if write was successful
   */
  public boolean writeToFile() {
    try (FileWriter file = new FileWriter(_filename)) {
      file.write(taskToJson());
      file.flush();
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Updates _listOfTasks with new list given
   * @param newTasks A ArrayList<Task> with new tasks to replace whats going to be
   *                 written.
   * @return success Returns true if _listOfTasks was updated.
   */
  public boolean updateTasks(ArrayList<Task> newTasks) {
    _listOfTasks = (ArrayList<Task>) newTasks.clone();
    return true;
  }

  public void setFilename( String filename ) {
    _filename = filename;
  }

  /**
   * Get the list of tasks that retrieved from the Json file 
   * @return Returns the list of tasks 
   */
  public ArrayList<Task> getTaskList() {
    return _listOfTasks;
  }
}
