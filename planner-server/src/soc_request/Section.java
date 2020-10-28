package soc_request;

public class Section {
	public Integer id;
	public Integer session; //what semester the class is held
	public String title;
	public String type; //Lec, Lec-Lab, Lec-Dis, Lab, Dis, Qz
	public String units;
	
	public Integer capacity;
	public Integer registered;
	
	public String day;
	public String start_time;
	public String end_time;
	public String location;
	
	public Instructor[] instructors;	
}
