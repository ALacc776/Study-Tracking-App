package view;

//import model.te1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import model.CountdownTimer;
import model.DbConnection;
import model.LeaderboardManager;
import model.Statistics;
import model.WebCamLogic;
import model.UserManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List; // This is the correct import for a generic List


public class MainWindow {

    private JFrame frame;
    private JTextField textField_set_timer_hours;
    private JTextField textField_set_time_minutes;
    
    private CountdownTimer countdownTimerlink;
    private Statistics statisticsLink; 
    private UserManager userManagerLink;
    
    private JTextField textField;
    private JTextField tFSetUsername;
    private JTextField tfSetPassword;
    private JTextField tfSetPasswordCheck;
    private JTextField tFUsername;
    private JTextField tFPassword;

    
    private DefaultTableModel leaderboardTableModel;
    private JTable leaderboardTable;
    private boolean sortBySessions = true; // Default sort by sessions
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    

    public MainWindow() {
    	DbConnection.connect();
    	countdownTimerlink = new CountdownTimer();
    	statisticsLink = new Statistics();
    	userManagerLink = new UserManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 565);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayoutVariable = new CardLayout(0,0);
        frame.getContentPane().setLayout(cardLayoutVariable);

        // --------------------------------------
        // COMPONENTS THAT DO NOT HAVE A FUNCTION
        // --------------------------------------
        // PANELS
        JPanel MainMenu = new JPanel();
        frame.getContentPane().add(MainMenu, "name_MainMenu");
        MainMenu.setLayout(null);

        JPanel StudyMode = new JPanel();
        frame.getContentPane().add(StudyMode, "name_StudyMode");
        StudyMode.setLayout(null);
        
        JPanel StudySet = new JPanel();
        frame.getContentPane().add(StudySet, "name_StudySet");
        StudySet.setLayout(null);
        
        JPanel Account = new JPanel();
        frame.getContentPane().add(Account, "name_Account");
        Account.setLayout(null);
       
        JPanel Stats = new JPanel();
        frame.getContentPane().add(Stats, "name_Stats");
        Stats.setLayout(null);
        
        JPanel Leaderboard = new JPanel();
        frame.getContentPane().add(Leaderboard, "name_Leaderboard");
        Leaderboard.setLayout(null);
        
        JPanel EndSessionConfirmation = new JPanel();
        frame.getContentPane().add(EndSessionConfirmation, "name_EndSessionConfirmation");
        EndSessionConfirmation.setLayout(null);
        
        JPanel Login = new JPanel();
        frame.getContentPane().add(Login, "name_Login");
        Login.setLayout(null);
        
        JPanel CreateAccount = new JPanel();
        frame.getContentPane().add(CreateAccount, "name_CreateAccount");
        CreateAccount.setLayout(null);
        
        JPanel LoggedIn = new JPanel();
        frame.getContentPane().add(LoggedIn, "name_LoggedIn");
        LoggedIn.setLayout(null);
        
 
        
        WebCamLogic webcam = new WebCamLogic();
        JPanel webcamPanel = webcam.getPanel();
        webcamPanel.setBounds(61, 55, 394, 258);
        StudyMode.add(webcamPanel);

        JLabel label_isLookingAtCamera = new JLabel("");
        label_isLookingAtCamera.setBounds(10, 495, 780, 20);
        StudyMode.add(label_isLookingAtCamera);
        
        JLabel label_webcamCoordinates = new JLabel("");
        label_webcamCoordinates.setBounds(61, 55, 450, 300);
        StudyMode.add(label_webcamCoordinates);
        
        JLabel lblTimeLeftToStudy = new JLabel("Time left to study: ");
        lblTimeLeftToStudy.setBounds(477, 55, 230, 53);
        StudyMode.add(lblTimeLeftToStudy);
        
        textField = new JTextField();
        textField.setBounds(49, 341, 462, 40);
        StudyMode.add(textField);
        textField.setColumns(10);
        
