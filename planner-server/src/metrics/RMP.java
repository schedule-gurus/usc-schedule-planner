package metrics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import models.Instructor;
import models.Section;

public class RMP {
	
	private static String rmp_request_URL = "https://usc-rmp-api.herokuapp.com";
	
	/*
	 * Input: Section[]
	 * Output: avg rmp score
	 */
	public static double computeAvgRMP(List<Section> sections) {
		int sum = 0;
		int num_instructors = 0;
		for(Section s : sections) {
			for(Instructor i : s.instructors) {
				sum += get_rmp(i);
				num_instructors++;		
			}
		}
		
		return (double)sum / num_instructors;
	}
	
	/*
	 * Input: Instructor object
	 * Output: rmp score
	 */
	public static double get_rmp(Instructor i) {
		
		String n = i.first_name.trim() + "+" + i.last_name.trim();
		
		URL rmp_request;
		try {
			rmp_request = new URL(String.format("%s/%s", rmp_request_URL, n));
			HttpURLConnection con = (HttpURLConnection) rmp_request.openConnection();
			con.setRequestMethod("GET");
			
			//add request header (not needed since this is server side)
		    //con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				return Double.parseDouble(in.readLine());
			} else {
				System.out.println("GET Response Code :: " + responseCode);
				System.out.println("GET request failed");
				return -1;
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/*
	 * Input: Full Name of professor (First and last name separated by space or +)
	 * Output: rmp score
	 */
	public static double get_rmp(String fname, String lname) {
		
//		Code for single string input
//		String[] s = name.split(" ");
//		String n = "";
//		
//		//if string input is given with spaces, reformat
//		if(s.length > 1) {
//			for(int i = 0; i < s.length-1; i++) {
//				n += s[i] + '+';
//			}
//			n += s[s.length-1];
//		}
//		else {
//			n = name;
//		}
		
		String n = fname.trim() + "+" + lname.trim();

		URL rmp_request;
		try {
			rmp_request = new URL(String.format("%s/%s", rmp_request_URL, n));
			HttpURLConnection con = (HttpURLConnection) rmp_request.openConnection();
			con.setRequestMethod("GET");
			
			//add request header (not needed since this is server side)
		    con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				return Double.parseDouble(in.readLine());
			} else {
				System.out.println("GET Response Code :: " + responseCode);
				System.out.println("GET request failed");
				return -1;
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	
	
	
	
	
    
	
}
