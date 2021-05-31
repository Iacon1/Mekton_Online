// By Iacon1
// Created 04/25/2021
// Configuration Manager; While the module manager handles modules (which contain code) this handles data instead

package GameEngine.Configurables;

import Utils.JSONManager;
import Utils.MiscUtils;

public final class ConfigManager
{
	private static class Config
	{
		protected int screenWidth_ = 640;
		protected int screenHeight_ = 480;
		protected int hexWidth_ = 38;
		protected int hexHeight_ = 32;
		protected int frameCap_ = 60; // All default values
	}
	private static Config config_;
	
	public static void init(String server) // Loads from a server folder, or uses the defaults if no path is provided
	{
		String path = null;
		config_ = null;
		if (server != null)
		{
			path = "Resources/Server Packs/" + server + "/Config.json";
			config_ = new Config();
			config_ = JSONManager.deserializeJSON(MiscUtils.readText(path), config_.getClass());
			if (config_ == null) // Save the default into there
			{
				config_ = new Config();
				MiscUtils.saveText(path, JSONManager.serializeJSON(config_));
			}
		}
		else config_ = new Config();
	}
	
	public static int getFrameCap() // In FPS
	{
		return config_.frameCap_;
	}
	public static int getScreenWidth() // Width of screen in pixels
	{
		return config_.screenWidth_;
	}

	public static int getScreenHeight() // Height of screen in pixels
	{
		return config_.screenHeight_;
	}

	public static int getHexWidth() // Hex width in pixels
	{
		return config_.hexWidth_;
	}
	public static int getHexHeight() // Hex height in pixels; Should be 85% of getHexLength()
	{
		return config_.hexHeight_;
	}

}
