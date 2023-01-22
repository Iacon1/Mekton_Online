// By Iacon1
// Created 04/25/2021
// Manages graphics with Graphics2D

package GameEngine.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import GameEngine.GameInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;
import jdk.internal.org.jline.utils.Colors;

public final class GraphicsManager
{
	private static Map<String, BufferedImage> images;
	private static Map<String, Font> fonts;
	
	public static void init(boolean justFonts)
	{
		if (!justFonts)
		{
			images = new HashMap<String, BufferedImage>();
		}
		fonts = new HashMap<String, Font>();
	}
	
	private static IndexColorModel getColorModel(List<Color> palette)
	{
		byte[] red = new byte[palette.size()], green = new byte[palette.size()], blue = new byte[palette.size()], alpha = new byte[palette.size()];
		for (int i = 0; i < palette.size(); ++i)
		{
			red[i] = (byte) palette.get(i).getRed();
			green[i] = (byte) palette.get(i).getGreen();
			blue[i] = (byte) palette.get(i).getBlue();
			alpha[i] = (byte) palette.get(i).getAlpha();
		}
		// https://stackoverflow.com/questions/680002/find-out-number-of-bits-needed-to-represent-a-positive-integer-in-binary
		return new IndexColorModel(Integer.SIZE - Integer.numberOfLeadingZeros(palette.size()), palette.size(), red, green, blue, alpha);
	}
	private static IndexColorModel getColorModel(Color[] palette)
	{
		byte[] red = new byte[palette.length], green = new byte[palette.length], blue = new byte[palette.length], alpha = new byte[palette.length];
		for (int i = 0; i < palette.length; ++i)
		{
			red[i] = (byte) palette[i].getRed();
			green[i] = (byte) palette[i].getGreen();
			blue[i] = (byte) palette[i].getBlue();
			alpha[i] = (byte) palette[i].getAlpha();
		}
		// https://stackoverflow.com/questions/680002/find-out-number-of-bits-needed-to-represent-a-positive-integer-in-binary
		return new IndexColorModel(Integer.SIZE - Integer.numberOfLeadingZeros(palette.length), palette.length, red, green, blue, alpha);
	}
	/** Loads an image from file with an indexed color model.
	 * 
	 */
	private static BufferedImage getAsIndexed(String path)
	{
		List<Color> palette = new ArrayList<Color>();
		File file = new File(MiscUtils.getAbsolute(path));
		BufferedImage img1, img2;
		try {img1 = ImageIO.read(file);}
		catch (Exception e) {Logging.logException(e); return null;}
		
		for (int i = 0; i < img1.getHeight(); ++i)
			for (int j = 0; j < img1.getWidth(); ++j)
			{
				int rgb = img1.getRGB(j, i);
				Color color = new Color(rgb, true);
				if (!palette.contains(color)) palette.add(color);
			}

		IndexColorModel icm = getColorModel(palette);
		img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, icm);
		for (int i = 0; i < img1.getHeight(); ++i)
			for (int j = 0; j < img1.getWidth(); ++j)
				img2.setRGB(j, i, img1.getRGB(j, i));

		return img2;
	}
	public static BufferedImage getImagePath(String path) // Gets an image from path
	{
		BufferedImage image;
		image = images.get(path);
		if (image == null)
		{
			
			Logging.logError("Have not loaded image " + path + ". Loading...");
			image = getAsIndexed(path);
			if (image == null) Logging.logError("Could not load image " + path);
			else Logging.logError("Loading done");
			images.put(path, image);
		}
		
		return image;
	}
	
	public static Font getFontPath(String path) // Gets an image from path
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
	
	@SuppressWarnings("rawtypes")
	public static Image getImage(String name, Color[] palette)
	{
		if (palette != null)
		{
			BufferedImage i = getImagePath(GameInfo.getServerPackResource("/Graphics/" + name + ".PNG"));
			WritableRaster r = i.getRaster();
			return new BufferedImage(getColorModel(palette), r, i.isAlphaPremultiplied(), new Hashtable());
		}
		else return getImagePath(GameInfo.getServerPackResource("/Graphics/" + name + ".PNG"));
	}
	public static Image getImage(String name)
	{
		return getImage(name, null);
	}
	public static Font getFont(String name)
	{
		return getFontPath(GameInfo.getServerPackResource("/Fonts/" + name + ".TTF"));
	}
	public static Color getColor(int r, int g, int b)
	{
		return new Color(r, g, b);
	}
	
	public static float getFontSize(int pixels) // Converts pixel height to font points
	{
		return (float) (72.0 * pixels / Toolkit.getDefaultToolkit().getScreenResolution()); // https://stackoverflow.com/questions/5829703/java-getting-a-font-with-a-specific-height-in-pixels
	}
	
	public static Color[] getPalette(String name)
	{
		List<Color> palette = new ArrayList<Color>();
		
		File file = new File(GameInfo.getServerPackResource("/Graphics/" + name + ".PNG"));
		BufferedImage img;
		try {img = ImageIO.read(file);}
		catch (Exception e) {Logging.logException(e); return null;}
		
		for (int i = 0; i < img.getHeight(); ++i)
			for (int j = 0; j < img.getWidth(); ++j)
			{
				int rgb = img.getRGB(j, i);
				Color color = new Color(rgb, true);
				if (!palette.contains(color)) palette.add(color);
			}
		
		return palette.toArray(new Color[palette.size()]);
	}
	
	public static float getHue(Color c)
	{
		float[] hsb = new float[3];
		return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb)[0];
	}
	public static float getSaturation(Color c)
	{
		float[] hsb = new float[3];
		return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb)[1];
	}
	public static float getValue(Color c)
	{
		float[] hsb = new float[3];
		return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb)[2];
	}
	public static float getRotation(float hue1, float hue2)
	{
		return 360 * (hue2 - hue1);
	}
	public static float rotateHue(float hue, float rot)
	{
		hue = (hue + rot / 360);
		if (hue == 0) return 0;
		else if (hue % 1 == 0) return 1;
		else return hue % 1;
	}
	
}
