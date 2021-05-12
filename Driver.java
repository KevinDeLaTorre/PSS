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
        
        System.out.print("Enter your user name: ");
        username = scan.nextLine();
        String filename = username + ".json";
        File file = new File( filename );
        DataFile user;
        if ( file.exists() == false ) { // Check if file exists
            System.out.print( "Creating file..." + filename );
            user = new DataFile( filename, false ); // if it doesn't create a blank file using username
            System.out.println( " done" );
        } else {
            System.out.print( "Reading file..." + filename );
            user = new DataFile( filename, true ); // if it does exist read the file.
            System.out.println( " done" );
        }
        Calendar schedule = new Calendar(user);

        boolean running = true;
        while( running ) {
            System.out.println("Menu");
            System.out.println("1. Schedule task");
            System.out.println("2. Edit task");
            System.out.println("3. Delete task");
            System.out.println("4. Generate full report");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = scan.nextInt();

            if ( choice < 4 ) { // If generating report don't need to ask about kind of task
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
                            printTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            printRTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            printTTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            type = "Cancellation";
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
                            printTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            printRTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            printTTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            type = "Cancellation";
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
                            printTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            printRTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            printTTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
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
                            type = "Cancellation";
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
                case 5:
                    System.out.println( "Goodbye" );
                    running = false;
                    break;
            }
        }
        scan.close();
    }    

    // Tasks types

    void printTypes() {
        System.out.println("");
        System.out.println("Types of tasks");
        System.out.println("Class");
        System.out.println("Study");
        System.out.println("Sleep");
        System.out.println("Exercise");
        System.out.println("Work");
        System.out.println("Meals");
        System.out.println("Visit");
        System.out.println("Shopping");
        System.out.println("Appointment");
        System.out.println("Cancellation");
        System.out.println("");
    }

    // Recurring tasks types

    void printRTypes(){
        System.out.println("");
        System.out.println("Types of recurring tasks");
        System.out.println("Class");
        System.out.println("Study");
        System.out.println("Sleep");
        System.out.println("Exercise");
        System.out.println("Work");
        System.out.println("Meals");
        System.out.println("");
    }
    
    // Transient tasks types

    void printTTypes() {
        System.out.println("");
        System.out.println("Types of transient tasks");
        System.out.println("Visit");
        System.out.println("Shopping");
        System.out.println("Appointment");
        System.out.println("");
    }

}
