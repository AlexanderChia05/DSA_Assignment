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
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                scan.nextLine(); // consume newline
                if (choice >= 0 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Error: Choice must be between 0 and 3. Please try again.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 0 and 3.\n");
                scan.nextLine(); // consume invalid input
            }
        }

        switch (choice) {
            case 1 -> CreateGraph();
            case 2 -> RecommendFriends();
            case 3 -> ViewFriendNetwork();
            case 0 -> System.exit(0);
            default -> System.out.println("Invalid choice!");
        }


    }

    private static void CreateGraph() {
        clearScreen();
        System.out.println(StringConstants.CREATE_GRAPH);
        int choice = -1;

        while (true) {
            System.out.print("Choose an option: ");
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                scan.nextLine(); // consume newline
                if (choice >= 0 && choice <= 7) {
                    break;
                } else {
                    System.out.println("Error: Choice must be between 0 and 7. Please try again.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 0 and 7.\n");
                scan.nextLine(); // consume invalid input
            }
        }

        switch (choice) {
            case 1 -> addUser();
            case 2 -> removeUser();
            case 3 -> addFriendship();
            case 4 -> removeFriendship();
            case 5 -> displayAllUsers();
            case 6 -> displayAllFriendships();
            case 7 -> resetAll();
            case 0 -> MainMenu();
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void addUser() {
        String continueChoice;
        do {
            clearScreen();

            System.out.println("All users:");
            displayUser(userController.getAllUsers());

            System.out.print("\nEnter the name of the user: ");
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
            displayUser(userController.getAllUsers());

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
            clearScreen();

            System.out.println("All users:");
            displayUser(userController.getAllUsers());

            System.out.println("All friendships:");
            Set<String> displayed = new HashSet<>();
            Map<String, List<String>> friendships = friendshipController.getAllFriendships();
            for (Map.Entry<String, List<String>> entry : friendships.entrySet()) {
                String user = entry.getKey();
                for (String friend : entry.getValue()) {
                    String pair = user.compareTo(friend) < 0 ? user + "," + friend : friend + "," + user;
                    if (!displayed.contains(pair)) {
                        displayFriendship(user, friend);
                        displayed.add(pair);
                    }
                }
            }

            System.out.print("\nEnter the name of the first user: ");
            String user1 = scan.nextLine().trim();
            System.out.print("Enter the name of the second user: ");
            String user2 = scan.nextLine().trim();

            boolean user1Exists = false, user2Exists = false;
            for (User user : userController.getAllUsers()) {
                if (user.getName().equals(user1)) user1Exists = true;
                if (user.getName().equals(user2)) user2Exists = true;
            }

            if (!user1Exists || !user2Exists) {
                System.out.println("Both users must exist to create a friendship.");
            } else if (!user1.isEmpty() && !user2.isEmpty()) {
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
            System.out.println("All friendships:");
            Set<String> displayed = new HashSet<>();
            Map<String, List<String>> friendships = friendshipController.getAllFriendships();
            for (Map.Entry<String, List<String>> entry : friendships.entrySet()) {
                String user = entry.getKey();
                for (String friend : entry.getValue()) {
                    String pair = user.compareTo(friend) < 0 ? user + "," + friend : friend + "," + user;
                    if (!displayed.contains(pair)) {
                        displayFriendship(user, friend);
                        displayed.add(pair);
                    }
                }
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
        clearScreen();
        System.out.println("All users:");
        displayUser(userController.getAllUsers());

        System.out.println("Press Enter to return...");
        scan.nextLine();
        CreateGraph();
    }

    private static void displayAllFriendships() {
        clearScreen();
        System.out.println("All friendships:\n");
        Set<String> displayed = new HashSet<>();
        Map<String, List<String>> friendships = friendshipController.getAllFriendships();
        List<String[]> cardLinesList = new ArrayList<>();
        int cardsPerRow = 3;

        for (Map.Entry<String, List<String>> entry : friendships.entrySet()) {
            String user = entry.getKey();
            for (String friend : entry.getValue()) {
                String pair = user.compareTo(friend) < 0 ? user + "," + friend : friend + "," + user;
                if (!displayed.contains(pair)) {
                    String card = StringUtils.beautify(
                        "Friendship\n" +
                        "==========\n" +
                        user + " - " + friend
                    );
                    cardLinesList.add(card.split("\n"));
                    displayed.add(pair);
                }
            }
        }

        // Print cards in rows of 3
        for (int i = 0; i < cardLinesList.size(); i += cardsPerRow) {
            int rowCards = Math.min(cardsPerRow, cardLinesList.size() - i);
            int cardHeight = cardLinesList.get(i).length;
            for (int line = 0; line < cardHeight; line++) {
                for (int j = 0; j < rowCards; j++) {
                    System.out.print(cardLinesList.get(i + j)[line] + "  ");
                }
                System.out.println();
            }
            System.out.println();
        }

        System.out.println("\nPress Enter to return...");
        scan.nextLine();
        CreateGraph();
    }

    private static void resetAll() {
        System.out.print("Are you sure you want to reset all data? (Y/N): ");
        String confirm = scan.nextLine().trim().toUpperCase();
        if (confirm.equals("Y")) {
            userController.resetUsers();
            friendshipController.resetFriendships();
            System.out.println("All data has been reset.");
        }
        else {
            System.out.println("Reset cancelled.");
        }
        System.out.println("Press Enter to return...");
        scan.nextLine();
        CreateGraph();
    }



    private static void RecommendFriends() {
        clearScreen();
        System.out.println("All users:");
        displayUser(userController.getAllUsers());

        System.out.print("\nEnter the username to search recommended friends: ");
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
        Map<String, Integer> level = new HashMap<>();
        List<String> level1 = new ArrayList<>();
        List<String> level2 = new ArrayList<>();

        queue.add(username);
        visited.add(username);
        level.put(username, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int currLevel = level.get(current);

            for (String friend : friendshipController.getFriends(current)) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    queue.add(friend);
                    int nextLevel = currLevel + 1;
                    level.put(friend, nextLevel);
                    if (nextLevel == 1) {
                        level1.add(friend);
                    } else if (nextLevel == 2) {
                        level2.add(friend);
                    }
                }
            }
        }

        int cardsPerRow = 3; // Declare once for both Level 1 and Level 2

        // Display Level 1 (Direct Friends)
        System.out.println("\nLevel 1 (Direct Friends)");
        List<String[]> level1CardLinesList = new ArrayList<>();
        for (String name : level1) {
            String card = StringUtils.beautify(
                "Direct Friend\n" +
                "─────────────────\n" +
                "Name: " + name
            );
            level1CardLinesList.add(card.split("\n"));
        }
        if (level1CardLinesList.isEmpty()) {
            System.out.println("No direct friends found.");
        } else {
            for (int i = 0; i < level1CardLinesList.size(); i += cardsPerRow) {
                int rowCards = Math.min(cardsPerRow, level1CardLinesList.size() - i);
                int maxCardHeight = 0;
                for (int j = 0; j < rowCards; j++) {
                    maxCardHeight = Math.max(maxCardHeight, level1CardLinesList.get(i + j).length);
                }
                for (int line = 0; line < maxCardHeight; line++) {
                    for (int j = 0; j < rowCards; j++) {
                        String[] cardLines = level1CardLinesList.get(i + j);
                        String lineText = (line < cardLines.length) ? cardLines[line] : "                       ";
                        System.out.print(lineText + "  ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }

        // Display Level 2 (Friends of Friends)
        System.out.println("Level 2 (Friend of Friends)");
        if (level2.isEmpty()) {
            System.out.println("No friends of friends found.");
        } else {
            // Prepare cards for recommendations (with mutual friends)
            List<String[]> cardLinesList = new ArrayList<>();
            Set<String> directFriends = new HashSet<>(level1);

            for (String rec : level2) {
                // Find mutual friends
                Set<String> recFriends = new HashSet<>(friendshipController.getFriends(rec));
                Set<String> mutual = new HashSet<>(recFriends);
                mutual.retainAll(directFriends);

                StringBuilder mutualInfo = new StringBuilder();
                mutualInfo.append("Mutual Friends: ").append(mutual.size());
                if (!mutual.isEmpty()) {
                    mutualInfo.append("\n- ").append(String.join("\n- ", mutual));
                }

                String card = StringUtils.beautify(
                    "Recommendation\n" +
                    "─────────────────\n" +
                    "Name: " + rec + "\n" +
                    mutualInfo
                );
                cardLinesList.add(card.split("\n"));
            }

            // Print cards in rows of 3
            for (int i = 0; i < cardLinesList.size(); i += cardsPerRow) {
                int rowCards = Math.min(cardsPerRow, cardLinesList.size() - i);
                int maxCardHeight = 0;
                for (int j = 0; j < rowCards; j++) {
                    maxCardHeight = Math.max(maxCardHeight, cardLinesList.get(i + j).length);
                }
                for (int line = 0; line < maxCardHeight; line++) {
                    for (int j = 0; j < rowCards; j++) {
                        String[] cardLines = cardLinesList.get(i + j);
                        String lineText = (line < cardLines.length) ? cardLines[line] : "                         ";
                        System.out.print(lineText + "  ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }

        System.out.println("\nPress Enter to return...");
        scan.nextLine();
        MainMenu();
    }
    
    private static void ViewFriendNetwork() {
        clearScreen();
        System.out.println("All users:");
        displayUser(userController.getAllUsers());
        
        System.out.print("\nEnter the username to view friend network: ");
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

        // BFS to find friends and friends of friends
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> level = new HashMap<>();
        List<String> level1 = new ArrayList<>();
        List<String> level2 = new ArrayList<>();

        queue.add(username);
        visited.add(username);
        level.put(username, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int currLevel = level.get(current);

            for (String friend : friendshipController.getFriends(current)) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    queue.add(friend);
                    int nextLevel = currLevel + 1;
                    level.put(friend, nextLevel);
                    if (nextLevel == 1) {
                        level1.add(friend);
                    } else if (nextLevel == 2) {
                        level2.add(friend);
                    }
                }
            }
        }

        // Display recommendation graphs
        if (level2.isEmpty()) {
            System.out.println("No friend recommendations available.");
        } else {
            Set<String> directFriends = new HashSet<>(level1);
            for (String recommended : level2) {
                // Find mutual friends
                Set<String> recFriends = new HashSet<>(friendshipController.getFriends(recommended));
                Set<String> mutual = new HashSet<>(recFriends);
                mutual.retainAll(directFriends);

                if (!mutual.isEmpty()) {
                    // Build the graph
                    StringBuilder mutualInfo = new StringBuilder();
                    for (String mutualFriend : mutual) {
                        mutualInfo.append("- ").append(mutualFriend).append("\n");
                    }

                    String userCard = StringUtils.beautify(
                        "User\n" +
                        "────────────\n" +
                        "Name: " + username,
                        StringUtils.BorderColor.BLUE
                    );

                    String mutualCard = StringUtils.beautify(
                        "Mutual Friends\n" +
                        "──────────────\n" +
                        mutualInfo.toString().trim(),
                        StringUtils.BorderColor.RED
                    );

                    String recommendedCard = StringUtils.beautify(
                        "Recommended Friend\n" +
                        "──────────────────\n" +
                        "Name: " + recommended,
                        StringUtils.BorderColor.BLUE
                    );

                    // Split cards into lines
                    String[] userLines = userCard.split("\n");
                    String[] mutualLines = mutualCard.split("\n");
                    String[] recommendedLines = recommendedCard.split("\n");

                    // Find max height
                    int maxHeight = Math.max(userLines.length, Math.max(mutualLines.length, recommendedLines.length));

                    // Print the graph
                    for (int i = 0; i < maxHeight; i++) {
                        String userLine = i < userLines.length ? userLines[i] : " ".repeat(userLines[0].length() / 2);
                        String mutualLine = i < mutualLines.length ? mutualLines[i] : " ".repeat(mutualLines[0].length());
                        String recommendedLine = i < recommendedLines.length ? recommendedLines[i] : " ".repeat(recommendedLines[0].length());
                        
                        // Print with consistent spacing
                        String connector = (i == maxHeight/2) ? "────" : "    ";
                        System.out.println(userLine + "  " + connector + "  " + mutualLine + "  " + connector + "  " + recommendedLine);
                    }
                    System.out.println();
                }
            }
        }

        System.out.println("\nPress Enter to return...");
        scan.nextLine();
        MainMenu();
    }

    private static void displayUser(Collection<User> users) {
        List<String[]> cardLinesList = new ArrayList<>();
        int cardsPerRow = 4;

        // Build beautified cards and split into lines
        for (User user : users) {
            String card = StringUtils.beautify(
                "User Profile\n" +
                "────────────\n" +
                "Name: " + user.getName()
            );
            cardLinesList.add(card.split("\n"));
        }

        // Print cards in rows of 3, only for actual users
        for (int i = 0; i < cardLinesList.size(); i += cardsPerRow) {
            int rowCards = Math.min(cardsPerRow, cardLinesList.size() - i);
            int cardHeight = cardLinesList.get(i).length;
            for (int line = 0; line < cardHeight; line++) {
                for (int j = 0; j < rowCards; j++) {
                    System.out.print(cardLinesList.get(i + j)[line] + "  ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static void displayFriendship(String user1, String user2) {
        String card = StringUtils.beautify(
            "Friendship\n" +
            "==========\n" +
            user1 + " - " + user2
        );
        System.out.println(card);
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