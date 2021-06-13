// By Iacon1
// Created 04/25/2021
// Manages graphics with Graphics2D

package GameEngine;

import java.util.HashMap;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import Utils.Logging;
import Utils.MiscUtils;

public final class GraphicsManager
{
	private static HashMap<String, Image> images_;
	
	public static void init()
	{
		images_ = new HashMap<String, Image>();
	}
	
	public static Image getImage(String path) // Gets an image from path
	{
		Image image = images_.get(path);
		if (image == null)
		{
			Logging.logError("Have not loaded image @ " + path + ". Loading...");
			image = Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(path));
			if (image == null) Logging.logError("Could not load image @ " + path);
			else Logging.logError("Loading done");
			images_.put(path, image);
		}
	
		return image;
	}
}
