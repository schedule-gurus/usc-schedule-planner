package soc_deserializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import models.Instructor;

public class InstructorDeserializer implements JsonDeserializer<Instructor>{
	@Override
	public Instructor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject idata = json.getAsJsonObject();
		Instructor i = new Instructor();
		i.first_name = idata.get("first_name").getAsString();
		i.last_name = idata.get("last_name").getAsString();
		
		//Getting the RMP scores here is too intensive! Do this when displaying chosen sections
		//i.rmp = RMP.get_rmp(i.first_name, i.last_name);
		
		return i;		
	}
}
