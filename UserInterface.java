
import java.util.Scanner;
import java.util.InputMismatchException;
public class UserInterface {
    private Scanner scanner;

    public UserInterface() {
        System.out.println("[UserInterface] Initializing UserInterface...");
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("[UserInterface] Displaying menu...");
        System.out.println("=== SwiftBot Control Menu ===");
        System.out.println("1 = Start Program");
        System.out.println("2 = Display Log");
        System.out.println("3 = Exit");
        System.out.print("Enter your choice: ");
    }

    public int getUserInput() {
        System.out.println("[UserInterface] Getting user input...");
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= 1 && input <= 3) {
                    System.out.println("[UserInterface] User input: " + input);
                    return input;
                } else {
                    System.out.println("[UserInterface] Invalid input. Please enter a number between 1 and 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[UserInterface] Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    public void displayLog(String log) {
        System.out.println("[UserInterface] Displaying log...");
        System.out.println("=== Execution Log ===");
        System.out.println(log);
        System.out.println("====================");
    }

    public boolean promptToDisplayLog() {
        System.out.println("[UserInterface] Prompting user to display log...");
        System.out.print("Do you want to display the log? (Y/N): ");
        String input = scanner.next().toUpperCase();
        return input.equals("Y");
    }

    public void close() {
        System.out.println("[UserInterface] Closing UserInterface...");
        scanner.close();
    }
}