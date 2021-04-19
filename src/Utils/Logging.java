package Utils;

public final class Logging
{
	public static void logError(String source, Exception e) // Logs an error w/ the corresponding source
	{
		System.out.println("MtO Logger: Caught error " + e.toString() + " From source " + source);
	}
}
