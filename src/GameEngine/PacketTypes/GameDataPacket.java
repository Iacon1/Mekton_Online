// By Iacon1
// Created 04/27/2021
// Data given to a player on update

package GameEngine.PacketTypes;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import Utils.JSONManager;
import Utils.Logging;

public class GameDataPacket extends Packet
{
	public int currentLocationId; // Player's location index
	public int playerObjId; // Player's index
	
	public GameWorld ourView; // Game world, but only contains the data we need

	private boolean isNeccessary(GameEntity instance) // Do we need to record this?
	{
		return true; // TODO how to determine
	}
	
	public void viewWorld() // Converts the global game world into the client data
	{
		try {ourView = GameWorld.getWorld().clone();}
		catch (Exception e) {Logging.logException(e);}
		/*for (int i = 0; i < ourView.getEntities().size(); ++i)
		{
			if (!isNeccessary(ourView.getEntities().get(i)))
			{
				ourView.getEntities().set(i, null); // Doesn't delete, just hides
			}
		}
		
		currentLocationId = 0; // TODO determine location
		*/ // TODO figure out copying the world again
	}
}
