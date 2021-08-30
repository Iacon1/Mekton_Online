// By Iacon1
// Created 04/22/2021
// Just some misc. util functions

package Utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


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
		try
		{
			FileWriter writer = new FileWriter(getAbsolute(path), false);
			writer.write(text);
			writer.close();
		}
		catch (Exception e) {Logging.logException(e);}
	}
	public static String readText(String path) // Reads text from file
	
	{
		try
		{
			try
			{
				File fileObj = new File(getAbsolute(path));
				
				Scanner scanner = new Scanner(fileObj);
				
				String text = "";
				
				while (scanner.hasNextLine()) text = text + scanner.nextLine();
				scanner.close();
				
				return text;
			}
			catch (Exception e) {Logging.logException(e); return null;}
		}
		catch (Exception e) {Logging.logException(e); return null;}
	}

	public static String getExIp() // Gets external IP https://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
	{
		
		try
		{
			URL ipSite = new URL("http://checkip.amazonaws.com");
			BufferedReader in = null;
			in = new BufferedReader(new InputStreamReader(ipSite.openStream()));
			String ip = in.readLine();
			in.close();
			return ip;
        }
		catch (Exception e) {Logging.logException(e); return "Null";}
	}
	
	public static <T> String arrayToString(T[] array, String sep)
	{
		String string = new String();
		for (int i = 0; i < array.length; ++i) string = string + array[i].toString() + sep;
		return string;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> String arrayToString(ArrayList<T> array, String sep)
	{
		return arrayToString((T[]) array.toArray(), sep);
	}
	
	
}
