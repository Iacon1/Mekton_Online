// By Iacon1
// Created 11/07/2021
// Manages graphics with Graphics2D

package GameEngine.Managers;

import java.util.HashMap;
import java.util.Map;

import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public final class StringManager
{
	private static Map<String, Map<String, String>> stringBanks;
	
	public static void init()
	{
		stringBanks = new HashMap<String, Map<String, String>>();
	}
	
	public static String getString(String path, String name) // Gets a string from a string bank
	{
		Map<String, String> stringBank = stringBanks.get(path);
		if (stringBank == null)
		{
			Logging.logError("Have not loaded string bank " + path + ". Loading...");
			stringBank = JSONManager.deserializeCollectionJSON(MiscUtils.getAbsolute(path), new HashMap<String, String>().getClass());
			if (stringBank == null) Logging.logError("Could not load string bank @ " + path);
			else Logging.logError("Loading done");
			stringBanks.put(path, stringBank);
		}
		String string = stringBank.get(name);
		if (string == null) Logging.logError("Could not find string " + name + " in string bank " + path);

		return string;
	}
}
