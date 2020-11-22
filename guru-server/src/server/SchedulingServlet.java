package server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.util.*;




//import org.json.JSONArray;
//import org.json.JSONException;
////import org.json.JSONException;
//import org.json.JSONObject;

import models.Schedule;
import models.Section;



@WebServlet("/SchedulingServlet")
public class SchedulingServlet extends HttpServlet
{
	
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
	  response.setHeader("Access-Control-Allow-Methods", "*");
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		// connect to database
		String JdbcURL = "jdbc:mysql://303.itpwebdev.com:3306/pluiz_usc_schedule_db";
        String Username = "pluiz_sachi";
        String password = "csci201final";
        
        
        
        // get parameters from request
        String metric = request.getParameter("metric");
        String c0 = request.getParameter("c0");
        String c1 = request.getParameter("c1");
        String c2 = request.getParameter("c2");
        String c3 = request.getParameter("c3");
        String c4 = request.getParameter("c4");
        String c5 = request.getParameter("c5");
        boolean valforopt;
        if(metric == null) {
        	System.out.println("metric null");
        	return;
        }
        if(metric.compareTo("1") == 0)
        {
        	valforopt = true;
        }
        else
        {
        	valforopt = false;
        }

        // add valid class strings to list
        ArrayList<String> pruneList = new ArrayList<String>();
        if(c0.compareTo("") != 0)
        {
        	pruneList.add(c0);
        }
        if(c1.compareTo("") != 0)
        {
        	pruneList.add(c1);
        }
        if(c2.compareTo("") != 0)
        {
        	pruneList.add(c2);
        }
        if(c3.compareTo("") != 0)
        {
        	pruneList.add(c3);
        }
        if(c4.compareTo("") != 0)
        {
        	pruneList.add(c4);
        }
        if(c5.compareTo("") != 0)
        {
        	pruneList.add(c5);
        }
        
        
      	String[] courseNames = new String[pruneList.size()];
        for(int i = 0; i < pruneList.size(); i++)
        {
        	courseNames[i] = pruneList.get(i);
        }
      	


      	

      	// we call the algorithm function, 
      	// public Schedule buildBestSchedule(Course[] courses, int schedulesDesired, boolean metric)
		    // get the Schedule
        Scheduler scheduler = new Scheduler();
      	Schedule myClasses = null;
		try {
			myClasses = scheduler.buildBestSchedule(courseNames, 20203, 1, valforopt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      	// save classes into database sections table
      	for(int i = 0; i < myClasses.sections.size(); i++) 
      	{
			Section currSection = myClasses.sections.get(i);

			// get instructor data ready
      		String prof_first = currSection.instructors[0].first_name;
      		String prof_last = currSection.instructors[0].last_name;
      		double prof_rmp = currSection.instructors[0].rmp;
      		
      		String help = "INSERT INTO instructors (first, last, rmp) VALUES (?, ?, ?); ";
      		try
      		{
      			Connection conn=DriverManager.getConnection(JdbcURL, Username, password); 
      			System.out.println("Yay!");
      		} catch(SQLException e) {
      			System.out.println("Frick");
      		}
      		
      		//check if the instructor exists
      		int prof_id = 0;
      		String sql1 = "SELECT ID FROM instructors WHERE first = ? AND last = ?;";
      		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); PreparedStatement ps = conn.prepareStatement(sql1);)
      		{
      			ps.setString(1, prof_first);
      			ps.setString(2, prof_last);
      			ResultSet rs = ps.executeQuery();
      			
      			rs.next();
      			prof_id = rs.getInt("ID");
      		}
      		catch(SQLException e)
      		{
      			e.printStackTrace();
      			System.out.println("Exception when getting instructorID");
      		}

      		if(prof_id == 0) {
	      		// insert into instructors table
	      		String sql2 = "INSERT IGNORE INTO instructors (first, last, rmp) VALUES (?, ?, ?); ";
	      		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); PreparedStatement ps = conn.prepareStatement(sql2);)
	      		{
	      			ps.setString(1, prof_first);
	      			ps.setString(2, prof_last);
	      			ps.setDouble(3, prof_rmp);
	//      			ps.setString(4, prof_first);
	//      			ps.setString(5, prof_last);
	      			ps.execute();
	      		}
	      		catch(SQLException e)
	      		{
	      			e.printStackTrace();
	      			System.out.println(prof_first + " " + prof_last);
	      			System.out.println("Exception when inserting into instructors table");
	      		}
	
