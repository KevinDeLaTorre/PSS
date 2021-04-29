import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the Super class of all the task types it holds the basic information and methods that all the tasks would need to have.
 */
public class Task {
  // List of general types all tasks can do
  private final String[] typesArray = { "Class", "Study", "Sleep", "Exercise", "Work", "Meal" };
  private final List<String> types = new ArrayList<String>(Arrays.asList( typesArray ));

  private String taskName;
  private String type;
  private double startTime;
  private int startDate;
  private double duration;


  /**
   * Constructor for the Task class
   * @param taskName
   * @param type
   * @param startTime
   * @param startDate
   * @param duration
   */
  public Task( String taskName, String type, double startTime, int startDate, double duration ) {
    this.taskName = taskName;
    this.startTime = startTime;
    this.startDate = startDate;
    this.duration = duration;

    // Checks to make sure a valid type is given, makes the type "Error" if not
    if ( types.contains(type) == false ) {
      this.type = "Error";
    } else {
      this.type = type;
    }
  }

  /**
   * Goes through several different checks to make sure this task is valid for the PSS system.
   * @return valid  Returns true if the attempted task object passes all restriction checks.
   */
  public static boolean checkRestrictions() {
    // TODO: add task restriction checks in here
  }
}