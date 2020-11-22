package models;

import com.google.gson.annotations.SerializedName;

public class Course {
	@SerializedName("prefix")
	public String department;
	
	@SerializedName("number")
	public Integer id;
	
	@SerializedName("SectionData")
	public Section[] sections;
	
	public String title;
	
	//helper variables
	int lecture_capacity = 0;
	int lecture_registered = 0;
	int lab_capacity = 0;
	int lab_registered = 0;
	int discussion_capacity = 0;
	int discussion_registered = 0;
	int quiz_capacity = 0;
	int quiz_registered = 0;
	
	//initialize
	public void prepare() {
		for(Section s : sections) {
			if(s.type.equals("Lec") || s.type.equals("Lec-Lab") || s.type.equals("Lec-Dis")) {
				lecture_capacity += s.capacity;
				lecture_registered += s.registered;
			}
			else if(s.type.equals("Lab")) {
				lab_capacity += s.capacity;
				lab_registered += s.registered;
			}
			else if(s.type.equals("Dis")) {
				discussion_capacity += s.capacity;
				discussion_registered += s.registered;
			}
			else if(s.type.equals("Qz")) {
				
			}
		}
	}
	
	//helper functions to determine the number of sections to take
	public boolean has_lecture() {
		return lecture_capacity > 0;
	}
	
	public boolean has_lab() {
		return lab_capacity > 0;
	}
	
	public boolean has_discussion() {
		return discussion_capacity > 0;
	}
	public boolean has_quiz() {
		return lab_capacity > 0;
	}
}