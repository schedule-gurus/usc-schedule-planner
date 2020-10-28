package soc_tests;

import java.io.IOException;

import soc_request.*;

public class DeserializationTest {
	public static void main(String[] args) throws IOException {
		
		//check get_department
		Department d = SOC_API.get_department("csci", 20201);
		Course c1 = d.courses[0];
		
		for(Section s : c1.sections) {
			System.out.println(c1.department+"-"+c1.id.toString() + " " + s.id + " " + s.title + " " + s.start_time+":"+s.end_time + " " + s.type + " " + s.day + " "+ s.units); 
		}
		
		
		
		//check get_course - CS 201
		Course c2 = SOC_API.get_course("csci-201", 20203);
		
		for(Section s : c2.sections) {
			System.out.println(c2.department+"-"+c2.id.toString() + " " + s.id + " " + s.title + " " + s.start_time+":"+s.end_time + " " + s.type + " " + s.day + " " + s.units); 
		}
	}
}
