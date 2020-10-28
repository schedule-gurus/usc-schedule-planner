package soc_deserializers;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import soc_request.Instructor;
import soc_request.Section;

public class SectionDeserializer implements JsonDeserializer<Section> {
	private Gson gson;
	
	public SectionDeserializer() {
		gson = new Gson();
	}
	
	public Section deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject sdata = json.getAsJsonObject();
		
		Section s = new Section();
		
		s.id = sdata.get("id").getAsInt();
		s.session = sdata.get("session").getAsInt(); //what semester the class is held
		s.title = sdata.get("title").getAsString();
		s.type = sdata.get("type").getAsString(); //Lec, Lec-Lab, Lec-Dis, Lab, Dis, Qz
		s.units = sdata.get("units").getAsString();
		
		s.capacity = sdata.get("spaces_available").getAsInt();
		s.registered = sdata.get("number_registered").getAsInt();
		
		//handles an edge case of duplicate times and days + missing days
		if(sdata.get("day").isJsonArray()) {
			s.day = sdata.get("day").getAsJsonArray().get(0).getAsString();
			s.start_time = sdata.get("start_time").getAsJsonArray().get(0).getAsString();
			s.end_time = sdata.get("end_time").getAsJsonArray().get(0).getAsString();
		}
		else if(!sdata.get("day").isJsonObject()){
			s.day = sdata.get("day").getAsString();
			s.start_time = sdata.get("start_time").getAsString();
			s.end_time = sdata.get("end_time").getAsString();
		}
		
		//handles edge case of missing locations
		if(!sdata.get("location").isJsonObject()) {
			if(sdata.get("location").isJsonArray()) {
				s.location = sdata.get("location").getAsJsonArray().get(0).getAsString();
			}
			else {
				s.location = sdata.get("location").getAsString();
			}
			
		}
		
		//if SectionData is a single section or a list of sections
		if(sdata.has("instructor")) {
			if(sdata.get("instructor").isJsonArray()) {
				s.instructors = gson.fromJson(sdata.get("instructor"), Instructor[].class);
			}
			else {
				s.instructors = new Instructor[1];
				s.instructors[0] = gson.fromJson(sdata.get("instructor"), Instructor.class);
			}
		}
		
		return s;		
	}
}
