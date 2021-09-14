// By Iacon1
// Created 09/12/2021
//

package Modules.HexUtilities;

import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public class HexConfigManager
{
	private static class HexConfig
	{
		protected int hexSize_ = 19;
	}
	private static HexConfig hexConfig_;
	
	public static void init(String server) // Loads from a server folder, or uses the defaults if no path is provided
	{
		String path = null;
		hexConfig_ = null;
		if (server != null)
		{
			path = "Resources/Server Packs/" + server + "/HexConfig.json";
			hexConfig_ = new HexConfig();
			hexConfig_ = JSONManager.deserializeJSON(MiscUtils.readText(path), hexConfig_.getClass());
			if (hexConfig_ == null) // Save the default into there
			{
				Logging.logError("No hex config found for server " + server + ". Generating one...");
				hexConfig_ = new HexConfig();
				Logging.logError("Done.");
				MiscUtils.saveText(path, JSONManager.serializeJSON(hexConfig_));
			}
		}
		else hexConfig_ = new HexConfig();
	}
	
	public static int getHexRadius() // Hex radius in pixels
	{
		return hexConfig_.hexSize_;
	}
	public static int getHexWidth() // Hex width in pixels
	{
		return getHexRadius() * 2;
	}
	public static int getHexHeight() // Hex height in pixels
	{
		double size = (double) getHexRadius();
		double h = Math.sqrt(3.0) * size;
		return (int) h;
	}
}
