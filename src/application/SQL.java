package application;

import java.sql.*;
import java.util.Date;

public class SQL {
	
	public static void main(String [] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
			
			Database db = new Database(conn);
			db.insertArtifact("1234", 1, 1, 1, "This is an important requirement...", "Description here .....");
			
//			db.insertTracelink(1, "uri_test", "uri_test2");
//			
//			Date date = new java.util.Date();
            
//			db.insertVersion("asdfg", "xyz", date);

		}catch(Exception e) {
			System.out.println(e);
		}
	}
}

