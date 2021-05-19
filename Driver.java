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
        
        System.out.print("Enter your user name(or name of file you'd like to use): ");
        username = scan.nextLine();
        String readFilename = username + ".json";
        String filename = username + "-db.json";
        File file = new File( filename );
        DataFile user;
        if ( file.exists() == false ) { // Check if file exists
            System.out.print( "Creating file..." + filename );
            user = new DataFile( filename, true );
            System.out.println( " done" );
        } else {
            System.out.print( "Reading file..." + filename );
            user = new DataFile( readFilename, true ); // if it does exist read the file.
            user.setFilename( filename );
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

            if ( choice < 2 ) { // If generating report don't need to ask about kind of task
                System.out.println("What kind of task do you want to schedule?");
                System.out.println("a. Task");
                System.out.println("b. Recurring task");
                System.out.println("c. Transient task");
                System.out.println("d. Anti task");

                System.out.println("Enter your choice: (a-d) ");
                answer = scan.next().charAt(0);
            }
            scan.nextLine(); // Clear scanner buffer

            switch(choice) {
                case 1:
                    System.out.println("\n| SCHEDULE A TASK |");
                    System.out.print("What is the name of the task you want to schedule? ");
                    taskName = scan.nextLine();
                    switch(answer) {
                        case 'a':
                            printTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
                            System.out.print("What is the start time? ");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            try {
                                Task t = new Task(taskName, type, startTime, startDate, duration);
                                if ( !schedule.scheduleTask(t) ) {
                                    System.out.println( "Task conflicted with schedule, was NOT added." );
                                }
                            } catch ( RestrictionCheckFailedException e ) {
                                System.out.println( "Invalid task entered, will not be added to schedule." );
                            }   

                            break;

                        case 'b':
                            printRTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
                            System.out.print("What is the start time? ");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            scan.nextLine(); // Flush the scanner of the \n
                            System.out.print("What is the end date? ");
                            endDate = scan.nextInt();
                            scan.nextLine();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            System.out.print("Enter the frequency: ");
                            scan.nextLine();
                            frequency = scan.nextInt();
                            try {
                                RecurringTask r = new RecurringTask(taskName, type, startTime, startDate, duration, endDate, frequency);
                                if ( !schedule.scheduleTask(r) ) {
                                    System.out.println( "Task conflicted with schedule, was NOT added." );
                                }
                            } catch ( RestrictionCheckFailedException e ) {
                                System.out.println( "Invalid task entered, will not be added to schedule." );
                            }

                            break;
                        case 'c':
                            printTTypes();
                            System.out.print("What type of task are you scheduling? ");
                            type = scan.nextLine();
                            System.out.print("What is the start time? ");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            try {
                                TransientTask tr = new TransientTask(taskName, type, startTime, startDate, duration);
                                if ( !schedule.scheduleTask(tr) ) {
                                    System.out.println( "Task conflicted with schedule, was NOT added." );
                                }
                            } catch ( RestrictionCheckFailedException e ) {
                                System.out.println( "Invalid task entered, will not be added to schedule." );
                            }
                            break;
                        case 'd':
                            type = "Cancellation";
                            System.out.print("What is the start time? ");
                            startTime = scan.nextDouble();
                            System.out.print("What is the start date (YYYYMMDD)? ");
                            startDate = scan.nextInt();
                            System.out.print("What is the duration of the task? ");
                            duration = scan.nextDouble();
                            try {
                                AntiTask a = new AntiTask(taskName, type, startTime, startDate, duration);
                                if ( !schedule.scheduleTask(a) ) {
                                    System.out.println( "Task conflicted with schedule, was NOT added." );
                                }
                            } catch ( RestrictionCheckFailedException e ) {
                                System.out.println( "Invalid task entered, will not be added to schedule." );
                            }
                            break;
                    }
                    break;
                case 2:
                    System.out.println("\n| EDIT A TASK |");
                    System.out.print("What is the name of the task you would like to edit (case-sensitive)? ");
                    taskName = scan.nextLine();
                    try {
                        Task newTask = schedule.getTask( taskName ).clone();
                        boolean editing = true;
                        while ( editing ) {
                            System.out.println( "\n" + schedule.generateSingleTaskReport( newTask ) );
                            System.out.println( "What would you like to edit? " );
                            printEditOptions( newTask );
                            int response = scan.nextInt();
                            switch (response) {
                                case 0:
                                    editing = false;
                                    break;
                                case 1:
                                    System.out.print( "Enter new date (YYYYMMDD): " );
                                    int newDate = scan.nextInt();
                                    if ( !newTask.setStartDate( newDate ) ) {
                                        System.out.println( "Invalid Date, keeping old one." );
                                    }
                                    break;
                                case 2:
                                    System.out.print( "Enter new name: " );
                                    if ( scan.hasNextLine() ) {
                                        scan.nextLine(); // Clear buffer
                                    }
                                    String newName = scan.nextLine();
                                    newTask.setTaskName( newName );
                                    break;
                                case 3:
                                    System.out.print( "Enter new start time: " );
                                    double newTime = scan.nextDouble();
                                    if ( !newTask.setStartTime( newTime ) ) {
                                        System.out.println( "Invalid Start Time, keeping old one." );
                                    }
                                    break;
                                case 4:
                                    System.out.print( "Enter new duration: " );
                                    double newDuration = scan.nextDouble();
                                    if ( !newTask.setDuration( newDuration ) ) {
                                        System.out.println( "Invalid Duration, keeping old one." );
                                    }
                                    break;
                                case 5:
                                    if ( newTask.isRecurringTask() ) {
                                        RecurringTask tmpNewTask = (RecurringTask)newTask.clone();
                                        System.out.println( "Enter new end date (YYYYMMDD): " );
                                        int newEndDate = scan.nextInt();
                                        if ( !tmpNewTask.setEndDate( newEndDate ) ) {
                                            System.out.println( "Invalid End Date, keeping old one." );
                                        }
                                        newTask = tmpNewTask;
                                    } else {
                                        System.out.println( "Invalid option entered" );
                                    }
                                    break;
                                case 6:
                                    if ( newTask.isRecurringTask() ) {
                                        RecurringTask tmpNewTask = (RecurringTask)newTask.clone();
                                        System.out.println( "Enter new frequency: " );
                                        int newFrequency = scan.nextInt();
                                        if ( !tmpNewTask.setFrequency( newFrequency ) ) {
                                            System.out.println( "Invalid End Date, keeping old one." );
                                        }
                                        newTask = tmpNewTask;
                                    } else {
                                        System.out.println( "Invalid option entered" );
                                    }
                                    break;
                                default:
                                    if ( response != 5 || response != 6 ) { // Prevents a double print
                                        System.out.println( "Invalid option entered" );
                                    }
                                }
                        }
                        System.out.print( "Attempting to schedule edited task..." );
                        if ( schedule.editTask( taskName, newTask ) ) {
                            System.out.println( "SUCCESS" );
                        } else {
                            System.out.println( "FAILED ( Keeping old task )" );
                        }
                    } catch ( TaskNotFoundException e ) {
                        System.out.println( "Task could not be found." );
                    }
                    break;
                case 3:
                    System.out.println("\n| DELETE A TASK |");
                    System.out.print("What is the name of the task you want to delete (case-sensitive)? ");
                    taskName = scan.nextLine();
                    if (schedule.deleteTask( taskName, false )) {
                        System.out.println( "Successfully deleted task(s)." );
                    } else {
                        System.out.println( "Failed to delete task." );
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
    
    static void printEditOptions( Task task ) {
        System.out.println( "0. Schedule Edited Task" );
        System.out.println( "1. Date" );
        System.out.println( "2. Name" );
        System.out.println( "3. Time" );
        System.out.println( "4. Duration" );
        if ( task.isRecurringTask() ) {
            System.out.println( "5. End Date" );
            System.out.println( "6. Frequency" );
        }
    }

    // Tasks types
    static void printTypes() {
        System.out.println("");
        System.out.println("Types of tasks");
        System.out.println("Class");
        System.out.println("Study");
        System.out.println("Sleep");
        System.out.println("Exercise");
        System.out.println("Work");
        System.out.println("Meal");
        System.out.println("Visit");
        System.out.println("Shopping");
        System.out.println("Appointment");
        System.out.println("Cancellation");
        System.out.println("");
    }

    // Recurring tasks types

    static void printRTypes(){
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

    static void printTTypes() {
        System.out.println("");
        System.out.println("Types of transient tasks");
        System.out.println("Visit");
        System.out.println("Shopping");
        System.out.println("Appointment");
        System.out.println("");
    }

}
