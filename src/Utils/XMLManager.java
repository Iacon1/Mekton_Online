// By Iacon1
// Created 04/22/2021
// Handles turning things into XML files and back for storage & transport

package Utils;

import com.thoughtworks.xstream.XStream;

public final class XMLManager
{
	private static XStream xstream_; // Xstream system
	private static boolean setup_; // Is set up?
	
	private static void setupIfNot() // Sets up if not already
	{
		if (!setup_)
		{
			xstream_ = new XStream();
			setup_ = true;
		}
	}
	
	public static String serializeXML(Object unserialized) // Serializes
	{
		setupIfNot();
		try {return xstream_.toXML(unserialized);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	public static Object deserializeXML(String serialized) // Unserializes
	{
		setupIfNot();
		try {return xstream_.fromXML(serialized);}
		catch (Exception e) {e.printStackTrace(); Logging.logException(e); return null;}
	}
}
