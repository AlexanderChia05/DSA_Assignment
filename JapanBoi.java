import java.util.*;
import java.io.IOException;
import utils.StringConstants;
import utils.StringUtils;

import model_controller.User;
import model_controller.UserController;
import model_controller.FriendshipController;

public class JapanBoi {
    private final static Scanner scan = new Scanner(System.in);
    private static final UserController userController = new UserController();
    private static final FriendshipController friendshipController = new FriendshipController();

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
            case 2 -> RecommendFriends();
            case 3 -> ViewFriendNetwork();
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
            scan.nextLine(); 

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
            case 3 -> addFriendship();
            case 4 -> removeFriendship();
            case 5 -> displayAllUsers();
            case 6 -> displayAllFriendships();
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

    private static void addFriendship() {
        String continueChoice;
        do{
            System.out.println("All users:");
            for (User user : userController.getAllUsers()) {
                System.out.println("- " + user.getName());
            }

            System.out.print("\nEnter the name of the first user: ");
            String user1 = scan.nextLine().trim();
            System.out.print("Enter the name of the second user: ");
            String user2 = scan.nextLine().trim();

            if (!user1.isEmpty() && !user2.isEmpty()) {
                friendshipController.addFriendship(user1, user2);
                System.out.println("Friendship between " + user1 + " and " + user2 + " added successfully.");
            } else {
                System.out.println("User names cannot be empty.");
            }
            System.out.print("Continue? Y/N: ");
            continueChoice = scan.nextLine().trim().toUpperCase();
        } while (continueChoice.equals("Y"));
        CreateGraph();
    }

    private static void removeFriendship() {
        String continueChoice;
        do {
            System.out.println("All users:");
            for (User user : userController.getAllUsers()) {
                System.out.println("- " + user.getName());
            }

            System.out.print("\nEnter the name of the first user: ");
            String user1 = scan.nextLine().trim();
            System.out.print("Enter the name of the second user: ");
            String user2 = scan.nextLine().trim();

            if (!user1.isEmpty() && !user2.isEmpty()) {
                friendshipController.removeFriendship(user1, user2);
                System.out.println("Friendship between " + user1 + " and " + user2 + " removed successfully.");
            } else {
                System.out.println("User names cannot be empty.");
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

    private static void displayAllFriendships() {
        System.out.println("All friendships:\n");
        Map<String, List<String>> friendships = friendshipController.getAllFriendships();
        Set<String> displayed = new HashSet<>();
        for (Map.Entry<String, List<String>> entry : friendships.entrySet()) {
            String user = entry.getKey();
            for (String friend : entry.getValue()) {
                // Avoid duplicate display (A-B and B-A)
                String pair = user.compareTo(friend) < 0 ? user + " - " + friend : friend + " - " + user;
                if (!displayed.contains(pair)) {
                    System.out.println(pair);
                    displayed.add(pair);
                }
            }
        }
        System.out.println("\nPress Enter to return...");
        scan.nextLine();
        CreateGraph();
    }



    private static void RecommendFriends() {
        System.out.println("All users:");
        for (User user : userController.getAllUsers()) {
            System.out.println("- " + user.getName());
        }
        
        System.out.print("\nEnter your username for recommendations: ");
        scan.nextLine(); 
        String username = scan.nextLine().trim();

        // Check if user exists
        boolean exists = false;
        for (User user : userController.getAllUsers()) {
            if (user.getName().equals(username)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            System.out.println("User not found.");
            System.out.println("Press Enter to return...");
            scan.nextLine();
            CreateGraph();
            return;
        }

        // BFS for friend recommendations
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> level = new HashMap<>();
        Set<String> directFriends = new HashSet<>(friendshipController.getFriends(username));
        Set<String> recommendations = new HashSet<>();

        queue.add(username);
        visited.add(username);
        level.put(username, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int currLevel = level.get(current);

            if (currLevel >= 2) continue; // Only look for friends-of-friends

            for (String friend : friendshipController.getFriends(current)) {
                if (!visited.contains(friend)) {
                    queue.add(friend);
                    visited.add(friend);
                    level.put(friend, currLevel + 1);

                    // If friend is at level 2 and not a direct friend or self, recommend
                    if (currLevel + 1 == 2 && !directFriends.contains(friend) && !friend.equals(username)) {
                        recommendations.add(friend);
                    }
                }
            }
        }

        if (recommendations.isEmpty()) {
            System.out.println("No friend recommendations found.");
        } else {
            System.out.println("Recommended friends for " + username + ":");
            for (String rec : recommendations) {
                System.out.println("- " + rec);
            }
        }
        System.out.println("\nPress Enter to return...");
        scan.nextLine();
        MainMenu();
    }

    private static void ViewFriendNetwork() {
        System.out.println("All users:");
        for (User user : userController.getAllUsers()) {
            System.out.println("- " + user.getName());
        }

        System.out.print("\nEnter the username to view their friend network: ");
        scan.nextLine(); // consume leftover newline
        String username = scan.nextLine().trim();

        // Check if user exists
        boolean exists = false;
        for (User user : userController.getAllUsers()) {
            if (user.getName().equals(username)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            System.out.println("User not found.");
            System.out.println("Press Enter to return...");
            scan.nextLine();
            MainMenu();
            return;
        }

        // BFS to find all connected friends
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        List<String> network = new ArrayList<>();

        queue.add(username);
        visited.add(username);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String friend : friendshipController.getFriends(current)) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    queue.add(friend);
                    network.add(friend);
                }
            }
        }

        System.out.println("\nFriend network for " + username + ":");
        if (network.isEmpty()) {
            System.out.println("No friends found.");
        } else {
            for (String friend : network) {
                System.out.println("- " + friend);
            }
        }
        System.out.println("\nPress Enter to return...");
        scan.nextLine();
        MainMenu();
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