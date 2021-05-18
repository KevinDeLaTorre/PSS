
public class TransientTask extends Task {
	// transient task types
	private final String[] types = {"Visit", "Shopping", "Appointment"};
	private final String TASKTYPE = "TRANSIENT";
	
	public TransientTask(String taskName, String type, double startTime, int startDate, double duration) throws RestrictionCheckFailedException {
		super(taskName, type, startTime, startDate, duration);
		
		if(checkRestrictions( type ) == false) {
		      throw new RestrictionCheckFailedException( "Restriction check failed." );
		}
	}
	
	/**
	 * Checks if the input value (task type) is valid
	 * @param type the type of task
	 * @return true if the type is transient
	 */
	public boolean checkRestrictions(String type)
	{
		boolean transientType = checkThisType(type); // check whether the taskType is transient
		if (transientType == true)
			return true; 
		return false;
	}
	
	/**
	   * Checks to make sure type input is a valid one.
	   * @param type  Type of task to check i.e. Class, Study, Sleep...
	   * @return contains Returns true if type is valid
	   */
	  public boolean checkThisType( String type ) {
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
