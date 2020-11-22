package models;

import com.google.gson.annotations.SerializedName;

public class Department {
	
	@SerializedName("Dept_Info")
	public String department;
	public String abbreviation;
	
	@SerializedName("CourseData")
	public Course[] courses;
}
