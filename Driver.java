import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String username = "";
        char answer;
        
        while(true) {
            
            System.out.print("Enter your user name: ");
            username = scan.nextLine();

            // if username is not in the file
            // ask if you want to add the username 
            // to the file, otherwise exit
            if (username != User.getFileName()) { //== username) {
                System.out.println("The user name you entered is not in the system.");
                System.out.print("Do you want to add \"" + username + "\" to your file? (y / n) ");
                answer = scan.next().charAt(0);

                if(answer == 'y') {
                    // add to the file
                    
                    break;
                } 

                
            }
        }

        //System.out.println("Username: " + username);
    }    
}
