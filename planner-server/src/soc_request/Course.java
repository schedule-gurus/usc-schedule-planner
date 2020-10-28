package soc_request;

import com.google.gson.annotations.SerializedName;

public class Course {
	@SerializedName("prefix")
	public String department;
	
	@SerializedName("number")
	public Integer id;
	
	@SerializedName("SectionData")
	public Section[] sections;
	
	public String title;
	
	//helper variables = sdata.get(
	int lecture_capacity = 0;
	int lecture_registered = 0;
	int lab_capacity = 0;
	int lab_registered = 0;
	int discussion_capacity = 0;
	int discussion_registered = 0;
	
//	//initialize
//	public void prepare() {
//		for(Section s : sections) {
//			if(s.type.equals("Lab") || s.type.equals("Lab") || s.type.equals("Lab")) {
//				
//			}
//		}
//	}
//	
//	public boolean has_lecture() {
//		
//	}
//	public boolean has_lab() {
//		
//	}
//	public boolean has_discussion() {
//	
//	}
}

//public class Course {
//	String prefix;
//	Integer number;
//	Section[] SectionData;
//	String title;
//	
//	Boolean has_lecture;
//	Boolean has_lab;
//	Boolean has_discussion;
//	
//	Boolean is_full;
//	Integer lecture_capacity;
//	Integer lecture_registered;
//	Integer lab_capacity = 0;
//	Integer lab_registered = 0;
//	Integer discussion_capacity = 0
//	Integer discussion_registered = 0
//}