	      		// get instructorID
	      		String sql3 = "SELECT ID FROM instructors WHERE first = ? AND last = ?;";
	      		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); PreparedStatement ps = conn.prepareStatement(sql3);)
	      		{
	      			ps.setString(1, prof_first);
	      			ps.setString(2, prof_last);
	      			ResultSet rs = ps.executeQuery();
	      			
	      			rs.next();
	      			prof_id = rs.getInt("ID");
	      		}
	      		catch(SQLException e)
	      		{
	      			e.printStackTrace();
	      			System.out.println("Exception when getting instructorID");
	      		}
      		}

      		// add section info into sections table
      		String sql = "INSERT IGNORE INTO sections ";
      		sql += "(ID, session, title, type, startTime, endTime, instructor, location, dotw, abrv) " ;
      		sql += "VALUES (?,?,?,?,?,?,?,?,?,?); ";
      		try (Connection conn=DriverManager.getConnection(JdbcURL, Username, password); PreparedStatement ps = conn.prepareStatement(sql);)
      		{
      			ps.setInt(1, currSection.id);
      			ps.setInt(2, currSection.session);
      			ps.setString(3, currSection.title);
      			ps.setString(4, currSection.type);
      			ps.setInt(5, currSection.start_time);
      			ps.setInt(6, currSection.end_time);
      			ps.setInt(7, prof_id);
      			ps.setString(8, currSection.location);
      			ps.setString(9, currSection.day);
      			ps.setString(10, currSection.course_name);
      			ps.executeUpdate();
      		}	
      		catch(SQLException e)
      		{
      			e.printStackTrace();
				System.out.println("Exception when adding section info into Sections table of database");
      		}		
      	}

      	// get classIDs
      	// just loop through myClasses and get each entry's .id Integer


      	// save classIDs as JSON, then send back out. done below:



        // This is an example of using putting an array of ints into a JSON, and it worked :)
	/*
	Integer[] test = new Integer[5];
	    for(int i = 0; i < 5; i++)
	    {
	    	test[i] = i;
	    }
	    //test[0] = 1;
	    //creates JSON object that will be put into the JSON array
	    JSONObject jo = new JSONObject();
    	
    	//populates JSON object with course IDs
    	for(int i = 0; i < test.length; i++)
    	{
    		//jo.put("courseID", test[i]);
    		jo.append("courseID", test[i]);
    	}
    	//creates JSON array that will be populated with JSON object that represents a user's class IDs
    	JSONArray ja = new JSONArray();
    	//populates JSON object with user's packaged class IDs
    	ja.put(jo);
    	
    	//prints out json object within the json array we're passing pilar
    	for (int i = 0; i < ja.length(); i++)
    	{
    	    JSONObject pet = ja.getJSONObject(i);
    	    System.out.println(pet);
    	}
      */

        Gson jo = new GsonBuilder().setPrettyPrinting().create();
//        JSONArray ja = new JSONArray();
        int[] ja = new int[myClasses.sections.size()];
        for(int i = 0; i < myClasses.sections.size(); i++)
        {
	          int currid = myClasses.sections.get(i).id;
	          ja[i] = currid;
//	          try {
////				jo.append("classIDs", currid);
//	        	  ja
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
//        ja.put(jo);
        String json = jo.toJson(ja);
        
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8888");
//        response.setHeader("Access-Control-Allow-Methods", "GET");
        
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.println(json);
        out.flush();
	    
    
    }

}
