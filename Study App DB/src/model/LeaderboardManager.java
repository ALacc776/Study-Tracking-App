 package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; // This is the correct import for a generic List

public class LeaderboardManager {

    // Method to fetch leaderboard data sorted by sessions
    public List<User> getLeaderboardBySessions() {
        List<User> leaderboard = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY sessions DESC, totaltime DESC";

        // Use DbConnection.connect() to get a connection from DbConnection class
        try (Connection conn = DbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("ID"),
                        rs.getString("username"),
                        rs.getInt("sessions"),
                        rs.getInt("totaltime")
                );
                leaderboard.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }
    // Adapted from:
    // (Isuru Weerarathna, 2019)
    // Poe: ChatGPT "fetch data and get a connection from sql database in java"
    
    public List<User> getLeaderboardByTime() {
        List<User> leaderboard = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY totaltime DESC, sessions DESC";

        // Use DbConnection.connect() to get a connection from DbConnection class
        try (Connection conn = DbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("ID"),
                        rs.getString("username"),
                        rs.getInt("sessions"),
                        rs.getInt("totaltime")
                );
                leaderboard.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }
    // Adapted from:
    // (Isuru Weerarathna, 2019)
    // Poe: ChatGPT "fetch data and get a connection from sql database in java"
    

    // Inner class to hold user data
    public static class User {
        private int id;
        private String username;
        private int sessions;
        private int totalTime;

        public User(int id, String username, int sessions, int totalTime) {
            this.id = id;
            this.username = username;
            this.sessions = sessions;
            this.totalTime = totalTime;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public int getSessions() {
            return sessions;
        }

        public int getTotalTime() {
            return totalTime;
        }

        // Setters, if needed, can be added here
    }
}