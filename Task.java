/**
 * This class is the Super class of all the task types it holds the basic information and methods that all the tasks would need to have.
 */
public class Task implements Comparable {
  // List of general types all tasks can do
  private final String[] types = { "Class", "Study", "Sleep", "Exercise", "Work", "Meal" };

  private String _taskName;
  private String _type;
  private double _startTime;
  private int _startDate;
  private double _duration;


  /**
   * Constructor for the Task class
   * @param taskName
   * @param type
   * @param startTime
   * @param startDate
   * @param duration
   */
  public Task( String taskName, String type, double startTime, int startDate, double duration ) throws RestrictionCheckFailedException {
    _taskName = taskName;
    _startTime = startTime;
    _startDate = startDate;
    _duration = duration;


    if ( checkRestrictions() == false ) {
      throw new RestrictionCheckFailedException( "Restriction check failed." );
    }
  }

  /**
   * Goes through several different checks to make sure this task is valid for the PSS system.
   * @return valid  Returns true if the attempted task object passes all restriction checks.
   */
  public boolean checkRestrictions() {
    // TODO: add task restriction checks in here
    return false;
  }

  /**
   * Checks to make sure type input is a valid one.
   * @param type  Type of task to check i.e. Class, Study, Sleep...
   * @return contains Returns true if type is valid
   */
  public boolean checkType( String type ) {
    // Checks to make sure a valid type is given, makes the type "Error" if not
    boolean contains = false;
    for ( int i = 0; i < types.length; i++ ) {
      if ( type == types[i] ) {
        contains = true;
        break;
      }
    }
    return contains;
  }

  public String getName() {
    return _taskName;
  }

  public double getStartTime() {
    return _startTime;
  }

  public int getStartDate() {
    return _startDate;
  }

  public double getDuration() {
    return _duration;
  }
  
  /**
   * Compares 2 tasks to sort in list by adding the date and time together and the smaller should be first meaning it goes before the other in the schedule.
   * @param otherTask
   * @return 
   */
  @Override
  public int compareTo( Task otherTask ) {
    return Double.compare((_startDate+_startTime), (otherTask._startDate+otherTask._startTime) );
  }
}