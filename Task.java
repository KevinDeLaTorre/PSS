/**
 * This class is the Super class of all the task types it holds the basic information and methods that all the tasks would need to have.
 */
public class Task {
  
  // List of general types of task
  private final String[] types = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal", "Visit", "Shopping", "Appointment"};

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
	  
	boolean result = checkRestrictions(taskName, startTime, duration, startDate); 
	if(result == false) {
		throw new RestrictionCheckFailedException( "Restriction check failed." );
	}
	else {
		_taskName = taskName;
		_startTime = startTime;
		_startDate = startDate;
		_duration = duration;
	}

  }

  /**
   * Goes through several different checks to make sure this task is valid for the PSS system.
   * @return valid  Returns true if the attempted task object passes all restriction checks.
   */
  public boolean checkRestrictions(String taskName, double startTime, double duration, int startDate) {
	    // TODO: add task restriction checks if taskName is valid
		  
		// validate startTime 
		if(startTime >= 0 && startTime <= 23.75) {
			if((startTime % 0.25) == 0) {
				return true;
			}
		}
		else 
			return false;
		
		
		// validate duration
		if(duration >= 0.25 && duration <= 23.75) {
			if((duration % 0.25) == 0) {
				return true;
			}
		}
		else
			return false;
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

  public String getType() {
    return _type;
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
   * Checks to make sure this task is a recurring task.
   * @return Returns false
   */
  public boolean isRecurringTask() {
    return false;
  }
}