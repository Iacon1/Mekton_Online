// By Iacon1
// Created 08/31/2021
// Client Info
// TODO set this up better for modules

package GameEngine;

public abstract class ClientInfo
{
	private GameWorld world_;
	
	protected static ClientInfo info_; // Must be instantiated
	
	protected ClientInfo()
	{}

	public static void setWorld(GameWorld world)
	{
		info_.world_ = world;
	}
	
	public static GameWorld getWorld()
	{
		return info_.world_;
	}
}
