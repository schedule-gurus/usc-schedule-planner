package soc_deserializers;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import models.Course;
import models.Section;

public class CourseDeserializer implements JsonDeserializer<Course>{
private Gson gson;
	
	public CourseDeserializer() {
		gson = new GsonBuilder().registerTypeAdapter(Section.class, new SectionDeserializer()).create();
	}

	@Override
	public Course deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject cData = (JsonObject) json.getAsJsonObject().get("CourseData");
		Course c = new Course();
		c.department = cData.get("prefix").getAsString();
		c.id = cData.get("number").getAsInt();
		c.title = cData.get("title").getAsString();
		
		
		//if SectionData is a single section or a list of sections
		if(cData.get("SectionData").isJsonArray()) {
			try {
				c.sections = gson.fromJson(cData.get("SectionData"), Section[].class);
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println(c.department + "-" + c.id);
			}
		}
		else {
			c.sections = new Section[1];
			c.sections[0] = gson.fromJson(cData.get("SectionData"), Section.class);
		}
		
		//update section objects with course name
		for(Section s : c.sections) {
			s.course_name = c.department + "-" + c.id;
		}
		
		return c;		
	}
}