        textField_set_timer_hours = new JTextField();
        textField_set_timer_hours.setFont(new Font("Tahoma", Font.PLAIN, 40));
        textField_set_timer_hours.setBounds(116, 278, 172, 95);
        StudySet.add(textField_set_timer_hours);
        textField_set_timer_hours.setColumns(10);
        
        textField_set_time_minutes = new JTextField();
        textField_set_time_minutes.setFont(new Font("Tahoma", Font.PLAIN, 40));
        textField_set_time_minutes.setColumns(10);
        textField_set_time_minutes.setBounds(335, 278, 172, 95);
        StudySet.add(textField_set_time_minutes);
        
        JLabel lblHours = new JLabel("Hours");
        lblHours.setBounds(116, 257, 46, 14);
        StudySet.add(lblHours);
        
        JLabel lblMinutes = new JLabel("Minutes");
        lblMinutes.setBounds(338, 257, 46, 14);
        StudySet.add(lblMinutes);
        
        JLabel label_studyDurationtxt = new JLabel("Study Duration");
        label_studyDurationtxt.setFont(new Font("Tahoma", Font.BOLD, 30));
        label_studyDurationtxt.setBounds(194, 164, 236, 58);
        StudySet.add(label_studyDurationtxt);
        
        JLabel lblNewLabel = new JLabel(":");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 66));
        lblNewLabel.setBounds(302, 282, 23, 79);
        StudySet.add(lblNewLabel);
        
        JLabel lblError = new JLabel("");
        lblError.setBounds(116, 384, 390, 23);
        StudySet.add(lblError);
        
        
        JLabel lblEndSession = new JLabel("Are you sure you want to END SESSION? (the time will be saved but the session number won't increase)");
        lblEndSession.setBounds(139, 91, 532, 108);
        EndSessionConfirmation.add(lblEndSession);
        
        JLabel lblStats = new JLabel("STATS:");
        lblStats.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblStats.setBounds(64, 65, 212, 32);
        Stats.add(lblStats);
        
        JLabel lblSession = new JLabel("Total Sessions: " + Integer.toString(statisticsLink.getTotalNumberOfSessions()));
        lblSession.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblSession.setBounds(100, 154, 379, 68);
        Stats.add(lblSession);
        
        JLabel lblTotalTimeStudied = new JLabel("Total Time Studied: 0hrs 0 mins");
        lblTotalTimeStudied.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTotalTimeStudied.setBounds(100, 251, 451, 74);
        Stats.add(lblTotalTimeStudied);


        JLabel lblTimeStudied = new JLabel("Time Studied: ");
        lblTimeStudied.setBounds(477, 88, 230, 53);
        StudyMode.add(lblTimeStudied);
        
        JLabel lblLoginError = new JLabel("Error: incorrect username / password or user does not exist.. try again");
        lblLoginError.setBounds(163, 305, 480, 14);
        Login.add(lblLoginError);
        lblLoginError.setVisible(false);
        
        JLabel lblCreateAccountError = new JLabel("");
        lblCreateAccountError.setBounds(196, 364, 416, 14);
        CreateAccount.add(lblCreateAccountError);
        
        // --------------------------------------
        // COMPONENTS THAT GO TO OTHER PANELS WHEN PRESSED
        // --------------------------------------
