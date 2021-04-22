// By Iacon1
// Created 04/21/2021
// Logger interface

// How this works is that there's one main log, divided into several sublogs
// Sublogs:
//   message
//   error
//   exception

package Utils;

public interface Logger
{
	public void logRaw(String text); // Adds a line of raw text to the main log w/ no sublog or line info
	public void logMessage(String message); // Adds a message to the "message" sublog along with the calling method and line
	public void logError(String error); // Adds a message to the "error" sublog along with the calling method and line
	public void logException(Exception exception); // Adds a message to the "exception" sublog along with the calling method and line
}
