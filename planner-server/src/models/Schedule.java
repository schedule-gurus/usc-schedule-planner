package models;

import metrics.ClassDistance;

public class Schedule {
	Section[] sections;
	Double distance;
	
	public Schedule(Section[] s) {
		sections = s;
		distance = ClassDistance.computeDistance(s);
	}
}
