import java.sql.*;
import java.io.*;

public class JDBCTest6
{
	public static void main (String[] args)
	{
		String db = "jdbc:mysql://localhost/StudentGrades";
		String user = "root";
		String pwd = "root";
		String sql = "{CALL GetGrade2(?,?,?)}";

		try (Connection conn = DriverManager.getConnection(db, user, pwd);
			  CallableStatement stmt = conn.prepareCall(sql);) {
			//stmt.setString(1, "Cooper");
			stmt.setString(1,  "Wolowitz");
			stmt.setString(2, "B");
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.executeUpdate();
			int res = stmt.getInt(3);
			System.out.println(res);
			//ResultSet rs = stmt.executeQuery();
			//while (rs.next())
			//				System.out.println (
			//					rs.getString("lname") + "\t" +
			//					rs.getString("lettergrade")  );
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}
	}
}
