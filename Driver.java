import java.util.Scanner;
import java.io.File;

public class Driver {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String username = "";
        char answer = 'y';
        int choice = 0;
        String taskName;
        String type;
        double startTime;
        int startDate;
        int endDate;
        int frequency;
        double duration;
        
        // checking if the user name exists

        /*
        while(true) {
            
            System.out.print("Enter your user name: ");
            username = scan.nextLine();


            User user = new User(username + ".json");
            // if username is not in the file
            // ask if you want to add the username 
            // to the file, otherwise exit

            if (username != User.getFileName()) { //== username) {
                System.out.println("The user name you entered is not in the system.");
                System.out.print("Do you want to add \"" + username + "\" to your file? (y / n) ");
                answer = scan.next().charAt(0);

                if(answer == 'y') {
                    // add to the file
                    
                    Datafile newFile = new DataFile(username, true);
                    Calendar newCalender = new Calendar(newFile);
                    break;
                } 

                
            }
            Datafile newFile = new DataFile(username, true);
            Calendar newCalender = new Calendar(newFile);
        }
        */
        
        System.out.print("Enter your user name: ");
        username = scan.nextLine();
        String filename = username + ".json";
        File file = new File( filename );
        DataFile user;
        if ( file.exists() == false ) { // Check if file exists
            user = new DataFile( filename, false ); // if it doesn't create a blank file using username
        } else {
            user = new DataFile( filename, true ); // if it does exist read the file.
        }
        Calendar schedule = new Calendar(user);

        while(answer == 'y') {
            System.out.println("Menu");
            System.out.println("1. Schedule task");
            System.out.println("2. Edit task");
            System.out.println("3. Delete task");
            System.out.println("4. Generate full report");

            System.out.print("Enter your choice: (1-4) ");
            choice = scan.nextInt();

            if ( choice != 4 ) { // If generating report don't need to ask about kind of task
                System.out.println("What kind of task are you using?");
                System.out.println("a. Task");
                System.out.println("b. Recurring task");
                System.out.println("c. Transient task");
                System.out.println("d. Anti task");

                System.out.println("Enter your choice: (a-d) ");
                answer = scan.next().charAt(0);
                scan.nextLine();
            }

            switch(choice) {
                case 1:
                    System.out.println("Schedule a task");
                    switch(answer) {
                        case 'a':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            Task t = new Task(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(t);

                            break;

                        case 'b':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = " Recurring Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the end date? ");
                            endDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            System.out.print("Enter the frequency: ");
                            frequency = scan.nextInt();
                            RecurringTask r = new RecurringTask(taskName, type, startTime, startDate, duration, endDate, frequency);
                            schedule.scheduleTask(r);

                            break;
                        case 'c':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Shopping";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            TransientTask tr = new TransientTask(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(tr);

                            break;
                        case 'd':
                             System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Anti Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            AntiTask a = new AntiTask(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(a);

                            break;
                    }

                    break;
                case 2:
                    System.out.println("Edit a task");
                    switch(answer) {
                        case 'a':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            Task t = new Task(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(t);

                            break;

                        case 'b':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = " Recurring Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the end date? ");
                            endDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            System.out.print("Enter the frequency: ");
                            frequency = scan.nextInt();
                            RecurringTask r = new RecurringTask(taskName, type, startTime, startDate, duration, endDate, frequency);
                            schedule.scheduleTask(r);

                            break;
                        case 'c':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Transient Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            TransientTask tr = new TransientTask(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(tr);

                            break;
                        case 'd':
                             System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Anti Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            AntiTask a = new AntiTask(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(a);

                            break;
                    }

                    break;

                case 3:
                    System.out.println("Delete a task");
                    switch(answer) {
                        case 'a':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            Task t = new Task(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(t);

                            break;

                        case 'b':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = " Recurring Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the end date? ");
                            endDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            System.out.print("Enter the frequency: ");
                            frequency = scan.nextInt();
                            RecurringTask r = new RecurringTask(taskName, type, startTime, startDate, duration, endDate, frequency);
                            schedule.scheduleTask(r);

                            break;
                        case 'c':
                            System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Transient Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            TransientTask tr = new TransientTask(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(tr);

                            break;
                        case 'd':
                             System.out.print("What is the task name you are scheduling? ");
                            taskName = scan.nextLine();
                            type = "Anti Task";
                            System.out.print("What is the start time?");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextInt();
                            AntiTask a = new AntiTask(taskName, type, startTime, startDate, duration);
                            schedule.scheduleTask(a);

                            break;
                    }

                    break;

                case 4:
                    System.out.println( schedule.generateReport() );
                    break;
            }

            System.out.print("Do you want to continue?  (y / n) ");
            choice = scan.nextInt();

        }
        scan.close();
    }    
}
