package model;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class CountdownTimer {
    private int hour, min, sec;
    private Timer timer;
    private boolean isRunning;
    private boolean isFaceDetected;
    private int stopwatchHours, stopwatchMinutes, stopwatchSeconds;
    
    public CountdownTimer() {
    	sec = 0;
    	isRunning = false;
    	isFaceDetected = false;
    }
    
    
    // Getters & Setters
    public boolean getIsFaceDetected() {
    	return this.isFaceDetected;
    }
    
    public void setIsFaceDetected(boolean set) {
    	this.isFaceDetected = set;
    }
    
    public boolean getIsRunning() {
    	return this.isRunning;
    }
    public void setIsRunning(boolean set) {
    	this.isRunning = set;
    }
    // End of Getters & Setters
    
    public int getHoursStudied() {
    	return this.stopwatchHours;
    }
    
    public int getMinutesStudied() {
    	return this.stopwatchMinutes;
    }
	
    
    // Method for the timer in "Study Mode"   
	public void countdownTimerMethod (JFrame frame, CardLayout cardLayoutVariable, JTextField textField_set_timer_hours, 
			JTextField textField_set_time_minutes, JLabel lblTimeLeftToStudy, JLabel lblError, JLabel lblTimeStudied) {
		// Section 1: When setting the timer the user is presented with 2 textFields to set (hours & minutes) 
		// This checks if the both textFields are Empty
		if (textField_set_timer_hours.getText().isEmpty() && textField_set_time_minutes.getText().isEmpty()) {
			lblError.setText("Please fill in a field before starting the timer.");
            System.out.println("Please fill in a field before starting the timer."); // error if textFields are empty
            return; // Return without starting the timer
        } else {
        	// If textFields are filled it will go to the StudyMode panel
        	cardLayoutVariable.show(frame.getContentPane(), "name_StudyMode");
        }
		
		// To make it easy for the user this autofills the empty section if only one is filled out
		// EG. if user wants to study for one hour, the minutes will automatically be set to 0
        if (textField_set_timer_hours.getText().isEmpty() && !textField_set_time_minutes.getText().isEmpty()) {
        	textField_set_timer_hours.setText("0");
        }

        if (!textField_set_timer_hours.getText().isEmpty() && textField_set_time_minutes.getText().isEmpty()) {
        	textField_set_time_minutes.setText("0");
        }

        
        // Section 2: Variables 
        hour = Integer.parseInt(textField_set_timer_hours.getText());
        min = Integer.parseInt(textField_set_time_minutes.getText());
        sec =  0;
        isRunning = true;
        
        stopwatchHours = 0;
        stopwatchMinutes = 0;
        stopwatchSeconds = 0;
        
        
        System.out.println("button pressed"); // tests if button is working

        // Section 3: Main logic of this method
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Modified to only count down when isRunning && facedetected is true
                if (isRunning && isFaceDetected) {
                	
                	stopwatchSeconds++;
                    if (stopwatchSeconds == 60) {
                        stopwatchSeconds = 0;
                        stopwatchMinutes++;
                        if (stopwatchMinutes == 60) {
                            stopwatchMinutes = 0;
                            stopwatchHours++;
                        }
                    }
                    
                    if (stopwatchHours >= 0 && stopwatchMinutes >= 0 && stopwatchSeconds >= 0) {
                    	lblTimeStudied.setText("Time Studied: " + stopwatchHours + ":" + stopwatchMinutes + ":" + stopwatchSeconds);
                    	//System.out.println("Time Studied: " + stopwatchHours + ":" + stopwatchMinutes + ":" + stopwatchSeconds);
                    }
                	
                	
                	
                    if (sec == 0) {
                        if (min == 0) {
                            if (hour == 0) {
                                ((Timer)e.getSource()).stop();
                                System.out.println("Time is up!");
                                // add TIME IS UP LABEL
                            } else {
                                hour--;
                                min = 59;
                                sec = 59;
                            }
                        } else {
                            min--;
                            sec = 59;
                        }
                    } else {
                        sec--;
                    }

                    if (hour >= 0 && min >= 0 && sec >= 0) {
                    	lblTimeLeftToStudy.setText("Time left to study: " + Integer.toString(hour) + ":" + Integer.toString(min) + ":" + Integer.toString(sec));
                        System.out.println("Time left to study: " + hour + ":" + min + ":" + sec);
                    }
                    
                }
            }
        });

        timer.start();
	}
}
