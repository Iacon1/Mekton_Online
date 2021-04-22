// By Iacon1
// Created 4/18/2021
// Make calls to this when logging; Can switch which logger is used w/ setLogger, all commands are then passed to that

package Utils;

public final class Logging
{
	private static Logger logger_;
	
	public static void setLogger(Logger logger)
	{
		logger_ = logger;
	}
	
	public static void logRaw(String text)
	{
		if (logger_ == null)
			return;
		else
			logger_.logRaw(text);
	}
	public static void logMessage(String message)
	{
		if (logger_ == null)
			return;
		else
			logger_.logMessage(message);
	}
	public static void logError(String error)
	{
		if (logger_ == null)
			return;
		else
			logger_.logError(error);
	}
	public static void logException(Exception exception)
	{
		if (logger_ == null)
			return;
		else
			logger_.logException(exception);
	}
}
