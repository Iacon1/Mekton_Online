// By Iacon1
// Created 05/09/2021
// For abstract classes that may be in lists
// https://stackoverflow.com/questions/16872492/gson-and-abstract-superclasses-deserialization-issue

package Utils.Serializers;

import java.lang.reflect.Type;

import com.google.gson.*;

import Utils.Logging;

public class AbsAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T>
{
	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
		result.add("properties", context.serialize(src, src.getClass()));

		return result;
	}
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
	    JsonObject jsonObject = json.getAsJsonObject();
	    String type = jsonObject.get("type").getAsString();
	    JsonElement element = jsonObject.get("properties");

	    try
	    {            
	    	String fullName = typeOfT.getTypeName();
	        return context.deserialize(element, Class.forName(type));
	    }
	    catch (JsonParseException e) {throw e;}
	    catch (Exception e) {Logging.logException(e); return null;}
	  }
}
