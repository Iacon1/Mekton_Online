// By Iacon1
// Created 04/22/2021
// Just some misc. util functions

package Utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;


public final class MiscUtils
{
	private static <C> void addItemsToFit(ArrayList<C> array, int newSize)
	{
		while (array.size() < newSize)
			array.add(null);
	}
	
	public static <C> ArrayList<C> resizeArrayList(ArrayList<C> array, int newSize) // Makes a resized version of the array
	{
		ArrayList<C> newArray = new ArrayList<C>();
		
		addItemsToFit(newArray, newSize);
		
		for (int i = 0; i < Math.min(newSize, array.size()); ++i)
			newArray.set(i, array.get(i));
		
		newArray.trimToSize(); // Cuts down to min(newSize, array.size()), then...
		addItemsToFit(newArray, newSize); // Makes sure to lengthen back out if array.size() was smaller than newSize
		
		return newArray;
	}
	
	public static ArrayList<Image> getIcons(boolean forClient) // Returns all versions of the icon for either client or server
	{
		ArrayList<Image> icons = new ArrayList<Image>();
		String pathPrefix;
		if (forClient) pathPrefix = "Resources/Icons/Client Icons/";
		else pathPrefix = "Resources/Icons/Server Icons/"; // For the server
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon16.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon32.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon64.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon128.PNG")));
		
		return icons;
	}
	public static String getVersion() // Gets version
	{
		return "V0.X";
	}
	public static String getProgramName() // What is this program (inc. version)?
	{
		return "Mekton Online " + getVersion();
	}
	public static String getAbsolute(String name)
	{
		return new File(name).getAbsolutePath();
	}
	
	public static void saveText(String path, String text) // Saves text to file
	{
		try {Files.write(Paths.get(getAbsolute(path)), text.getBytes());}
		catch (Exception e) {e.printStackTrace();}
	}
	public static String readText(String file) // Reads text from file
	
	{
		try {return Files.readString(Paths.get(getAbsolute(file)));}
		catch (Exception e) {Logging.logException(e); return null;}
	}

}
