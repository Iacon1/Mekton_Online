// By Iacon1
// Created 04/22/2021
// Handles turning things into XML files and back for storage & transport

package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Utils.GSONConfig.GiveBuilder;

public final class DataManager
{
	private static Gson gson; // GSON system
	private static boolean setup; // Is set up?
	
	private static void setupIfNot() // Sets up if not already
	{
		if (!setup)
		{
			GsonBuilder builder = GiveBuilder.giveBuilder();
			gson = builder.create();
			setup = true;
		}
	}
	
	public static void invalidate() // Forces a reset of the Gson object
	{
		setup = false;
	}
	public static <C> String serialize(C unserialized) // Serializes
	{
		setupIfNot();
		try {return gson.toJson(unserialized);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	public static <C> String serialize(C unserialized, Class<C> classFrom) // Serializes
	{
		setupIfNot();
		try {return gson.toJson(unserialized, classFrom);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	public static <C> C deserialize(String serialized, Class<C> classTo) // Unserializes
	{
		setupIfNot();
//		Logging.logNotice(serialized);
		try {return gson.fromJson(serialized, classTo);}
		catch (Exception e) {Logging.logException(e); return null;}
	}

	public static <C> C deserializeCollection(String serialized, java.lang.reflect.Type typeTo) // Unserializes a collection
	{
		setupIfNot();
		try {return gson.fromJson(serialized, typeTo);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	
	/** Deserializes a parameterized collection.
	 *  @param serialized String to deserialize.
	 *  @param mainClass Class of the collection.
	 *  @param classArgs Parameters.
	 *  
	 *  @return The deserialized collection.
	 */
	public static <C> C deserializeCollectionList(String serialized, Class<C> mainClass, Class<?>... classArgs) // Unserializes a parameterized collection
	{
		java.lang.reflect.Type typeTo = TypeToken.getParameterized(mainClass, classArgs).getType();
	
		return deserializeCollection(serialized, typeTo);
	}
}
