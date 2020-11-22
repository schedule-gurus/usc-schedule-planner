package server;

import exceptions.CollisionException;
import metrics.ClassDistance;
import models.Binaryizer;
import models.Course;
import models.Schedule;
import models.Section;

import java.util.*;

public class Scheduler {

    private List<BitSet> schedules ;
    private List<List<Section>> successfulSchedules;                //0700->2200, M-F, 10 minute bins = 450 bins to represent the whole week\
    
    public Scheduler() {
    	super();
    	ClassDistance.initCoordinates("/Users/niravadunuthula/repo/cs201/usc-schedule-planner/guru-server/src/building_coordinates.txt"); //load the Class Distances
    }

    //Input: Courses Strings wanted, number of schedules to compute RMP and DIST for, and metric to optimize.
    //Output: The best Schedule for the given metric.
    public Schedule buildBestSchedule(String[] courseNames, int sem_id, int schedulesDesired, boolean metric) throws Exception{
    	
    	Course[] courses = new Course[courseNames.length];
    	for(int i = 0; i < courseNames.length; i++) {
    		courses[i] = SOC_API.get_course(courseNames[i], sem_id);
	    }
    	
    	buildValidSchedules(courses);
    	
    	List<List<Section>> desiredSchedules = new ArrayList<List<Section>>(successfulSchedules);
    	Collections.shuffle(desiredSchedules);

    	List<Schedule> randSchedules = new ArrayList<Schedule>();
    	if (schedulesDesired ==1 ){
			System.out.println("Generating " + schedulesDesired + " schedule...");
		}
		else{
			System.out.println("Generating " + schedulesDesired + " schedules...");
		}
    	for(int i = 0; i < schedulesDesired && i < desiredSchedules.size(); i++) {
    		float tempPercent = (float) i / schedulesDesired ;
			System.out.println(tempPercent*100 + "%");
    		randSchedules.add(new Schedule(desiredSchedules.get(i)));
    	}
		System.out.println(100 + "%" + "\nSchedule creation complete.");

		//metric - Rate By RMP, !metric - Distance
    	if(metric) {
    		Collections.sort(randSchedules, new RMPComp());
    	}
    	else {
    		Collections.sort(randSchedules, new DistComp());
    	}

    	System.out.println("Returning " + ((metric) ? "RMP" : "Distance" ) + " optimal schedule...");
    	return randSchedules.get(0);
    }
    
    /*Schedule Comparators*/
    
    //Highest RMP first
    private class RMPComp implements Comparator<Schedule>{
		@Override
		public int compare(Schedule s1, Schedule s2) {
			return (int)(s2.avg_rmp - s1.avg_rmp);
		}
    	
    }
    
    //Lowest Distance first
    private class DistComp implements Comparator<Schedule>{
		@Override
		public int compare(Schedule s1, Schedule s2) {
			return (int)(s1.distance - s2.distance);
		}
    	
    }
    
    //Build a list of valid Schedules by generating all valid combinations
    //Input: Courses wanted, and number of schedules desired
    //Output: a List of all valid schedules
    public List<List<Section>> buildValidSchedules(Course[] courses) throws Exception {
        //INPUT: LIST OF COURSE OBJECTS
    	//OUTPUT: List of possible schedules in bitset format
    	schedules = new ArrayList<>();
        successfulSchedules = new ArrayList<List<Section>>();
    	
        List<List<List<Section>>> courseList =  new ArrayList<List<List<Section>>>(); 
        for (int i = 0; courses.length > i; i++){
        	courseList.add(computeCourseSections(courses, i));
        }
        computeSuccessfulSchedules(courseList, courses.length-1, new ArrayList<Section>());
    	
        
        return successfulSchedules;
    }
    
