// By Iacon1
// Created 12/03/2021
// Bad index types for IndexTable

package Utils;

public class IndexTypesException extends Exception
{
	private static String message(Class<?>[] classes, Object[] values)
	{
		if (classes.length < values.length) return "Too many values.";
		else if (classes.length > values.length) return "Too few values.";
		else
		{
			String classNames = "";
			String valueClassNames = "";
			for (int i = 0; i < classes.length; ++i)
			{
				String sep = ", ";
				if (i == 0) sep = "";
				classNames += sep + classes[i].getName();
				if (values[i] != null) valueClassNames += sep + values[i].getClass().getName();
				else valueClassNames += sep + "[null]";
			}
			
			return "Expected classes [" + classNames + "], got [" + valueClassNames + "].";
		}
	}
	public IndexTypesException(Class<?>[] classes, Object[] values)
	{
		super(message(classes, values));
	}
}
