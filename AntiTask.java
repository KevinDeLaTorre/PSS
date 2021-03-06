
public class AntiTask extends Task {
	private final String[] types = {"Cancellation"}; // only task type
	private final String TASKTYPE = "ANTI";
	
	public AntiTask(String taskName, String type, double startTime, int startDate, double duration) throws RestrictionCheckFailedException {
		super(taskName, type, startTime, startDate, duration);
		
		if(checkRestrictions( type ) == false) {
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
		boolean antiType = checkThisType(type); 
		if (antiType == true)
			return true; 
		System.out.println( "Failed at anti check restrictions" );
		return false;
	}
	
	/**
	   * Checks to make sure type input is a valid one.
	   * @param type  Type of task to check i.e. Class, Study, Sleep...
	   * @return contains Returns true if type is valid
	   */
	  public boolean checkThisType( String type ) {
	    // Checks to make sure a valid type is given, makes the type "Error" if not
			return ( type.equals( "Cancellation"));
	  }
	
		public AntiTask clone() {
			AntiTask tmp = new AntiTask(getName(), getType(), getStartTime(), getStartDate(), getDuration() );
			return tmp;
		}
}
