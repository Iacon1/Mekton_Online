// By Iacon1
// Created 04/27/2021
// Data given to a player on update

package Modules.BaseModule.PacketTypes;

import GameEngine.Account;
import GameEngine.GameInfo;
import GameEngine.Point2D;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.TransSerializable;

import GameEngine.PacketTypes.Packet;

public class GameDataPacket extends Packet implements TransSerializable
{
	public int currentLocationId; // Player's location index
	public int possesseeId; // Player's index
	
	public GameInfo.GameWorld ourView; // Game world, but only contains the data we need
	public Point2D camera;

	private boolean isNeccessary(GameEntity instance) // Do we need to record this?
	{
		return true; // TODO how to determine
	}

	public GameDataPacket(Account player)
	{
		ourView = new GameInfo.GameWorld();
		
		for (int i = 0; i < GameInfo.getWorld().getEntities().size(); ++i)
		{
			if (!isNeccessary(GameInfo.getWorld().getEntities().get(i))) ourView.getEntities().add(null);
			else ourView.getEntities().add(GameInfo.getWorld().getEntities().get(i));
		}
		
		currentLocationId = 0; // TODO determine location
		
		possesseeId = player.possessee;
		camera = player.getCamera();
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
		GameInfo.setCamera(camera);
//		Camera.gui = (GUIPin) GameInfo.getWorld().getEntity(ourGUI);
	}
}
