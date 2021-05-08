
public class RecurringTask extends Task{
	private int endDate;
	private int frequency;
	
	public RecurringTask(String taskName, String type, double startTime, int startDate, double duration, int eDate, int freq) {
		super(taskName, type, startTime, startDate, duration);
		endDate = eDate;
		frequency = freq;
	}
	
	
	// checkRestriction method
	public boolean checkRestrictions() {
	    // TODO: check restrictions for a recurring task
	    return false;
	  }

}


