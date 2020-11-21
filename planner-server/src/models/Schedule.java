package models;

import java.util.List;

import metrics.ClassDistance;
import metrics.RMP;

public class Schedule {
	public List<Section> sections;
	public Double distance;
	public Double avg_rmp;
	
	public Schedule(List<Section> s) {
		sections = s;
		distance = ClassDistance.computeDistance(s);
		avg_rmp = RMP.computeAvgRMP(s);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sections: " + sections + "| Avg RMP: " + avg_rmp + "| Avg Dist: " + distance);
        return sb.toString();
	}
}
