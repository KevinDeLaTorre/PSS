import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is in charge of generating the reports or schedule tables of the tasks.
 */
public class Report {
    private ArrayList<Task> _listOfTasks;

    /**
     * Report Constructor 
     * @param listOfTasks
     */
    public Report(ArrayList<Task> listOfTasks) {
        _listOfTasks = (ArrayList<Task>) listOfTasks.clone();
    }

    /**
     * Generate a schedulem that includes all the tasks
     * @return Returns a string of table of the full schedule
     */
    public String generateFullSchedule() {
        String schedule = String.format("%-12s%-30s%-7s%-11s%-12s%-11s\n", "Date", "Name", "Time", "Duration", "End Date", "Frequency");
        Iterator<Task> iterator = _listOfTasks.iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();
            String date = getDateFormat(task.getStartDate());
            String name = task.getName();
            double time = task.getStartTime();
            double duration = task.getDuration();
            if (task.isRecurringTask()) {
                String endDate = getDateFormat(((RecurringTask) task).getEndDate());
                int freq = ((RecurringTask) task).getFrequency();
                schedule += String.format("%-12s%-30s%-7.2f%-11.2f%-12s%-11d\n", date, name, time, duration, endDate, freq);
            }
            else {
                schedule += String.format("%-12s%-30s%-7.2f%-11.2f%-12s%-11s\n", date, name, time, duration, "---", "---");
            }
        }
        return schedule;
    }

    /**
     * Generate a partial schedule that includes some of the tasks bound by the dates
     * @param fromDate the beginning date
     * @param toDate the ending date
     * @return Returns a string of table of the partial schedule
     */
    public String generatePartSchedule(int fromDate, int toDate) {
        String schedule = String.format("%-12s%-30s%-7s%-11s%-12s%-11s\n", "Date", "Name", "Time", "Duration", "End Date", "Frequency");
        Iterator<Task> iterator = _listOfTasks.iterator();
        
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getStartDate() >= fromDate && task.getStartDate() <= toDate) {
                String date = getDateFormat(task.getStartDate());
                String name = task.getName();
                double time = task.getStartTime();
                double duration = task.getDuration();
                if (task.isRecurringTask()) {
                    String endDate = getDateFormat(((RecurringTask) task).getEndDate());
                    int freq = ((RecurringTask) task).getFrequency();
                    schedule += String.format("%-12s%-30s%-7.2f%-11.2f%-12s%-11d\n", date, name, time, duration, endDate, freq);
                }
                else {
                    schedule += String.format("%-12s%-30s%-7.2f%-11.2f%-12s%-11s\n", date, name, time, duration, "---", "---");
                }
            }
        }
        return schedule;
    }

    /**
     * Convert and format the date from an integer to a string, format as MONTH-DAY-YEAR with leading zeros
     * @param value the integer value of the date
     * @return Returns the string and formated version of the date 
     */
    private String getDateFormat(int value) {
        String date = Integer.toString(value);
        String formattedDate = date.substring(4, 6) + "-" + date.substring(6) + "-" + date.substring(0, 4);
        return formattedDate;
    }
}
