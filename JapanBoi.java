import java.util.*;
import java.io.IOException;
import utils.StringConstants;
import utils.StringUtils;

import model_controller.User;
import model_controller.Friendship;
import model_controller.UserController;
import model_controller.FriendshipController;

public class JapanBoi {
    private final static Scanner scan = new Scanner(System.in);
    private static final UserController userController = new UserController();

    public static void main(String[] args) {
        MainMenu();
    }

    private static void MainMenu() {
        clearScreen();
        System.out.println(StringConstants.MainMenu);
        int choice = -1; // Initialize choice to -1 to handle invalid input

        while (true) {
            System.out.print("Choose an option: ");
            choice = scan.nextInt();

            if (choice == -1){
                scan.nextLine();
                continue;
            }
            if (choice >= 1 || choice <= 4) {
                break;
            }
            else{
                System.out.println("Error: Choice must be between 1 and 4. Please try again.");
            }
        }

        switch (choice) {
            case 1 -> CreateGraph();
            // case 2 -> RecommendFriends();
            // case 3 -> ViewFriendNetwork();
            case 4 -> System.exit(0);
            default -> System.out.println("Invalid choice!");
        }


    }

    private static void CreateGraph() {
        clearScreen();
        System.out.println(StringConstants.CREATE_GRAPH);
        int choice = -1;

        while (true) {
            System.out.print("Choose an option: ");
            choice = scan.nextInt();
            scan.nextLine(); // Consume the newline character

            if (choice == -1){
                scan.nextLine();
                continue;
            }
            if (choice >= 1 || choice <= 7) {
                break;
            }
            else{
                System.out.println("Error: Choioce must be between 1 and 7. Please try again.");
            }
        }

        switch (choice) {
            case 1 -> addUser();
            case 2 -> removeUser();
            // case 3 -> addFriendship();
            // case 4 -> removeFriendship();
            case 5 -> displayAllUsers();
            // case 6 -> displayAllFriendships();
            case 7 -> MainMenu();
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void addUser() {
        String continueChoice;
        do {
            System.out.print("Enter the name of the user: ");
            String user = scan.nextLine().trim();
            if (!user.isEmpty()) {
                userController.addUser(user);
                System.out.println("User " + user + " added successfully.");
            } else {
                System.out.println("User name cannot be empty.");
            }
            System.out.print("Continue? Y/N: ");
            continueChoice = scan.nextLine().trim().toUpperCase();
        } while (continueChoice.equals("Y"));
        CreateGraph();
    }

    private static void removeUser() {
        String continueChoice;
        do {
            System.out.println("All users:");
            for (User user : userController.getAllUsers()) {
                System.out.println("- " + user.getName());
            }

            System.out.print("\nEnter the name of the user to remove: ");
            String user = scan.nextLine().trim();
            if (!user.isEmpty()) {
                userController.removeUser(user);
                System.out.println("User " + user + " removed successfully.");
            } else {
                System.out.println("User name cannot be empty.");
            }
            System.out.print("Continue? Y/N: ");
            continueChoice = scan.nextLine().trim().toUpperCase();
        } while (continueChoice.equals("Y"));
        CreateGraph();
    }

    

    private static void displayAllUsers() {
        System.out.println("All users:");
        for (User user : userController.getAllUsers()) {
            System.out.println("- " + user.getName());
        }
        System.out.println("Press Enter to return...");
        scan.nextLine();
        CreateGraph();
    }


    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error clearing screen: " + e.getMessage());
        }
    }

}