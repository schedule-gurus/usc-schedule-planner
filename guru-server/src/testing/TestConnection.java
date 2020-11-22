package testing;

import java.sql.*;
import java.io.*;

public class TestConnection {
	public static void main (String[] args)
	{
		String JdbcURL = "jdbc:mysql://303.itpwebdev.com:3306/pluiz_usc_schedule_db";
        String Username = "pluiz_sachi";
        String password = "csci201final";
		String sql = "SELECT * FROM Student";

		try 
  		{
			Connection conn=DriverManager.getConnection(JdbcURL, Username, password);
  			System.out.println("Success");
  		}
  		catch(SQLException e)
  		{
  			System.out.println("Failure");
  		}
		
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
		
		String fname = "Spongebob";
		String lname = "Squarepants";
		double rmp = 4.4;
		
		String smt2 = "INSERT INTO instructors (first, last, rmp) VALUES (?, ?, ?);";
  		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); 
  				PreparedStatement ps = conn.prepareStatement(smt2);)
  		{
  			ps.setString(1, fname);
  			//System.out.println("set first");
  			ps.setString(2, lname);
  			//System.out.println("set last");
  			ps.setDouble(3, rmp);
  			System.out.println("Before execute");
  			ps.execute();
  			System.out.println("Success");
  		}
  		catch(SQLException e)
  		{
  			System.out.println("Exception when inserting into instructors table");
  		}
	}
}
