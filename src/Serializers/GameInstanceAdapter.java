// By Iacon1
// Created 05/06/2021
//

package Serializers;

import java.lang.reflect.Type;

import com.google.gson.*;

import GameEngine.GameInstance;

public class GameInstanceAdapter implements JsonSerializer<GameInstance>, JsonDeserializer<GameInstance>
{
	@Override
	public JsonElement serialize(GameInstance src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
		result.add("properties", context.serialize(src, src.getClass()));
		
		return result;
	}
	
	@Override
	public GameInstance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
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
