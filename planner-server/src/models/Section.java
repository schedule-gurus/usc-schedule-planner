package models;

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
}
