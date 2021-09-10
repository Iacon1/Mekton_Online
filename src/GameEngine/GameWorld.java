// By Iacon1
// Created 04/26/2021
// All data about the game world

package GameEngine;

import java.util.ArrayList;

public class GameWorld
{
	protected ArrayList<GameEntity> instances_; // List of game instances
	
	private String command_;
	
	private static transient GameWorld world_;
	private static transient boolean isClient_;
	private static transient GameFrame frame_;
	
	public GameWorld()
	{
		instances_ = new ArrayList<GameEntity>();
	}
	
	public ArrayList<GameEntity> getRootEntities() // Returns every instance with no parent
	{
		ArrayList<GameEntity> array = new ArrayList<GameEntity>();
		for (int i = 0; i < instances_.size(); ++i)
		{
			GameEntity instance = instances_.get(i);
			if (instance.getParent() == null) array.add(instance);
		}
		
		return array;
	}
	
	public ArrayList<GameEntity> getEntities() // Shows every instance instead of just our children; GameWorld.children_ ought be empty
	{
		return instances_;
	}

	public static void setWorld(GameWorld world)
	{
		world_ = world;
	}
	public static GameWorld getWorld()
	{
		return world_;
	}

	public static void setFrame(GameFrame gameFrame)
	{
		frame_ = gameFrame;
	}

	public static void setCommand(String command)
	{
		world_.command_ = command;
	}
	public static String getCommand()
	{
		return world_.command_;
	}

	public static void setClient(boolean isClient)
	{
		isClient_ = isClient;
	}
	public static boolean isClient()
	{
		return isClient_;
	}
}
