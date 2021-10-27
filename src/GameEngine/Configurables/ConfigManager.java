// By Iacon1
// Created 04/25/2021
// Configuration Manager; While the module manager handles modules (which contain code) this handles data instead

package GameEngine.Configurables;

import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public final class ConfigManager
{
	private static class Config
	{
		// Default values
		
		// Graphics
		protected int screenWidth = 640;
		protected int screenHeight = 480;
		protected boolean maintainRatio = true;
		
		protected int frameCap = 60;
		
		//Thread settings
		protected int checkCapI = 60; // 0 - None
		protected int checkCapO = 60; // 0 - None
		protected int checkCapM = 60; // 0 - None
		protected boolean mono = true;
	}
	private static Config config;
	
	public static void init(String server) // Loads from a server folder, or uses the defaults if no path is provided
	{
		String path = null;
		config = null;
		if (server != null)
		{
			path = "Resources/Server Packs/" + server + "/BaseConfig.json";
			config = new Config();
			config = JSONManager.deserializeJSON(MiscUtils.readText(path), config.getClass());
			if (config == null) // Save the default into there
			{
				Logging.logError("No config found for server " + server + ". Generating one...");
				config = new Config();
				Logging.logError("Done.");
				MiscUtils.saveText(path, JSONManager.serializeJSON(config));
			}
		}
		else config = new Config();
	}
	
	public static int getFrameCap() // In FPS
	{
		return config.frameCap;
	}
	public static int getCheckCapI() // In CPS
	{
		return config.checkCapI;
	}
	public static int getCheckCapO() // In CPS
	{
		return config.checkCapO;
	}
	public static int getCheckCapM() // In CPS
	{
		return config.checkCapM;
	}
	
	public static boolean getMonoThread()
	{
		return config.mono;
	}
	public static int getScreenWidth() // Width of screen in pixels
	{
		return config.screenWidth;
	}

	public static int getScreenHeight() // Height of screen in pixels
	{
		return config.screenHeight;
	}

	public static boolean maintainRatio()
	{
		return config.maintainRatio;
	}

}
