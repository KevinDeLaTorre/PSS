
public class AntiTask extends Task {
	private final String[] types = {"Cancellation"}; // only task type
	
	public AntiTask(String taskName, String type, double startTime, int startDate, double duration) throws RestrictionCheckFailedException {
		super(taskName, type, startTime, startDate, duration);
		
		if(checkRestrictions(super.getType()) == false) {
		      throw new RestrictionCheckFailedException( "Restriction check failed." );
		}
	}

	/**
	 * Checks if the inputs are valid
	 * @param type the type of task
	 * @return true if the task is anti-task
	 */
	public boolean checkRestrictions(String type)
	{
		boolean antiType = checkType(type); 
		if (antiType == true)
			return true; 
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
	
}
