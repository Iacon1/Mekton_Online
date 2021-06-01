// By Iacon1
// Created 04/26/2021
// Logger for server

package Server;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.*;

import Utils.Logger;

public class ServerLogger implements Logger
{
	private static class LogEntry
	{
		public Date date_;
		public String category_;
		public String text_;
	}
	private ArrayList<LogEntry> log_;
	
	private static String getCaller() // Returns calling function & line
	{
		StackTraceElement element = Thread.currentThread().getStackTrace()[4];
		return element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber();
	}
	public ServerLogger()
	{
		log_ = new ArrayList<LogEntry>();
	}
	
	@Override
	public void logRaw(String reason, String text)
	{
		LogEntry entry = new LogEntry();
		entry.date_ = Date.from(Instant.now());
		entry.text_ = text;
		entry.category_ = reason;
		
		log_.add(entry);
	}

	@Override
	public void logNotice(String text)
	{
		logRaw("note", text);
	}

	@Override
	public void logError(String error)
	{
		logRaw("error", error + "<br>  " + getCaller());
	}

	@Override
	public void logException(Exception exception)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);
		String trace = stringWriter.toString();
		logRaw("exception", exception.toString() + "<br>  " + trace);
	}

	public final String buildLabelText(String category) // Builds a string holding a category of log; If category is null then shows all
	{
		String log = "<html>";
		for (int i = 0; i < log_.size(); ++i)
		{
			LogEntry entry = log_.get(i);
			if (entry.category_ == category || category == null)
				log = log + "(" + entry.date_.toString() + ") [" + entry.category_ + "]: " + entry.text_ + "<br>";
		}
		
		return log;
	}
}
