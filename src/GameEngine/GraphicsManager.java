// By Iacon1
// Created 04/25/2021
// Manages graphics with Graphics2D

package GameEngine;

import java.util.HashMap;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import Utils.MiscUtils;

public final class GraphicsManager
{
	private static HashMap<String, Image> map_;
	
	public static void init()
	{
		map_ = new HashMap<String, Image>();
	}
	
	public static final Image getImage(String path) // Gets an image from path
	{
		Image image = map_.get(path);
		if (image == null)
		{
			image = Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(path));
			map_.put(path, image);
		}
	
		return image;
	}
}
