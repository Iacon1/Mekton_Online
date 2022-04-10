// By Iacon1
// Created 04/25/2021
// Configuration Manager; While the module manager handles modules (which contain code) this handles data instead

package GameEngine.Configurables;

import java.util.HashMap;
import java.util.Map;

import GameEngine.GameInfo;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public final class ConfigManager
{
	private static Map<String, String> values;
	private static boolean setup = false;

	private static void setupIfNot() // Loads from a server folder if not already loaded.
	{
		if (setup) return;
		
		String path = null;
		values = new HashMap<String, String>();
		
		if (GameInfo.hasServerPack())
		{
			path = GameInfo.getServerPackResource("Config.json");
			values = JSONManager.deserializeJSON(MiscUtils.readText(path), values.getClass());
			Logging.logError("No config found.");
			values = new HashMap<String, String>();
		}
		else
		{
			Logging.logError("No server pack loaded.");
			values = new HashMap<String, String>();
		}
		
		setup = true;
	}
	private static void saveConfig()
	{
		String path = GameInfo.getServerPackResource("Config.json");
		String text = JSONManager.serializeJSON(values);
		MiscUtils.saveText(path, text);
	}
	
	public static String getValue(String name) {setupIfNot(); return values.get(name);}
	public static String getValue(String name, String defaultValue)
	{
		setupIfNot();
		String value = values.get(name);
		if (value == null)
		{
			value = defaultValue;
			values.put(name, value);
			saveConfig();
		}
		return value;
	}
	public static void setValue(String name, String value) {setupIfNot(); values.put(name, value); saveConfig();}
	
	public static int getFramerateCap() {return Integer.valueOf(getValue("framerate_cap", "60"));} // In FPS
	public static int getNetrateCap() {return Integer.valueOf(getValue("netrate_cap", "60"));} // In CPS

	public static int getScreenWidth() {return Integer.valueOf(getValue("screen_width", "640"));} // Width of screen, in pixels

	public static int getScreenHeight() {return Integer.valueOf(getValue("screen_height", "480"));} // Height of screen, in pixels
}
