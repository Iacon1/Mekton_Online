// By Iacon1
// Created 04/25/2021
// Configuration Manager; Loads a configuration & answers questions statically

package GameEngine;

public final class ConfigManager
{
	private class Config // TODO: Loading of this from XML
	{
		protected int ScreenWidth_;
		protected int ScreenHeight_;
		protected int hexSize_;
	}
	
	public static int getFramerate() // In FPS
	{
		return 60;
	}
	public static int getScreenWidth() // Width of screen in pixels
	{
		return 640;
	}

	public static int getScreenHeight() // Height of screen in pixels
	{
		return 480;
	}

	public static int getHexSize() // Hex width / height in pixels
	{
		return 32;
	}

}
