/**
* This class is the Super class of all the task types it holds the basic information and methods that all the tasks would need to have.
*/
public class Task implements Comparable<Task> {
  
  // List of general types of task
  private final String[] types = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal", "Visit", "Shopping", "Appointment", "Cancellation"};

  private String _taskName;
  private String _type;
  private double _startTime;
  private int _startDate;
  private double _duration;


  /**
   * Constructor for the Task class
   * @param task Name the name of task
   * @param type task type (recurring, transient, or anti)
   * @param startTime start time of the task
   * @param startDate date when the task starts
   * @param duration the duration of task
   */
  public Task( String taskName, String type, double startTime, int startDate, double duration ) throws RestrictionCheckFailedException {
	  
	  boolean result, typeChecked, validateDate;
		result = checkRestrictions(taskName, startTime, duration, startDate); 
		typeChecked = checkType(type); 
		validateDate = checkDate(startDate);
		
		if((result == false) && (typeChecked == false) && (validateDate == false)) {
			throw new RestrictionCheckFailedException( "Restrictions check failed." );
		}
		else {
			_taskName = taskName;
			_type = type;
			_startTime = startTime;
			_startDate = startDate;
			_duration = duration;
		}

  }

  /**
   * Check whether the input values are valid or invalid
   * @param taskName name of task
   * @param startTime start time of task
   * @param duration duration of task
   * @param startDate starting date
   * @return true if the given values are valid, else return false
   */
  public boolean checkRestrictions(String taskName, double startTime, double duration, int startDate) {
	    
		// validate startTime 
		if(startTime >= 0 && startTime <= 23.75 && (startTime % 0.25) == 0) {
			
			// validate duration
			if(duration >= 0.25 && duration <= 23.75 && (duration % 0.25) == 0) {
				return true;
			}
		}
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
  
  /**
   * Checks if the date has the valid form (YYYYMMDD)
   * @param date
   * @return true if the date is correct
   */
  public boolean checkDate(int date) {
	  String d = String.valueOf(date);
		int[] days_in_month = {31,28,31,30,31,30,31,31,30,31,30,31};
		if(d.length()==8){
			  int month = Integer.parseInt(d.substring(4,6)); 
			  int day = Integer.parseInt(d.substring(6,8));
			  int numDays = days_in_month[month-1]; 
			  if(day > 0 && day <= numDays) {
				  return true;
			  }
		}
		return false;
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
   * Compares 2 tasks to sort in list by adding the date and time together and the smaller should be first meaning it goes before the other in the schedule.
   * @param otherTask
   * @return 
   */
  public int compareTo( Task otherTask ) {
    return Double.compare((_startDate+_startTime), (otherTask._startDate+otherTask._startTime) );
  }

  /**
   * Checks to make sure this task is a recurring task.
   * @return Returns false
   */
  public boolean isRecurringTask() {
    return false;
  }
}