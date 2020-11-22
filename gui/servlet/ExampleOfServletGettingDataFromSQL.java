import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;



@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet
{
	public class Student
	{
		public int id;
		public String fname;
		public String lname;
		public Student(int iid, String ifname, String ilname)
		{
			id = iid;
			fname = ifname;
			lname = ilname;
		}
		
		public String printStudent()
		{
			return id + " " + fname + " " + lname;
		}
		
	}
	
	//private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		
		// get data from database
		String db = "jdbc:mysql://localhost/StudentGrades";
		String user = "root";
		String pwd = "root";
		String sql = "SELECT * FROM Student";
		ArrayList<Student> studentlist = new ArrayList<Student>();
		
		// test connection
		Connection con = null;
	    try 
	    {
	         System.out.println("Connecting to database..............."+db);
	         con=DriverManager.getConnection(db, user, pwd);
	         System.out.println("Connection is successful!!!!!!");
	    }
	    catch(Exception e) 
	    {
	    	System.out.println("Connection error! ");
	         e.printStackTrace();
	    }

		try (Connection conn = DriverManager.getConnection(db, user, pwd);
		      Statement st = conn.createStatement();
			  ResultSet rs = st.executeQuery(sql);) 
		{
			while (rs.next())
			{
				int newid = rs.getInt("studentID");
				String newfirst = rs.getString("fname");
				String newlast = rs.getString("lname");
				Student temp = new Student(newid, newfirst, newlast);
				studentlist.add(temp);
			}
				
		} 
		catch (SQLException sqle) 
		{
			System.out.println ("SQLException: " + sqle.getMessage());
		}
		
		// check studentlist
		for(int i = 0; i < studentlist.size(); i++)
		{
			System.out.println(studentlist.get(i).printStudent());
		}
		
		// print results!
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Test Servlet with MySQL</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Student Information Table</h1>");
		out.println("<table>");
		out.println("<tr><th>Student ID</th>");
		out.println("<th>First Name</th>");
		out.println("<th>Last Name</th></tr>");
		for(int i = 0; i < studentlist.size(); i++)
		{
			out.println("<tr>");
			out.print("<td>" + studentlist.get(i).id + "</td>");
			out.print("<td>" + studentlist.get(i).fname + "</td>");
			out.print("<td>" + studentlist.get(i).lname + "</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		
	}
}
