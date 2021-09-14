// By Iacon1
// Created 04/27/2021
// Data given to a player on update

package Modules.BaseModule.PacketTypes;

import GameEngine.GameInfo;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.TransSerializable;
import GameEngine.EntityTypes.GUITypes.GUIPin;
import GameEngine.PacketTypes.Packet;
import Server.Account;

public class GameDataPacket extends Packet implements TransSerializable
{
	public int currentLocationId; // Player's location index
	public int possesseeId; // Player's index
	
	public GameInfo.GameWorld ourView; // Game world, but only contains the data we need
	public int ourGUI;
	
	private boolean isNeccessary(GameEntity instance) // Do we need to record this?
	{
		return true; // TODO how to determine
	}

	public GameDataPacket(Account player)
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
		*/
		ourGUI = GUIPin.findPin(player).getId();
		possesseeId = player.possessee;
	}
	@Override
	public void preSerialize()
	{

	}

	@Override
	public void postDeserialize()
	{
		GameInfo.setWorld(ourView);
		GameInfo.setPossessee(possesseeId);
//		Camera.gui = (GUIPin) GameInfo.getWorld().getEntity(ourGUI);
	}
}
