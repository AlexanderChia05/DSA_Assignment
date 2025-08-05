package model_controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendshipController {
    private static final String FRIENDSHIP_FILE = "data/friendships.csv";
    private Map<String, List<String>> friendships = new HashMap<>();

    public FriendshipController() {
        loadFriendships();
    }

    private void loadFriendships() {
        File file = new File(FRIENDSHIP_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Format: user1,user2
                    String[] data = line.split(",");
                    if (data.length == 2) {
                        friendships.computeIfAbsent(data[0], k -> new ArrayList<>()).add(data[1]);
                        friendships.computeIfAbsent(data[1], k -> new ArrayList<>()).add(data[0]);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading friendships: " + e.getMessage());
            }
        } else {
            saveFriendships();
        }
    }

    private void saveFriendships() {
        Set<String> written = new HashSet<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FRIENDSHIP_FILE))) {
            for (Map.Entry<String, List<String>> entry : friendships.entrySet()) {
                String user = entry.getKey();
                for (String friend : entry.getValue()) {
                    // Avoid duplicate pairs (A,B) and (B,A)
                    String pair = user.compareTo(friend) < 0 ? user + "," + friend : friend + "," + user;
                    if (!written.contains(pair)) {
                        writer.write(user + "," + friend);
                        writer.newLine();
                        written.add(pair);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving friendships: " + e.getMessage());
        }
    }

    public void addFriendship(String user1, String user2) {
        if (!user1.equals(user2)) {
            friendships.computeIfAbsent(user1, k -> new ArrayList<>()).add(user2);
            friendships.computeIfAbsent(user2, k -> new ArrayList<>()).add(user1);
            saveFriendships();
        }
    }

    public void removeFriendship(String user1, String user2) {
        if (friendships.containsKey(user1)) {
            friendships.get(user1).remove(user2);
        }
        if (friendships.containsKey(user2)) {
            friendships.get(user2).remove(user1);
        }
        saveFriendships();
    }

    public List<String> getFriends(String user) {
        return friendships.getOrDefault(user, new ArrayList<>());
    }

    public Map<String, List<String>> getAllFriendships() {
        return friendships;
    }

    public void resetFriendships() {
        friendships.clear();
        saveFriendships();
    }
}