package soc_deserializers;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import soc_request.Course;
import soc_request.Department;

public class DepartmentDeserializer implements JsonDeserializer<Department>{
	private Gson gson;
	
	public DepartmentDeserializer() {
		gson = new GsonBuilder().registerTypeAdapter(Course.class, new CourseDeserializer()).create();
	}

	@Override
	public Department deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject departmentContainer = json.getAsJsonObject();
		JsonObject department = (JsonObject) departmentContainer.get("Dept_Info"); 
		JsonObject courses = (JsonObject) departmentContainer.get("OfferedCourses");
		
		Department dep = new Department();
		dep.department = department.get("department").getAsString();
		dep.abbreviation = department.get("abbreviation").getAsString();
		
		//if courses is a single course or a list of courses
		if(courses.get("course").isJsonArray()) {
			dep.courses = gson.fromJson(courses.get("course"), Course[].class);
		}
		else {
			dep.courses = new Course[1];
			dep.courses[0] = gson.fromJson(courses.get("course"), Course.class);
		}
		
		//initialize helper variables for the course
		for(Course c : dep.courses) {
			c.prepare();
		}
		
		return dep;
	}

}
