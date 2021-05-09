
public class AntiTask extends Task{

	public AntiTask(String taskName, String type, double startTime, int startDate, double duration) throws RestrictionCheckFailedException {
		super(taskName, type, startTime, startDate, duration);
		
		if(checkRestrictions() == false) {
		      throw new RestrictionCheckFailedException( "Restriction check failed." );
		}
	}

	public boolean checkExtraRestrictions()
	{
		// TODO: check additional restrictions
		return false;
	}

	// To Do: add additional methods 
	
}