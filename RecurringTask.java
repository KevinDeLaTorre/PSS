import java.util.ArrayList;
public class RecurringTask extends Task{
	
	// resurring task types
	private final String[] typesArray = { "Class", "Study", "Sleep", "Exercise", "Work", "Meal" };
	private final String TASKTYPE = "RECURRING";
	
	private int _endDate;
	private int _frequency;
  private ArrayList<String> types = new ArrayList<String>();

	public RecurringTask(String taskName, String type, double startTime, int startDate, double duration, int eDate, int freq) throws RestrictionCheckFailedException {
		super(taskName, type, startTime, startDate, duration);

		for ( String t : typesArray ) {
			types.add( t );
		}
		boolean valid = checkRestrictions(super.getStartDate(), eDate, freq); // check date and frequency
		boolean recurringType = checkThisType(type); // check task type is recurring
		
		if((valid == false) && (recurringType == false)) {
			throw new RestrictionCheckFailedException("Restriction check failed.");
		}
		else {
			_endDate = eDate;
			_frequency = freq;
		}
	}
	
	
	/**
	   * Checks if the frequency is valid and the end date is different from the start date. 
	   * @return valid  Returns true if the attempted task object passes all restriction checks.
	   */
	public boolean checkRestrictions(int sDate, int eDate, int freq) {
		
		
	    if(freq==1 && freq==7) {
	    	
	    	// end date is different 
	    	if (eDate != sDate) {
	    		
	    		// convert integer to string to validate yyyy, mm, and dd 
	    		String d = String.valueOf(eDate);
	    		int[] days_in_month = {31,28,31,30,31,30,31,31,30,31,30,31}; 
	    		
	    		// if the length of the date is 8
	    		if(d.length()==8){
	    			  // extract year, month, and day for endDate
	    			  int year = Integer.parseInt(d.substring(0,4)); 
	    			  int month = Integer.parseInt(d.substring(4,6));  
	    			  int day = Integer.parseInt(d.substring(6,8));
	    			  
	    			  int numDays = days_in_month[month-1]; //find the total days in the given month 
	    			  
	    			  // check endDate is valid (not the same as the startDate)
	    			  if(day > 0 && day <= numDays) {
	    				  String sd = String.valueOf(sDate);
	    				  int m = Integer.parseInt(sd.substring(4,6));
	    				  int startDay = Integer.parseInt(sd.substring(6,8));
	    				  int y = Integer.parseInt(sd.substring(0,4));
	    				  if(month > m || day > startDay || year > y) {
	    					  return true;
	    				  }
	    			  }
	    		}
	    	}
	    }
	    	
	    return false;
	}
	
/**
	 * Checks to make sure type input is a valid one.
	 * @param type  Type of task to check i.e. Class, Study, Sleep...
	 * @return contains Returns true if type is valid
	 */
	public boolean checkThisType( String type ) {
		// Checks to make sure a valid type is given, makes the type "Error" if not
		return types.contains( type );
	}

	public int getEndDate() {
		return _endDate;
	}

	public int getFrequency(){
		return _frequency;
	}

	/**
    * Checks to make sure this task is a recurring task.
    * @return Returns true
    */
	public boolean isRecurringTask() {
		return true;
	}
}


