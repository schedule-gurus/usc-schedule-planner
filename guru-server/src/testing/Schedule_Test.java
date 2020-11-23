package testing;

import java.io.IOException;

import models.Course;
import server.SOC_API;
import server.Scheduler;

public class Schedule_Test {
	
	public static void main(String[] args) {
		try {
			Schedule_Test s = new Schedule_Test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Schedule_Test() throws Exception {
		bestSchedule();
		allPossibleSchedules();
    }
	
	 public void bestSchedule() throws Exception {
	    	int sem_id = 20201;
	        String[] courseNames = {"csci-201", "ee-109", "csci-270", "hist-250"};

	        Scheduler s = new Scheduler();
	        System.out.println(s.buildBestSchedule(courseNames, sem_id, 10, true));

	    }
	
    public void allPossibleSchedules() throws Exception {
    	int sem_id = 20203;
        String[] courseStrings = {"csci-201", "ee-109", "csci-270", "hist-250"};
        Course[] courses = new Course[courseStrings.length];
        
        for(int i = 0; i < courseStrings.length; i++) {
        	courses[i] = SOC_API.get_course(courseStrings[i], sem_id);
        }

        Scheduler s = new Scheduler();
        s.buildValidSchedules(courses);

        System.out.println(s.printSchedules());

    }
}