    //Return all possible lists of Sections that have to be taken for a particular course
    //Input: Courses wanted and the index of the course
    //Output: A list of all possible combinations of required sections for that course
    private List<List<Section>> computeCourseSections(Course[] courses, int i) throws CollisionException{
    	List<List<Section>> courseSections = new ArrayList<List<Section>>();
    	
    	List<Section> lectures = new ArrayList<Section>();
    	List<Section> labs = new ArrayList<Section>();
    	List<Section> discussions = new ArrayList<Section>();
    	Section quiz = null;
    	
    	//get the different types of classes required for a course
    	for(Section s : courses[i].sections) {
    		if(s.type.equals("Lec") || s.type.equals("Lec-Lab") || s.type.equals("Lec-Dis")) {
				lectures.add(s);
			}
			else if(s.type.equals("Lab")) {
				labs.add(s);
			}
			else if(s.type.equals("Dis")) {
				discussions.add(s);
			}
			else if(s.type.contains("Qz")) {
				//We want to ignore quizzes since they are sometimes scheduled at the same time
				//quiz = s;
			}
		}
    	
    	if(lectures.size() > 0) {
        	//Lecture, Lab, Discussion
    		if(labs.size() > 0 && discussions.size() > 0) {          	
            	for(int a = 0; a < lectures.size(); a++) {
            		for(int b = 0; b < labs.size(); b++) {
            			for(int c = 0; c < discussions.size(); c++) {
            				List<Section> sections = new ArrayList<Section>();
            				
            				sections.add(lectures.get(a));
            				sections.add(labs.get(b));
            				sections.add(discussions.get(c));
            				
            				if(quiz != null) {
            					sections.add(quiz);
            				}
            				
            				if(!hasCollision(sections)) {
            					courseSections.add(sections);
            				}
            			}
            		}
            	}
    		}
    		else if (labs.size() > 0) {
    			//Lecture-Lab            	
            	for(int a = 0; a < lectures.size(); a++) {
            		for(int b = 0; b < labs.size(); b++) {
        				List<Section> sections = new ArrayList<Section>();
        				
        				sections.add(lectures.get(a));
        				sections.add(labs.get(b));
        				
        				if(quiz != null) {
        					sections.add(quiz);
        				}
        				
        				if(!hasCollision(sections)) {
        					courseSections.add(sections);
        				}

            		}
            	}
    		}
    		else if (discussions.size() > 0) {
            	//Lecture-Disc     	
            	for(int a = 0; a < lectures.size(); a++) {
            		for(int b = 0; b < discussions.size(); b++) {
        				List<Section> sections = new ArrayList<Section>();
        				
        				sections.add(lectures.get(a));
        				sections.add(discussions.get(b));
        				
        				if(quiz != null) {
        					sections.add(quiz);
        				}
        				
        				if(!hasCollision(sections)) {
        					courseSections.add(sections);
        				}
            		}
            	}
    		}
    		else {
            	//Lecture
            	for(int a = 0; a < lectures.size(); a++) {
    				List<Section> sections = new ArrayList<Section>();
    				
    				sections.add(lectures.get(a));
    				
    				if(quiz != null) {
    					sections.add(quiz);
    				}
    				
    				if(!hasCollision(sections)) {
    					courseSections.add(sections);
    				}
            	}
    		}
    	}
    	
    	return courseSections;
    }
    
    //Recursively generates all possible combos to find valid schedules
    //Input: A List of all the possible sections 
    private void computeSuccessfulSchedules(List<List<List<Section>>> courseList, int index, 
			List<Section> schedule) throws CollisionException{
    	
    	//BC - you finished a schedule
    	if(index == -1) {
    		
    		//if it has no collisions, add it!
    		if(!hasCollision(schedule)) {
    			successfulSchedules.add(schedule);
    		}
    		return;
    	}
    	
    	//recursive for-loop
    	for(int i = 0; i < courseList.get(index).size(); i++) {
    		List<Section> tmp = new ArrayList<Section>(schedule);
    		tmp.addAll(courseList.get(index).get(i));
			computeSuccessfulSchedules(courseList, index-1, tmp);
    	}
    	
    	return;
    }
    
    private boolean hasCollision(List<Section> sectionList) throws CollisionException {
	    BitSet tempSchedule = new BitSet(450);              //creating a empty schedule to fill later
	    List<Integer> tempSIDArray = new ArrayList<>();
	    boolean collision = false;
	    for (Section section : sectionList) {
	        //Looping through course list and adding to tempSchedule
	        try {
	            tempSchedule = addSectionToSchedule(tempSchedule, tempSIDArray, section);
	        } catch (CollisionException e) {
	            //Collision so we need to restart the above loop. possibly ask JaeHyung
	            //System.out.println(e.getMessage());
	            collision = true;
	            break;
	        }
	    }
	    return collision;
    }

    public BitSet addSectionToSchedule(BitSet schedule,  List<Integer> SIDarray, Section classtoAdd) throws CollisionException {            //TODO: Might need to change Bitset -> Object course. Maybe return a course
    	Binaryizer b = new Binaryizer(classtoAdd.day, classtoAdd.start_time, classtoAdd.end_time);
    	
    	int sectionBits = b.output.cardinality();                //Cardinality essentially adds all the positive bits and outputs integer eg. 0101 => 2
        int scheduleBits = schedule.cardinality();                          // Doing this to later compare with the result
        BitSet temp = new BitSet(450);
        temp.or(schedule);        //Copying into temp
        temp.or(b.output);
        int tempBits = temp.cardinality();      //added schedule, to later compare

        //Is there a collision?
        if (tempBits != sectionBits+scheduleBits){
            throw new CollisionException("COLLISION for class " + classtoAdd.course_name + " " + classtoAdd.type + " " + classtoAdd.start_time + ":" + classtoAdd.end_time);
            //COLLISION there is a conflict in the course time with the schedule you have.
            //Break the whole thing and swap this current SessionID with a different one sequence.
            //Go back to the specific course list you collided with and grab a different one
            //I should have a try block for whatever is running this function
        }
        //No collision and we good, everything worked out.
        else{
            SIDarray.add(classtoAdd.session);
            return temp;
        }

    }

    public List<List<Section>> getScheduleList(){
        return successfulSchedules;
    }
    public List<BitSet> getScheduleBits(){
        return schedules;
    }

    public String printSchedules(){
        StringBuilder sb = new StringBuilder();
        for (int j = 0 ; successfulSchedules.size() > j; j++){
            sb.append("Schedule " + j + ": " + successfulSchedules.get(j).toString() + "\n");
        }
        return sb.toString();
    }
}
