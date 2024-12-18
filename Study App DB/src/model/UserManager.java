package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Statistics;

public class UserManager {
	private String currentusername;
	private boolean loggedinstatus;
	private Statistics statisticsLink;
	private boolean accountcreated;
	
	public UserManager() {
		statisticsLink = new Statistics();
		loggedinstatus = false;
		accountcreated = false;
	}
	
	
	
    // Adds a new user to the database
	  public boolean addNewUser(String username, String password) {
	        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
	        try (Connection conn = DbConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, username);
	            pstmt.setString(2, password);
	            int result = pstmt.executeUpdate();
	            return result > 0;
	        } catch (SQLException e) {
	            // Handle SQL exceptions
	            e.printStackTrace();
	        }
	        return false;
	    }
	  //  Adapted from Poe: Chat GPT 
	  // "method to add a newUser with username and password as parameters to sql database in java" 


    // Checks if the username and password match a user in the database
    public boolean checkUserNameAndPass(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }
        return false;
    }
	//  Adapted from: 
    // (prince_ade, 2024) 
    // Poe: Chat GPT "check username and password matches user in database method sql in java"
    
    public void createAccount (JTextField tFSetUsername, JTextField tfSetPassword, JTextField tfSetPasswordCheck, JLabel lblCreateAccountError) {
    	
    	if (tFSetUsername.getText() == "username" || tfSetPassword.getText() == "password" || tfSetPasswordCheck.getText() == "retype password") {
    		lblCreateAccountError.setText("Please fill in all fields");
    	} 
    	else if (!tfSetPassword.getText().equals(tfSetPasswordCheck.getText())) {
    		lblCreateAccountError.setText("Password does not match");
    	}
    	
    	if(!tFSetUsername.getText().isEmpty() &&  !(tFSetUsername.getText() == "username") && tfSetPassword.getText().equals(tfSetPasswordCheck.getText()) 
    			&& !tfSetPassword.getText().isEmpty()) {
    		this.addNewUser(tFSetUsername.getText(), tfSetPassword.getText());
    		this.accountcreated = true;
    		
    	} else {
    		System.out.println("something is wrong");
    		this.accountcreated = false;
    	}
    }
    
    public void Login(JTextField tFUsername, JTextField tFPassword, JLabel lblSession, JButton goToAccountPanel, JLabel lblTotalTimeStudied, JLabel lblLoginError) {
		if(this.checkUserNameAndPass(tFUsername.getText(), tFPassword.getText()) == true) {
			lblLoginError.setVisible(false);
			System.out.println("Logged in!");
			this.loggedinstatus = true;
			this.currentusername = tFUsername.getText();
			goToAccountPanel.setText(this.currentusername);
			int totalSessions = statisticsLink.retrieveSessions(this.currentusername);
			if (totalSessions >= 0) {
				lblSession.setText("Total Sessions: " + totalSessions);
			} else {
				System.out.println("not found");
			}
			
			int retrieveTotalTime = statisticsLink.retrieveTotalTime(this.currentusername);
			lblTotalTimeStudied.setText("Total time studied:" + (retrieveTotalTime/60) + "hrs " + (retrieveTotalTime % 60) + "mins");
			
			
		} else {
			this.loggedinstatus = false;
			lblLoginError.setVisible(true);
			System.out.println("Error");
		}
    }
    
    public void Logout(JButton goToAccountPanel, JLabel lblSession) {
    	this.loggedinstatus = false;
    	goToAccountPanel.setText("ACCOUNT");
    	lblSession.setText("Total sessions: 0");
    	
    }
    
    public String getUsername() {
    	return this.currentusername;
    }
    
    public boolean getLoggedInStatus() {
    	return this.loggedinstatus;
    }
    
    public boolean getAccountCreated() {
    	return this.accountcreated;
    }


}