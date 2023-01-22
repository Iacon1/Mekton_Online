// By Iacon1
// Created 04/22/2021
// Just some misc. util functions

package Utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.math.RoundingMode;

public final class MiscUtils
{
	/** Adds items to a list until it reaches a certain size.
	 * 
	 *  @param list List to add to.
	 *  @param newSize Size to add until.
	 */
	private static <C> void addItemsToFit(List<C> list, int newSize)
	{
		while (list.size() < newSize)
			list.add(null);
	}
	
	/** Adds items to a list or removes them until it has a certain size.
	 * 
	 *  @param list List to add to.
	 *  @param newSize Size to set to.
	 */
	public static <C> void resizeList(List<C> list, int newSize)
	{
		if (list.size() < newSize) addItemsToFit(list, newSize);
		else while (list.size() > newSize) list.remove(list.size() - 1);
	}
	
	public enum ExecType
	{
		client,
		server,
		editor;
	}
	/** Returns all versions of the icon for either client or server
	 * 
	 *  @param execType The set of icons to get.
	 *  @return List of icons.
	 */
	public static List<Image> getIcons(ExecType execType) // Returns all versions of the icon for client, server, or editor
	{
		List<Image> icons = new ArrayList<Image>();
		String pathPrefix;
		if (execType == ExecType.client) pathPrefix = "Resources/Icons/Client Icons/";
		else if (execType == ExecType.server) pathPrefix = "Resources/Icons/Server Icons/";
		else if (execType == ExecType.editor) pathPrefix = "Resources/Icons/Editor Icons/";
		else return null;
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon16.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon32.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon64.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon128.PNG")));
		
		return icons;
	}
	
	/** Returns the program version.
	 *  @return The version of the program.
	 */
	public static String getVersion() // Gets version
	{
		return "V0.X";
	}
	/** Returns the program name.
	 *  @return The program's name.
	 */
	public static String getProgramName() // What is this program (inc. version)?
	{
		return "Mekton Online " + getVersion();
	}
	/** Returns the absolute path of the filename.
	 *  
	 *  @param name The filename.
	 *  @return The corresponding absolute path.
	 */
	public static String getAbsolute(String name)
	{
		return new File(name).getAbsolutePath();
	}
	
