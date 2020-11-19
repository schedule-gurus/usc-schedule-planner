package models;

import metrics.RMP;

public class Section {
	public Integer id;
	public Integer session; //what semester the class is held
	public String title;
	public String type; //Lec, Lec-Lab, Lec-Dis, Lab, Dis, Qz
	public String units;
	
	public Integer capacity;
	public Integer registered;
	
	public String day;
	public Integer start_time;
	public Integer end_time;
	public String location;
	
	public Instructor[] instructors;	
	
	public String toString() {
		return title + " " + start_time+"-"+end_time + " " + location + " " + type + " " + day + " " + units;
	}
}
