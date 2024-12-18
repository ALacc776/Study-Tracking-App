package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	public static Connection connect() {
		
		Connection con = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			con  = DriverManager.getConnection("jdbc:sqlite:DBStudyApp.db");
			System.out.println("Connected!");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
		System.out.println(e+"");
		}
		
		return con;
	}

	 public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection("jdbc:sqlite:DBStudyApp.db");
	    }

}
// ADAPTED FROM (www.javatpoint.com, 2021)

