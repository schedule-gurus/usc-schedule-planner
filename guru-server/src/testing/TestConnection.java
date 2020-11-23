import java.sql.*;
import java.io.*;

public class TestConnection {
	public static void main (String[] args)
	{
				
		// test connection
		String JdbcURL = "jdbc:mysql://303.itpwebdev.com:3306/pluiz_usc_schedule_db";
        String Username = "pluiz_sachi";
        String password = "csci201final";

		try 
  		{
			Connection conn=DriverManager.getConnection(JdbcURL, Username, password);
  			System.out.println("Success");
  		}
  		catch(SQLException e)
  		{
  			System.out.println("Failure");
  		}
		
		// test selecting from instructors table
		String smt = "SELECT * FROM instructors;";
		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); 
  				PreparedStatement ps = conn.prepareStatement(smt);)
		{
			ResultSet rs = ps.executeQuery();
  			while (rs.next())
  			{
  				System.out.println(rs.getString("first"));
  			}
		}
		catch(SQLException e)
  		{
  			System.out.println("Exception when getting instructors' first names");
  		}
		
		// test inserting into instructors table
		String fname = "TestingFirst";
		String lname = "TestingLast";
		double rmp = 1.5;
		
		String smt2 = "INSERT INTO instructors (first, last, rmp) VALUES (?, ?, ?);";
  		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); 
  				PreparedStatement ps = conn.prepareStatement(smt2);)
  		{
  			ps.setString(1, fname);
  			ps.setString(2, lname);
  			ps.setDouble(3, rmp);
  			ps.execute();
  		}
  		catch(SQLException e)
  		{
  			System.out.println("Exception when inserting into instructors table");
  		}
  		
  		// test selecting specific instructors and getting first name
  		String first = "Allan";
  		String last = "Weber";
  		//String smt3 = "IF NOT EXISTS (SELECT * FROM instructors WHERE first = ? and last = ?) "; 
  		String smt3 = "SELECT ID FROM instructors WHERE first = ? AND last = ?;";
  		//String smt3 = "INSERT IGNORE into instructors WHERE first = ? AND last = ?;";
  		int num = 0;
  		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); 
  				PreparedStatement ps = conn.prepareStatement(smt3);)
		{
  			
  			ps.setString(1,  first);
  			ps.setString(2,  last);
  			ps.setString(3,  first);
  			ps.setString(4,  last);
  			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			num = rs.getInt("ID");

		}
		catch(SQLException e)
  		{
  			System.out.println("Exception when getting instructors' IDs");
  		}
  		
  		System.out.println(num);
  		
	}
}