//        JButton goToStudyModePanel = new JButton("DirectStudyMode(TEST)");
//        goToStudyModePanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
//        goToStudyModePanel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cardLayoutVariable.show(frame.getContentPane(), "name_StudyMode");
//            }
//        });
//        goToStudyModePanel.setBounds(10, 438, 165, 77);
//        MainMenu.add(goToStudyModePanel);
        
        JButton goToStudySetPanel = new JButton("STUDY");
        goToStudySetPanel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayoutVariable.show(frame.getContentPane(), "name_StudySet");
        	}
        });
        goToStudySetPanel.setFont(new Font("Tahoma", Font.BOLD, 70));
        goToStudySetPanel.setBounds(69, 145, 283, 282);
        MainMenu.add(goToStudySetPanel);
        

        JButton goToAccountPanel = new JButton("ACCOUNT");
        goToAccountPanel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (userManagerLink.getLoggedInStatus() == false) {
        			cardLayoutVariable.show(frame.getContentPane(), "name_Account");
        		} else {
        			cardLayoutVariable.show(frame.getContentPane(), "name_LoggedIn");
        		}
        		
        	}
        });
        goToAccountPanel.setFont(new Font("Tahoma", Font.BOLD, 30));
        goToAccountPanel.setBounds(565, 293, 187, 134);
        MainMenu.add(goToAccountPanel);
        
        JButton goToStatsPanel = new JButton("STATS");
        goToStatsPanel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayoutVariable.show(frame.getContentPane(), "name_Stats");
        	}
        });
        goToStatsPanel.setFont(new Font("Tahoma", Font.BOLD, 40));
        goToStatsPanel.setBounds(362, 292, 193, 135);
        MainMenu.add(goToStatsPanel);
        
        JButton goToLeaderboardPanel = new JButton("LEADERBOARD");
        goToLeaderboardPanel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayoutVariable.show(frame.getContentPane(), "name_Leaderboard");
        		populateLeaderboardTable();
        		
        	}
        });
        goToLeaderboardPanel.setFont(new Font("Tahoma", Font.BOLD, 40));
        goToLeaderboardPanel.setBounds(362, 145, 390, 143);
        MainMenu.add(goToLeaderboardPanel);
        
        goBackToShortcut(cardLayoutVariable, StudySet, "Go Back", "name_MainMenu");
        goBackToShortcut(cardLayoutVariable, Leaderboard,"Go Back", "name_MainMenu");
        
 
        goBackToShortcut(cardLayoutVariable, Stats, "Go Back", "name_MainMenu");
        
 
        goBackToShortcut(cardLayoutVariable, Account, "Go Back", "name_MainMenu");
        goBackToShortcut(cardLayoutVariable, LoggedIn, "Go Back", "name_MainMenu");
        
        JLabel lblusername = new JLabel("Username: ");
        lblusername.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblusername.setBounds(91, 101, 375, 49);
        LoggedIn.add(lblusername);
        
        JButton btnLogout = new JButton("LOGOUT");
        btnLogout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		userManagerLink.Logout(goToAccountPanel, lblSession);
        		cardLayoutVariable.show(frame.getContentPane(), "name_MainMenu");
        	}
        });
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnLogout.setBounds(496, 113, 142, 33);
        LoggedIn.add(btnLogout);
        goBackToShortcut(cardLayoutVariable, CreateAccount, "Go Back", "name_Account");
        goBackToShortcut(cardLayoutVariable, Login, "Go Back", "name_Account");
        
        
        
        JButton goToLoginPanel = new JButton("LOGIN");
        goToLoginPanel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayoutVariable.show(frame.getContentPane(), "name_Login");
        	}
        });
        goToLoginPanel.setFont(new Font("Tahoma", Font.BOLD, 40));
        goToLoginPanel.setBounds(183, 281, 446, 119);
        Account.add(goToLoginPanel);
        
        JButton goToCreateAccountPanel = new JButton("CREATE ACCOUNT");
        goToCreateAccountPanel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayoutVariable.show(frame.getContentPane(), "name_CreateAccount");
        	}
        });
        goToCreateAccountPanel.setFont(new Font("Tahoma", Font.BOLD, 40));
        goToCreateAccountPanel.setBounds(183, 94, 446, 133);
        Account.add(goToCreateAccountPanel);
        
        
        // --------------------------------------
        // USERMANAGER SECTION
        // --------------------------------------
        
        tFSetUsername = new JTextField("username");
        tFSetUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text field only if the default text is still there
                if ("username".equals(tFSetUsername.getText())) {
                	tFSetUsername.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Set the text to "password" only if the text field is empty
                if (tFSetUsername.getText().isEmpty()) {
                	tFSetUsername.setText("username");
                }
            }
        });
        tFSetUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tFSetUsername.setBackground(new Color(191, 191, 191));
        tFSetUsername.setBounds(196, 113, 416, 51);
        CreateAccount.add(tFSetUsername);
        tFSetUsername.setColumns(10);
        
        tfSetPassword = new JTextField("password");
        tfSetPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text field only if the default text is still there
                if ("password".equals(tfSetPassword.getText())) {
                    tfSetPassword.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Set the text to "password" only if the text field is empty
                if (tfSetPassword.getText().isEmpty()) {
                    tfSetPassword.setText("password");
                }
            }
        });
        tfSetPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tfSetPassword.setColumns(10);
        tfSetPassword.setBackground(new Color(191, 191, 191));
        tfSetPassword.setBounds(196, 207, 416, 51);
        CreateAccount.add(tfSetPassword);
        
        tfSetPasswordCheck = new JTextField("retype password");
        tfSetPasswordCheck.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text field only if the default text is still there
                if ("retype password".equals(tfSetPasswordCheck.getText())) {
                	tfSetPasswordCheck.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Set the text to "password" only if the text field is empty
                if (tfSetPasswordCheck.getText().isEmpty()) {
                	tfSetPasswordCheck.setText("retype password");
                }
            }
        });
        tfSetPasswordCheck.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tfSetPasswordCheck.setColumns(10);
        tfSetPasswordCheck.setBackground(new Color(191, 191, 191));
        tfSetPasswordCheck.setBounds(196, 302, 416, 51);
        CreateAccount.add(tfSetPasswordCheck);
        
        tFUsername = new JTextField("username");
        tFUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text field only if the default text is still there
                if ("username".equals(tFUsername.getText())) {
                	tFUsername.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Set the text to "password" only if the text field is empty
                if (tFUsername.getText().isEmpty()) {
                	tFUsername.setText("username");
                }
            }
        });
        tFUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tFUsername.setColumns(10);
        tFUsername.setBackground(new Color(191, 191, 191));
        tFUsername.setBounds(163, 142, 416, 51);
        Login.add(tFUsername);
        
        tFPassword = new JTextField("password");
        tFPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text field only if the default text is still there
                if ("password".equals(tFPassword.getText())) {
                	tFPassword.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Set the text to "password" only if the text field is empty
                if (tFPassword.getText().isEmpty()) {
                	tFPassword.setText("password");
                }
            }
        });
        tFPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tFPassword.setColumns(10);
        tFPassword.setBackground(new Color(191, 191, 191));
        tFPassword.setBounds(163, 239, 416, 51);
        Login.add(tFPassword);
        
        // --------------------------------------
        // USERMANAGER SECTION - important button
        // --------------------------------------
        
        
        JButton btnCreateAccount = new JButton("CONFIRM");
        btnCreateAccount.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println(tFSetUsername.getText() + " " + tfSetPassword.getText() + " " + tfSetPasswordCheck.getText()); // test
        		userManagerLink.createAccount(tFSetUsername, tfSetPassword, tfSetPasswordCheck, lblCreateAccountError);
        		
        		if (userManagerLink.getAccountCreated() == true) {
        			cardLayoutVariable.show(frame.getContentPane(), "name_Account");
        		} 
        	}
        });
        btnCreateAccount.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnCreateAccount.setBounds(196, 404, 416, 51);
        CreateAccount.add(btnCreateAccount);
        

        
        JButton btnLogin = new JButton("CONFIRM");
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println(tFUsername.getText() + " " + tFPassword.getText()); // test
        		userManagerLink.Login(tFUsername, tFPassword, lblSession, goToAccountPanel, lblTotalTimeStudied, lblLoginError);
        		if (userManagerLink.getLoggedInStatus() == true) {
        			lblusername.setText("Username: " + userManagerLink.getUsername());
        			cardLayoutVariable.show(frame.getContentPane(), "name_MainMenu");
        		}

        	}
        });
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnLogin.setBounds(163, 330, 416, 51);
        Login.add(btnLogin);
        

        
        // --------------------------------------
        // END OF USERMANAGER SECTION
        // --------------------------------------
        
        
        
        // --------------------------------------
        // START OF LEADERBOARDMANAGER SECTION
        // --------------------------------------
        
        leaderboardTableModel = new DefaultTableModel(new Object[]{"ID", "Username", "Sessions", "Total Time"}, 0);

        // Initialize the JTable with the table model
        leaderboardTable = new JTable(leaderboardTableModel);

        // Set the table bounds or add it to a JScrollPane and set the scroll pane bounds
        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboardTable);
        leaderboardScrollPane.setBounds(137, 114, 558, 293); // Set bounds as needed
        Leaderboard.add(leaderboardScrollPane);
        


        // --------------------------------------
        // END OF LEADERBOARDMANAGER SECTION
        // --------------------------------------
        
        
        
        // --------------------------------------
        // COMPONENTS THAT HAVE CODE / ACTIONS
        // --------------------------------------

        JButton sortButton = new JButton("Sort by Time");
        sortButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                // Toggle sort state
                sortBySessions = !sortBySessions;

                // Update the button text based on the sort state
                sortButton.setText(sortBySessions ? "Sort by Time" : "Sort by Sessions");

                // Sort and repopulate the leaderboard
                populateLeaderboardTable();
        	}
        });
        sortButton.setBounds(28, 66, 155, 23);
        Leaderboard.add(sortButton);

        
        
        JButton goToStats = new JButton("YES");
        goToStats.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int totalElapsedTimeInMinutes = countdownTimerlink.getHoursStudied() * 60 + countdownTimerlink.getMinutesStudied();
        		if (userManagerLink.getLoggedInStatus() == false) {
        			statisticsLink.addNewSession(1); 
            		lblSession.setText("Total Sessions: " + Integer.toString(statisticsLink.getTotalNumberOfSessions()));            		
            		lblTotalTimeStudied.setText("Total time studied:" + (totalElapsedTimeInMinutes/60) + "hrs " + (totalElapsedTimeInMinutes % 60) + "mins");
            		
        		} else {
        			// sessions
        			statisticsLink.incrementSessions(userManagerLink.getUsername());
            		
        			int totalSessions = statisticsLink.retrieveSessions(userManagerLink.getUsername());
        			if (totalSessions >= 0) {
        				lblSession.setText("Total Sessions: " + totalSessions);
        			} else {
        				System.out.println("not found");
        			}
        			
        			// time
        			statisticsLink.updateTotalTimeInDatabase(userManagerLink.getUsername(), totalElapsedTimeInMinutes);
        			
        			int retrieveTotalTime = statisticsLink.retrieveTotalTime(userManagerLink.getUsername());
        			lblTotalTimeStudied.setText("Total time studied:" + (retrieveTotalTime/60) + "hrs " + (retrieveTotalTime % 60) + "mins");
        		}
        		
        		cardLayoutVariable.show(frame.getContentPane(), "name_Stats");
        		
        		
        	}
        });
        goToStats.setFont(new Font("Tahoma", Font.BOLD, 45));
        goToStats.setBounds(98, 233, 261, 150);
        EndSessionConfirmation.add(goToStats);
        
        JButton goBacktoStudy = new JButton("NO");
        goBacktoStudy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		countdownTimerlink.setIsRunning(true);
        		cardLayoutVariable.show(frame.getContentPane(), "name_StudyMode");
        	}
        });
        goBacktoStudy.setFont(new Font("Tahoma", Font.BOLD, 45));
        goBacktoStudy.setBounds(440, 233, 293, 150);
        EndSessionConfirmation.add(goBacktoStudy);
        


        JButton PauseResumeTimebtn = new JButton("Pause");
        PauseResumeTimebtn.setFont(new Font("Tahoma", Font.BOLD, 25));
        PauseResumeTimebtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		countdownTimerlink.setIsRunning(!countdownTimerlink.getIsRunning());
                if (countdownTimerlink.getIsRunning()) {
                    System.out.println("Timer resumed");
                    PauseResumeTimebtn.setText("Pause");
                } else {
                    System.out.println("Timer paused");
                    PauseResumeTimebtn.setText("Resume");
                }
        	}
        });
        PauseResumeTimebtn.setBounds(477, 133, 230, 40);
        StudyMode.add(PauseResumeTimebtn);
        
        JButton EndSessionbtn = new JButton("End Session\r\n");
        EndSessionbtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		countdownTimerlink.setIsRunning(false);
        		cardLayoutVariable.show(frame.getContentPane(), "name_EndSessionConfirmation");
        	}
        });
        EndSessionbtn.setFont(new Font("Tahoma", Font.BOLD, 25));
        EndSessionbtn.setBounds(477, 184, 229, 40);
        StudyMode.add(EndSessionbtn);
        

        

        // TEST BUTTON
        JButton goToStudyModePanel_and_starttimer = new JButton("DirectStudyMode");
        goToStudyModePanel_and_starttimer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		countdownTimerlink.countdownTimerMethod(frame, cardLayoutVariable, textField_set_timer_hours, textField_set_time_minutes, lblTimeLeftToStudy, lblError, lblTimeStudied);		
        	}
        });
        goToStudyModePanel_and_starttimer.setFont(new Font("Tahoma", Font.PLAIN, 11));
        goToStudyModePanel_and_starttimer.setBounds(591, 162, 172, 273);
        StudySet.add(goToStudyModePanel_and_starttimer);
        


        // WEBCAM
        webcam.start();
        Timer timer = new Timer(1000, new ActionListener() {
            long lastFaceDetectedTime = System.currentTimeMillis();
            boolean faceNotified = false; // flag to control the popup notification

            @Override
            public void actionPerformed(ActionEvent e) {
                if (webcam.isFaceDetected()) {
                    lastFaceDetectedTime = System.currentTimeMillis();
                    label_isLookingAtCamera.setText("Face Detected: True");
                    countdownTimerlink.setIsFaceDetected(true);
                    faceNotified = false; // reset notification flag when face is detected
                } else if (System.currentTimeMillis() - lastFaceDetectedTime > 10000) {
                    label_isLookingAtCamera.setText("Face Detected: False");
                    countdownTimerlink.setIsFaceDetected(false);
                    if (!faceNotified && countdownTimerlink.getIsRunning()) { // check if the notification has not been already shown
                        faceNotified = true; // set the flag to prevent multiple notifications
                        // Use SwingUtilities.invokeLater to ensure the popup is created on the EDT
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JOptionPane.showMessageDialog(null, "Face not detected!", "Warning", JOptionPane.WARNING_MESSAGE);
                            }
                        });
                    }
                }
            }
        });
        timer.start();
    }
    
    public void goBackToShortcut(CardLayout cardLayoutVariable, JPanel panel, String buttonText, String location) {
        JButton button = new JButton("Go Back");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayoutVariable.show(frame.getContentPane(), location);
        	}
        });
        button.setBounds(10, 11, 89, 23);
        panel.add(button);
    }
    
    private void populateLeaderboardTable() {
        // Clear the existing data
        DefaultTableModel model = (DefaultTableModel) leaderboardTable.getModel();
        model.setRowCount(0); // This will clear all the rows

        LeaderboardManager manager = new LeaderboardManager();
        
        // Get the leaderboard data from the backend and sort it
        List<LeaderboardManager.User> leaderboard;
        if (sortBySessions) {
            leaderboard = manager.getLeaderboardBySessions();
        } else {
            leaderboard = manager.getLeaderboardByTime();
        }
        


        // *
        // Add the user data to the table model in the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                for (LeaderboardManager.User user : leaderboard) {
                    model.addRow(new Object[]{
                        user.getId(),
                        user.getUsername(),
                        user.getSessions(),
                        user.getTotalTime()
                    });
                }
            }
        });
        // *
        
        // (*) code block was generated by Poe: Chat GPT 
        //"Provide a Java snippet to update a JTable with a List using SwingUtilities.invokeLater"
    }
}






