package model_controller;

import java.io.*;
import java.util.*;

public class UserController {
    private static final String USER_FILE = "data/users.csv";
    private Map<String, User> users = new HashMap<>();

    public UserController() {
        loadUsers();
    }

    private void loadUsers() {
        File file = new File(USER_FILE);
        if(file.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Assuming each line is just the username
                    User user = new User(line.trim());
                    users.put(user.getName(), user);
                }
            } catch (IOException e) {
                System.err.println("Error loading users: " + e.getMessage());
            }
        } else {
            saveUsers();
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users.values()) {
                writer.write(user.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Example method to add a user
    public void addUser(String name) {
        if (!users.containsKey(name)) {
            users.put(name, new User(name));
            saveUsers();
        }
    }

    // Example method to remove a user
    public void removeUser(String name) {
        if (users.containsKey(name)) {
            users.remove(name);
            saveUsers();
        }
    }

    // Example method to get all users
    public Collection<User> getAllUsers() {
        return users.values();
    }

    // Example method to reset all users
    public void resetUsers() {
        users.clear();
        saveUsers();
    }
}