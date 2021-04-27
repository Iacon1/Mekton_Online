// By Iacon1
// Created 04/26/2021
//

package Server;

import GameEngine.GameWorld;
import Server.Frames.ServerWindow;

// TODO these are all dummy values
public class Server
{
	protected GameWorld world_;
	
	public Server()
	{
	}
	
	public String getName() // Get server name
	{
		return null;
	}
	
	public String getResourceFolder() // Get path for resources
	{
		return "Resources/Server Packs/Default";
	}
	
	public int currentPlayers()
	{
		return 0;
	}
	public int maxPlayers()
	{
		return 10;
	}

	public GameWorld getWorld()
	{
		return world_;
	}
	
	
}
