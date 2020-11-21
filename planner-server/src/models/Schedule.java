package models;

import metrics.ClassDistance;
import metrics.RMP;

public class Schedule {
	public Section[] sections;
	public Double distance;
	public Double avg_rmp;
	
	public Schedule(Section[] s) {
		sections = s;
		distance = ClassDistance.computeDistance(s);
		avg_rmp = RMP.computeAvgRMP(s);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < sections.length; i++){
            sb.append("Sections: " + sections + "| Avg RMP: " + avg_rmp + "| Avg Dist: " + distance);
        }
        return sb.toString();
	}
}
