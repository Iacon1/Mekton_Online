// By Iacon1
// Created 04/22/2021
// Handles turning things into XML files and back for storage & transport

package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Serializers.GiveBuilder;

public final class JSONManager
{
	private static Gson gson_; // GSON system
	private static boolean setup_; // Is set up?
	
	private static void setupIfNot() // Sets up if not already
	{
		if (!setup_)
		{
			GsonBuilder builder = GiveBuilder.giveBuilder();
			gson_ = builder.create();
			setup_ = true;
		}
	}
	
	public static <C> String serializeJSON(C unserialized) // Serializes
	{
		setupIfNot();
		try {return gson_.toJson(unserialized);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	public static <C> C deserializeJSON(String serialized, Class<C> classTo) // Unserializes
	{
		setupIfNot();
		try {return gson_.fromJson(serialized, classTo);}
		catch (Exception e) {Logging.logException(e); return null;}
	}

	public static <C> C deserializeArrayJSON(String serialized, java.lang.reflect.Type typeTo) // Unserializes
	{
		setupIfNot();
		try {return gson_.fromJson(serialized, typeTo);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
}
