package soc_request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import soc_deserializers.DepartmentDeserializer;

public class Instructor {
	public String first_name;
	public String last_name;
	public double rmp;
	
	public double getRMP() throws IOException {
		final String rmp_request_URL = "";
		URL rmp_request = new URL(rmp_request_URL);
		HttpURLConnection con = (HttpURLConnection) rmp_request.openConnection();
		
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			
			Gson g = new Gson();
			return 0;
		} else {
			System.out.println("GET request failed");
			return -1;
		}
	}
}
