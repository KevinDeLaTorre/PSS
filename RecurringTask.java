
public class RecurringTask extends Task{
	private int startDate;
	private int endDate;
	private int frequency;
	
	public RecurringTask(String taskName, String type, double startTime, int startDate, double duration, int sDate, int eDate, int freq) {
		super(taskName, type, startTime, startDate, duration);
		this.startDate = sDate;
		endDate = eDate;
		frequency = freq;
	}

}