	/** Saves text to a file.
	 *  
	 *  @param name The filename / path to save to.
	 *  @param text The text to save.
	 */
	public static void saveText(String name, String text) // Saves text to file
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(getAbsolute(name), false);
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
	/** Reads text from a file.
	 *  
	 *  @param name The filename / path to read from.
	 *  @return The read text.
	 */
	public static String readText(String name) // Reads text from file
	{
		Scanner scanner = null;
		String text = "";
		try
		{
			try
			{
				File fileObj = new File(getAbsolute(name));
				
				scanner = new Scanner(fileObj);

				while (scanner.hasNextLine()) text = text + scanner.nextLine();
			}
			catch (Exception e) {Logging.logException(e); return null;}
			finally {scanner.close();}
			
			return text;
		}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	/** Lists all files in a folder. */
	public static String[] listFilenames(String name)
	{
		File[] files = new File(getAbsolute(name)).listFiles();
		String[] names = new String[files.length];
		for (int i = 0; i < files.length; ++i) names[i] = files[i].getName();
		return names;
	}
	/** Creates a new folder. */
	public static void newFolder(String name)
	{
		new File(getAbsolute(name)).mkdir();
	}
	/** Returns the computer's external IP.
	 * 
	 *  @return The computer's external IP.
	 */
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
	
	/** Converts an array of values into a string,
	 *  using their toString functions.
	 *  
	 *  @param array The array of values.
	 *  @param sep A seperator to put between their strings.
	 *  
	 *  @return The string made from the array.
	 */
	public static <T> String arrayToString(T[] array, String sep)
	{
		String string = new String();
		for (int i = 0; i < array.length; ++i) string = string + array[i].toString() + sep;
		return string;
	}
	/** Converts a list of values into a string,
	 *  using their toString functions.
	 *  
	 *  @param list The list of values.
	 *  @param sep A seperator to put between their strings.
	 *  
	 *  @return The string made from the list.
	 */
	public static <T> String arrayToString(List<T> list, String sep)
	{
		return arrayToString((T[]) list.toArray(), sep);
	}
	/** Converts an array of bytes into a string,
	 *  using their toString functions.
	 *  
	 *  @param array The array of bytes
	 *  @param sep A seperator to put between their strings.
	 *  
	 *  @return The string made from the array.
	 */
	public static <T> String arrayToString(byte[] array, String sep)
	{
		String string = new String();
		for (int i = 0; i < array.length; ++i) string = string + "0x" + asHex(array[i], 2) + sep;
		return string;
	}
	
	/** Returns the class name.
	 * 
	 *  @param sClass class to return the name of.
	 *  @return The name of the class.
	 */
	// Really simple, but a few ways to do it. This wrapper is used
	// to maintain consistency between a few classes that need this,
	// in case I change how I do it later.
	public static <T> String ClassToString(Class<T> sClass)
	{
		return sClass.getName();
	}
	
	// Hidden because the documentation wouldn't have made sense.
	private static int multiMaxRecursive(int x, int... X)
	{
		int y;
		if (X.length > 1)
		{
			int[] sX = Arrays.copyOfRange(X, 1, X.length);
			y = multiMaxRecursive(X[0], sX);
		}
		else y = X[0];
		
		return Math.max(x, y);
	}
	
	/** Returns the largest of the provided values.
	 * 
	 *  @param X A list of integers to sort between.
	 *  @return The largest of them.
	 */
	public static int multiMax(int... X)
	{
		if (X.length <= 0) return -1;
		else return multiMaxRecursive(X[0], X);
	}
	
	/** I forgot what lerp does
	 * 
	 *  @param a
	 *  @param b
	 *  @param t
	 *  @return ???
	 */
	public static float lerp(float a, float b, float t)
	{
		return a + (b - a) * t;
	}
	
	/** Forces a to be a multiple of b.
	 * 
	 *  @param a The float to force.
	 *  @param b The float to force it to be a multiple of.
	 *  @return The forced version of a.
	 */
	public static int forceMult(float a, float b)
	{
		return (int) (Math.floor(a / b) * b);
	}
	/** Forces a to be a multiple of b.
	 * 
	 *  @param a The double to force.
	 *  @param b The double to force it to be a multiple of.
	 *  @return The forced version of a.
	 */
	public static int forceMult(double a, double b)
	{
		return (int) (Math.floor(a / b) * b);
	}
	
	/** Returns a string representation of a float, up to a specified precision.
	 *  @param value  The float to represent.
	 *  @param digits The precision to represent it at.
	 */
	public static String floatPrecise(float value, int digits)
	{
		if (digits > 0)
		{
			DecimalFormat format = new DecimalFormat("#." + "#".repeat(digits));
			format.setRoundingMode(RoundingMode.HALF_UP);
			return format.format(value);
		}
		else return Integer.toString((int) Math.floor(value));
	}
	/** Returns a string representation of a double, up to a specified precision.
	 *  @param value  The double to represent.
	 *  @param digits The precision to represent it at.
	 */
	public static String doublePrecise(double value, int digits)
	{
		if (digits > 0)
		{
			DecimalFormat format = new DecimalFormat("#." + "#".repeat(digits));
			format.setRoundingMode(RoundingMode.HALF_UP);
			return format.format(value);
		}
		else return Integer.toString((int) Math.floor(value));
	}
	
	/** Returns whether caps lock is on.
	 * 
	 */
	public static boolean isCapsLockOn()
	{
		return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
	}

	/** Returns a character version of a VK code
	 *  @param code The code to translate to a character.
	 */
	public static char translateCode(int code)
	{
		return (char) code;
	}
	
	/** Converts a number into a string depicting its hexadecimal representation.
	 *  @param number The number to represent.
	 *  @param digits The length of the representation in characters.
	 *  
	 *  @return The string.
	 */
	public static String asHex(int number, int digits)
	{
		return String.format("%0" + digits + "x", number);
	}
	
	/** Converts the least-significant digit of a string depicting a hexadecimal number into a byte.
	 *  @param digits The number to conver the LSD of.
	 *  
	 *  @return The byte.
	 */
	public static byte toByte(String digits)
	{
		switch (digits.charAt(0))
		{
		case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7':
			return Byte.valueOf(digits, 16);
		case '8': case '9': case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
			return (byte) (int) Integer.valueOf(digits, 16);
		default: return 0;
		}
	}
}
