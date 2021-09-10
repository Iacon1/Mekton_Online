// By Iacon1
// Created 04/27/2021
// Data given to a player on update

package Modules.BaseModule.PacketTypes;

import GameEngine.GameEntity;
import GameEngine.GameInfo;
import GameEngine.TransSerializable;
import GameEngine.PacketTypes.Packet;

public class GameDataPacket extends Packet implements TransSerializable
{
	public int currentLocationId; // Player's location index
	public int playerObjId; // Player's index
	
	public GameInfo.GameWorld ourView; // Game world, but only contains the data we need

	private boolean isNeccessary(GameEntity instance) // Do we need to record this?
	{
		return true; // TODO how to determine
	}

	@Override
	public void preSerialize()
	{
		ourView = GameInfo.getWorld();
		
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

	@Override
	public void postDeserialize()
	{
		GameInfo.setWorld(ourView);
	}
}
