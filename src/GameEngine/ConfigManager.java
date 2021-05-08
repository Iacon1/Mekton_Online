// By Iacon1
// Created 04/25/2021
// Configuration Manager; Loads a configuration & answers questions statically

package GameEngine;

public final class ConfigManager
{
	private static class Config // TODO: Loading of this from XML
	{
		protected int screenWidth_ = 640;
		protected int screenHeight_ = 480;
		protected int hexWidth_ = 38;
		protected int hexHeight_ = 32;
	}
	private static Config config_ = new Config();
	
	public static int getFramerate() // In FPS
	{
		return 60;
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
