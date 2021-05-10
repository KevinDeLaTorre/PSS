
public class TransientTask extends Task {
	// transient task types
	private final String[] types = {"Visit", "Shopping", "Appointment"};
	
	public TransientTask(String taskName, String type, double startTime, int startDate, double duration) throws RestrictionCheckFailedException {
		super(taskName, type, startTime, startDate, duration);
		
		if(checkRestrictions() == false) {
		      throw new RestrictionCheckFailedException( "Restriction check failed." );
		}
	}
	
	public boolean checkRestrictions()
	{
		// TODO: check additional restrictions
		return false;
	}

	// To Do: add additional methods 
}
