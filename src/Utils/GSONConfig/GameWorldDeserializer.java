// By Iacon1
// Created 06/05/2021
// For serializing & deserializing GameWorld

package Utils.GSONConfig;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import GameEngine.GameWorld;
import Utils.Logging;

public class GameWorldDeserializer implements JsonDeserializer<GameWorld>
{
	@Override
	public GameWorld deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
	    JsonObject jsonObject = json.getAsJsonObject();
	    GameWorld world = new GameWorld();
	    try
	    {
	    	world = GiveBuilder.giveBuilderNGW().create().fromJson(jsonObject, typeOfT);
	    	
	    	for (int i = 0; i < world.getEntities().size(); ++i)
	    	{
	    		world.getEntities().get(i).setWorld(world);
	    	}
	    }
	    catch (Exception e) {Logging.logException(e);}
	    
	    
	    
	    return world;
	  }
}
