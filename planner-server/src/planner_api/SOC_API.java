package planner_api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.*;
import soc_deserializers.DepartmentDeserializer;

public class SOC_API{
	static final String soc_request_URL = "https://web-app.usc.edu/web/soc/api/classes";
	
	/*
	 * Input: String course_id, int section_id, int semester_id
	 * Return: the queried section if it exists in that course that semester, otherwise null.
	 * Throws: IOException if course id is incorrectly formatted
	 */
	public static Section get_section(String course_id, int section_id, int semester_id) throws IOException {
		Course c = get_course(course_id, semester_id);
		
		for(Section s : c.sections) {
			if(s.id == section_id) {
				return s;
			}
		}
		return null;
	}
	
	/*
	 * Input: String course_id, int semester_id
	 * Return: the queried course if it exists in that course that semester, otherwise null.
	 * Throws: IOException if course id is incorrectly formatted 
	 */
	public static Course get_course(String course_id, int semester_id) throws IOException {
		//split the course prefix and number
		String[] course_info = course_id.split("-");
		
		//error checking
		if(course_info.length != 2) {
			throw new IOException("Malformed Course ID");
		}
		if(!course_info[0].matches("[a-zA-Z]+") || !course_info[1].matches("[0-9]+")) {
			throw new IOException("Malformed Course ID");
		}
		
		//get department
		Course[] dpt_courses = get_department_courses(course_info[0], semester_id);
		for(Course c : dpt_courses){
			if(c.id == Integer.parseInt(course_info[1])) {
				return c;
			}
		}
		
		return null;
	}
	
	/*
	 * Input: String department_id, int semester_id
	 * Return: the queried department for the given semester, or null if it doesn't exist
	 * Throws: IOException if an exception occurs while querying the SOC database 
	 */

	public static Department get_department(String department_id, int semester_id) throws IOException {
		URL soc_request = new URL(String.format("%s/%s/%d", soc_request_URL, department_id, semester_id));
		HttpURLConnection con = (HttpURLConnection) soc_request.openConnection();
		
		con.setRequestMethod("GET");
		
		//add request header (not needed since this is server side)
	    //con.setRequestProperty("User-Agent", "Mozilla/5.0");
	    
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			
			Gson g = new GsonBuilder().registerTypeAdapter(Department.class, new DepartmentDeserializer()).create();
			return g.fromJson(in, Department.class);
		} else {
			System.out.println("GET request failed");
			return null;
		}
	}
	
	/*
	 * Input: String department_id, int semester_id
	 * Return: the courses in the queried department for the given semester, or null if it doesn't exist
	 * Throws: IOException if an exception occurs while querying the SOC database 
	 */
	private static Course[] get_department_courses(String department_id, int semester_id) throws IOException {
		Department d = get_department(department_id, semester_id);
		
		if(d != null) {
			return d.courses;
		}
		return null;
	}
}

	
