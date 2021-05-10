// By Iacon1
// Created 05/09/2021
//

package Serializers;

import java.lang.reflect.Type;

import com.google.gson.*;

public class AbsAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T>
{
	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
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
	        String packageText = fullName.substring(0, fullName.lastIndexOf(".") + 1);

	        return context.deserialize(element, Class.forName(packageText + type));
	    }
	    catch (ClassNotFoundException cnfe)
	    {
	        throw new JsonParseException("Unknown element type: " + type, cnfe);
	    }
	  }
}
