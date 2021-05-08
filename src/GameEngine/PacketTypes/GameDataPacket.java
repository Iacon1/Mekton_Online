// By Iacon1
// Created 04/27/2021
// Data given to a player on update

package GameEngine.PacketTypes;

import GameEngine.GameInstance;
import GameEngine.GameWorld;
import GameEngine.Hexmap;

public class GameDataPacket extends Packet
{
	public int currentLocationId; // Player's location index
	public int playerObjId; // Player's index
	
	public GameWorld ourView; // Game world, but only contains the data we need
	
	private boolean isNeccessary(GameInstance instance) // Do we need to record this?
	{
		return true; // TODO how to determine
	}
	
	public void viewWorld() // Converts the global game world into the client data
	{
		ourView = GameWorld.getWorld().copy();
		
		for (int i = 0; i < ourView.getInstances().size(); ++i)
		{
			if (!isNeccessary(ourView.getInstances().get(i)))
			{
				ourView.getInstances().set(i, null); // Doesn't delete, just hides
			}
		}
		
		currentLocationId = 0; // TODO determine location
	}
	
	public Hexmap getMap()
	{
		return (Hexmap) ourView.getInstances().get(currentLocationId);
	}
}
