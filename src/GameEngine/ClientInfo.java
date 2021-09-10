// By Iacon1
// Created 08/31/2021
// Client Info
// TODO set this up better for modules

package GameEngine;

public abstract class ClientInfo
{
	private GameWorld world_;
	private GameFrame frame_;
	private String command_;
	
	protected static ClientInfo info_; // Must be instantiated
	private static boolean isClient_; // On if client
	
	protected ClientInfo()
	{}

	public static void setClient(boolean isClient)
	{
		isClient_ = isClient;
	}
	public static boolean isClient()
	{
		return isClient_;
	}
	
	public static void setWorld(GameWorld world)
	{
		info_.world_ = world;
	}
	public static GameWorld getWorld()
	{
		return info_.world_;
	}
	
	public static void setFrame(GameFrame frame)
	{
		info_.frame_ = frame;
	}
	public static GameFrame getFrame()
	{
		return info_.frame_;
	}

	public static void resetCommand() // Resets all input elements
	{
		info_.command_ = null;
	}
	public static void setCommand(String command)
	{
		info_.command_ = command;
	}
	public static String getCommand() // Gets input, resets if not empty, returns null if empty
	{
		if (info_.command_ == null)
			return null;
		else
		{
			String command = info_.command_;
			resetCommand();
			
			return command;
		}
	}
}
