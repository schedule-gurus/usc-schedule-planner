package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.Section;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Type;


public class ClassDistance {
	private static HashMap<String, JOpenCageLatLng> cMap = new HashMap<String, JOpenCageLatLng>();
	
	//Read in coordinates from file
	public static void initCoordinates(String path) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Type addressMapType = new TypeToken<HashMap<String, JOpenCageLatLng>>() {}.getType();
		try {
			cMap = gson.fromJson(new FileReader(path), addressMapType);
		} catch (IOException e) {
			System.out.println("IO Exception: " + e.getStackTrace());
		}
	}
	
	private static Comparator<Section> comp = new sectionComp();
	
	//returns the distance between each day's classes in miles given a list of Sections (a schedule)
	public static double computeDistance(List<Section> sections) {
		List<Section> sec = new ArrayList<Section>(sections);
		Collections.sort(sec, comp);
		
		double[] distances = new double[5];
		String[] prev = new String[5];
		String[] days = {"M", "T", "W", "H", "F"};
		
		for(int d = 0; d < days.length; d++) {
			for(int s = 0; s < sec.size(); s++) {
				if(sec.get(s).day.contains(days[d])) {
					
					//ONLINE doesn't have a location, so ignore for distance
					if(sec.get(s).location.equals("ONLINE")) {
						continue;
					}
					
					if(prev[d] == null) {
						prev[d] = locationSplit(sec.get(s).location)[0];
					}
					else {
						String curr = locationSplit(sec.get(s).location)[0];
						distances[d] += haversine(prev[d], curr);
						prev[d] = curr;
					}
				}
			}
		}
		
		double totalDistance = 0;
		for(double d : distances) {
			totalDistance += d;
		}
		
		return totalDistance * 0.6214;
	}
	
	//Section comparator to order them by day, then time
	private static class sectionComp implements Comparator<Section>{
		public int compare(Section s1, Section s2) {
			return s1.start_time - s2.start_time;
		}
	}
	
	//helper function to split location string up and put it in len3-ID Format
	private static String[] locationSplit(String locations) {
		String[] locs = locations.replaceAll("[\\s0-9]", "").split("&");
		for(int i = 0; i < locs.length; i++) {
			if(!locs[i].equals("ONLINE")) {
				locs[i] = locs[i].substring(0, 3);
			}
		}
		
		return locs;
	}
	
	//Haversine distance formula from https://www.geeksforgeeks.org
	private static double haversine(String from, String to) 
	{ 
		try {
			double lat1 = cMap.get(from).getLat();
			double lon1 = cMap.get(from).getLng(); 
	        double lat2 = cMap.get(to).getLat();
	        double lon2 = cMap.get(to).getLng();
	        
	        //System.out.println("from: " + lat1 + " " + lon1 + " to: " + lat2 + "  " + lon2);
	        
			// distance between latitudes and longitudes 
			double dLat = Math.toRadians(lat2 - lat1); 
			double dLon = Math.toRadians(lon2 - lon1); 
			
			// convert to radians 
			lat1 = Math.toRadians(lat1); 
			lat2 = Math.toRadians(lat2); 
			
			// apply formulae 
			double a = Math.pow(Math.sin(dLat / 2), 2) +  
			   Math.pow(Math.sin(dLon / 2), 2) *  
			   Math.cos(lat1) *  
			   Math.cos(lat2); 
			double rad = 6371; 
			double c = 2 * Math.asin(Math.sqrt(a)); 
			return rad * c; 
		}
		catch(NullPointerException e) {
			System.out.println("Inputs to haversine did not have coordinates: " + from + ", " + to);
		}
		return 0;
	} 
	
	//Geocode code to compute coordinates of addresses from https://opencagedata.com/tutorials/geocode-in-java
	public static void computeCoordinates(String from, String to) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Type addressMapType = new TypeToken<Map<String, String>>() {}.getType();
		
		Map<String, String> tmp_map = new HashMap<String, String>();
		try {
			tmp_map = gson.fromJson(new FileReader(from), addressMapType);
		} catch (IOException e) {
			System.out.println("IO Exception: " + e.getStackTrace());
		}
		
		JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("5a2c5c4bbdb149308c4e2664fd250f07");
		
		for (Map.Entry<String,String> entry : tmp_map.entrySet()) {
			JOpenCageForwardRequest request = new JOpenCageForwardRequest(entry.getValue());
			request.setLimit(1);
			request.setNoAnnotations(true);
			JOpenCageResponse response = jOpenCageGeocoder.forward(request);
			
			if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
			    JOpenCageLatLng coordinates = response.getResults().get(0).getGeometry();
			    cMap.put(entry.getKey(), coordinates);
			} else {
				System.out.println("Unable to geocode input address: " + entry.getKey());
			}
			
			try {
				Thread.sleep(1000); // free trial accounts limited to 1 request/sec
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		cMap.put("ONLINE", null);
		
		try {
			gson.toJson(cMap, new FileWriter(to));
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getStackTrace());
		}
	}
	
//	Temporary main function to compute coordinates
//	public static void main(String[] args) {
//		ClassDistance.computeCoordinates("building_addresses.txt", "building_coordinates.txt");
//	}
}
