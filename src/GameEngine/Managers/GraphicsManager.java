// By Iacon1
// Created 04/25/2021
// Manages graphics with Graphics2D

package GameEngine.Managers;

import java.util.HashMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import Utils.Logging;
import Utils.MiscUtils;

public final class GraphicsManager
{
	private static HashMap<String, Image> images;
	private static HashMap<String, Font> fonts;
	
	public static void init()
	{
		images = new HashMap<String, Image>();
		fonts = new HashMap<String, Font>();
	}
	
	public static Image getImage(String path) // Gets an image from path
	{
		Image image = images.get(path);
		if (image == null)
		{
			Logging.logError("Have not loaded image " + path + ". Loading...");
			image = Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(path));
			if (image == null) Logging.logError("Could not load image " + path);
			else Logging.logError("Loading done");
			images.put(path, image);
		}
	
		return image;
	}
	
	public static Font getFont(String path) // Gets an image from path
	{
		Font font = fonts.get(path);
		if (font == null)
		{
			Logging.logError("Have not loaded font " + path + ". Loading...");
			try {font = Font.createFont(Font.TRUETYPE_FONT, new File(MiscUtils.getAbsolute(path)));}
			catch (Exception e) {Logging.logException(e);}
			if (font == null) Logging.logError("Could not load font " + path);
			else Logging.logError("Loading done");
			fonts.put(path, font);
		}
	
		return font;
	}
	
	public static Color getColor(int r, int g, int b)
	{
		return new Color(r, g, b);
	}
	
	public static float getFontSize(int pixels) // Converts pixel height to font points
	{
		return (float) (72.0 * pixels / Toolkit.getDefaultToolkit().getScreenResolution()); // https://stackoverflow.com/questions/5829703/java-getting-a-font-with-a-specific-height-in-pixels
	}
}
