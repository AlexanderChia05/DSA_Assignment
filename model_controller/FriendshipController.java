package model_controller;

import java.io.*;

public class FriendshipController {
    private static final String FRIENDSHIP_FILE = "data/friendships.csv";
    private User user;

    public FriendshipController() {
        loadFriendships();
    }

    private void loadFriendships() {
        File file = new File(FRIENDSHIP_FILE);
        if(file.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String[] data = reader.readLine().split(",");
                user = new User(data[0]);
            } catch (IOException e) {
                System.err.println("Error loading friendships: " + e.getMessage());
            }
        } else {
            saveFriendships();
        }
    }

    private void saveFriendships() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FRIENDSHIP_FILE))) {
            writer.write(user.getName());
        } catch (IOException e) {
            System.err.println("Error saving friendships: " + e.getMessage());
        }
    }
}