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
import java.util.Arrays;
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
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(getAbsolute(path), false);
			writer.write(text);
		}
		catch (Exception e) {Logging.logException(e);}
		finally
		{
			if (writer != null)
			{
				try {writer.close();}
				catch (Exception e) {Logging.logException(e);}
			}
		}
	}
	public static String readText(String path) // Reads text from file
	
	{
		Scanner scanner = null;
		String text = "";
		try
		{
			try
			{
				File fileObj = new File(getAbsolute(path));
				
				scanner = new Scanner(fileObj);

				while (scanner.hasNextLine()) text = text + scanner.nextLine();
			}
			catch (Exception e) {Logging.logException(e); return null;}
			finally {scanner.close();}
			
			return text;
		}
		catch (Exception e) {Logging.logException(e); return null;}
	}

	public static String getExIp() // Gets external IP https://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
	{
		BufferedReader in = null;
		String ip = null;
		try
		{
			URL ipSite = new URL("http://checkip.amazonaws.com");
			in = null;
			in = new BufferedReader(new InputStreamReader(ipSite.openStream()));
			ip = in.readLine();
        }
		catch (Exception e) {Logging.logException(e); return "Null";}
		finally
		{
			try {in.close();}
			catch (Exception e) {Logging.logException(e);}
		}
		
		return ip;
	}
	
	public static <T> String arrayToString(T[] array, String sep)
	{
		String string = new String();
		for (int i = 0; i < array.length; ++i) string = string + array[i].toString() + sep;
		return string;
	}
	
	public static <T> String arrayToString(ArrayList<T> array, String sep)
	{
		return arrayToString((T[]) array.toArray(), sep);
	}
	
	public static <T> String ClassToString(Class<T> sClass)
	{
		return sClass.getName();
	}
	
	public static int divCeil(int a, int b)
	{
		return (int) (((float) a) / ((float) b));
	}
	public static int divFloor(int a, int b)
	{
		return (int) (((float) a) / ((float) b));
	}
	
	public static int multiMax(int x, int... X)
	{
		int y;
		if (X.length > 1)
		{
			int[] sX = Arrays.copyOfRange(X, 1, X.length);
			y = multiMax(X[0], sX);
		}
		else y = X[0];
		
		return Math.max(x, y);
	}
	
	/** I forgot what lerp does
	 * 
	 * @ param a
	 * @ param b
	 * @ param t
	 */
	public static float lerp(float a, float b, float t)
	{
		return a + (b - a) * t;
	}
	
	public static int forceMult(float a, float b)
	{
		return (int) (Math.floor(a / b) * b);
	}
	public static int forceMult(double a, double b)
	{
		return (int) (Math.floor(a / b) * b);
	}
}
