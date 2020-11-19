package testing;

import java.io.IOException;

import metrics.*;
import models.*;
import server.SOC_API;

public class SOC_Test {
	
	public SOC_Test() throws IOException {
		//checkCourse();
		//checkCourseHelpers();
		checkInstructor();
		checkDistance();
		
	}
	
	private void checkCourse() throws IOException {
		//check get_department
		Department d = SOC_API.get_department("csci", 20201);
		Course c1 = d.courses[0];
		
		for(Section s : c1.sections) {
			System.out.println(c1.department+"-"+c1.id.toString() + " " + s.id + " " + 
					s.title + " " + s.start_time+"-"+s.end_time + " " + s.location + " " + s.type + " " + 
					s.day + " "+ s.units); 
		}
		System.out.println();
	}
	
	private void checkInstructor() throws IOException {
		//check get_section - CS 201 Fall 2020 
		Section s1 = SOC_API.get_section("ee-109", 31291, 20203);
		System.out.println(/*s1.department+"-"+s1.id.toString() + " " + s1.id + " " + */s1.title + 
				" " + s1.start_time+"-"+s1.end_time + " " + s1.location + " " + s1.type + " " + s1.day + " " + s1.units);
		
		//print Instructor info
		for(Instructor i : s1.instructors) {
			System.out.println(i.first_name + " " + i.last_name + " RMP: " + RMP.get_rmp(i));
		}
		System.out.println();
	}
	
	private void checkDistance() throws IOException {
		Department d = SOC_API.get_department("csci", 20201);
		Course c1 = d.courses[0];
		
		for(Section s : c1.sections) {
			System.out.println(c1.department+"-"+c1.id.toString() + " " + s.id + " " + 
					s.title + " " + s.start_time+"-"+s.end_time + " " + s.location + " " + s.type + " " + 
					s.day + " "+ s.units); 
		}
		System.out.println();
		
		//check Distance API assuming your schedule is given by c1's sections
		ClassDistance.initCoordinates("building_coordinates.txt");
		System.out.println("The distance for this schedule is " + ClassDistance.computeDistance(c1.sections) + " miles.");
		System.out.println();
	}
	
	private void checkCourseHelpers() throws IOException {
		Course c2 = SOC_API.get_course("csci-201", 20203);
		for(Section s : c2.sections) {
			System.out.println(c2.department+"-"+c2.id.toString() + " " + s.id + " " + 
					s.title + " " + s.start_time+"-"+s.end_time + " " + s.location + " " + s.type + " " + 
					s.day + " " + s.units); 
		}
		System.out.println();
		
		System.out.println(c2.department+ "-" + c2.id + " has a LEC section: " + c2.has_lecture());
		System.out.println(c2.department+ "-" + c2.id + " has a DISC section: " + c2.has_discussion());
		System.out.println(c2.department+ "-" + c2.id + " has a LAB section: " + c2.has_lab());
		System.out.println(c2.department+ "-" + c2.id + " has a QUIZ section: " + c2.has_quiz());
		System.out.println();
	}
	
//	public static void main(String[] args) throws IOException {
//		
//		//check get_department
//		Department d = SOC_API.get_department("csci", 20201);
//		Course c1 = d.courses[0];
//		
//		for(Section s : c1.sections) {
//			System.out.println(c1.department+"-"+c1.id.toString() + " " + s.id + " " + 
//					s.title + " " + s.start_time+"-"+s.end_time + " " + s.location + " " + s.type + " " + 
//					s.day + " "+ s.units); 
//		}
//		System.out.println();
//		
//		//check Distance API assuming your schedule is given by c1's sections
//		ClassDistance.initCoordinates("building_coordinates.txt");
//		System.out.println("The distance for this schedule is " + ClassDistance.computeDistance(c1.sections) + " miles.");
//		System.out.println();
//		
//		
//		//check get_course - CS 201 Fall 2020
//		Course c2 = SOC_API.get_course("csci-201", 20203);
//		
//		for(Section s : c2.sections) {
//			System.out.println(c2.department+"-"+c2.id.toString() + " " + s.id + " " + 
//					s.title + " " + s.start_time+"-"+s.end_time + " " + s.location + " " + s.type + " " + 
//					s.day + " " + s.units); 
//		}
//		System.out.println();
//		
//		//check get_section - CS 201 Fall 2020 
//		Section s1 = SOC_API.get_section("ee-109", 31291, 20203);
//		System.out.println(/*s1.department+"-"+s1.id.toString() + " " + s1.id + " " + */s1.title + 
//				" " + s1.start_time+"-"+s1.end_time + " " + s1.location + " " + s1.type + " " + s1.day + " " + s1.units);
//		
//		//print Instructor info
//		for(Instructor i : s1.instructors) {
//			System.out.println(i.first_name + " " + i.last_name + " RMP: " + RMP.get_rmp(i));
//		}
//		System.out.println();
//		
//		
//		//check the helper functions for a course
//		System.out.println(c2.department+ "-" + c2.id + " has a LEC section: " + c2.has_lecture());
//		System.out.println(c2.department+ "-" + c2.id + " has a DISC section: " + c2.has_discussion());
//		System.out.println(c2.department+ "-" + c2.id + " has a LAB section: " + c2.has_lab());
//		System.out.println(c2.department+ "-" + c2.id + " has a QUIZ section: " + c2.has_quiz());
//		System.out.println();
//		
//
//	}
}
