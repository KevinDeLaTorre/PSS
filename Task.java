/**
 * This class is the Super class of all the task types it holds the basic information and methods that all the tasks would need to have.
 */
public class Task {
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
    this.type = type;
    this.startTime = startTime;
    this.startDate = startDate;
    this.duration = duration;
  }

  /**
   * Goes through several different checks to make sure this task is valid for the PSS system.
   * @return valid  Returns true if the attempted task object passes all restriction checks.
   */
  public static boolean checkRestrictions() {
    // TODO: add task restriction checks in here
  }
}