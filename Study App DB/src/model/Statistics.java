package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Statistics {

	private int totalNumberOfSessions;
	
	
	public Statistics() {
		totalNumberOfSessions = 0;
	}
	
    public int getTotalNumberOfSessions() {
    	return this.totalNumberOfSessions;
    }
    
    public void setTotalNumberOfSessions(int addSession) {
    	this.totalNumberOfSessions = addSession;
    }
    
    public void addNewSession(int howManyToAdd) {
    	totalNumberOfSessions = totalNumberOfSessions + howManyToAdd;
    }
    
    public int retrieveSessions(String username) {
        String selectSql = "SELECT sessions FROM users WHERE username = ?";
        int totalSessions = -1;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Retrieve the current sessions count
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                totalSessions = rs.getInt("sessions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return totalSessions;
    }
    
    public void incrementSessions(String username) {
        String updateSql = "UPDATE users SET sessions = sessions + 1 WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // Increment the sessions count
            updateStmt.setString(1, username);
            int affectedRows = updateStmt.executeUpdate();

            if (affectedRows == 0) {
                // Handle the case where the update did not affect any rows (e.g., user not found)
                throw new SQLException("Incrementing sessions failed, user not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
    
    // time
    public void updateTotalTimeInDatabase(String username, int additionalMinutes) {
        String updateSql = "UPDATE users SET totaltime = totaltime + ? WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            updateStmt.setInt(1, additionalMinutes);
            updateStmt.setString(2, username);
            int affectedRows = updateStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating total time failed, user not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
    
    public int retrieveTotalTime(String username) {
        String selectSql = "SELECT totaltime FROM users WHERE username = ?";
        int totalTimeInMinutes = -1;  // Initialize to -1 to indicate an error if the user is not found

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Set the username in the PreparedStatement
            selectStmt.setString(1, username);
            
            // Execute the query and retrieve the total time
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                totalTimeInMinutes = rs.getInt("totaltime");  // Get the totaltime column value
            }
            
            // It's a good practice to close the ResultSet
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return totalTimeInMinutes;  // This will be -1 if the user was not found
    }

    
}
