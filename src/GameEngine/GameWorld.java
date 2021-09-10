// By Iacon1
// Created 04/26/2021
// All data about the game world

package GameEngine;

import java.util.ArrayList;

public class GameWorld implements Cloneable
{
	protected ArrayList<GameEntity> instances_; // List of game instances
	private static transient boolean isClient_; // On if client
	private static transient String command_;
	private static transient GameFrame gameFrame_;
	
	private static transient GameWorld world_;
	
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
	
	public static void setWorld(GameWorld world)
	{
		world_ = world;
	}
	public static GameWorld getWorld()
	{
		return world_;
	}
	
	public ArrayList<GameEntity> getEntities() // Shows every instance instead of just our children; GameWorld.children_ ought be empty
	{
		return instances_;
	}
	
	public void cleanupEntities()
	{
		for (int i = 0; i < instances_.size(); ++i)
		{
			instances_.get(i).cleanup();
		}
	}
	
	public static void setCommand(String command)
	{
		command_ = command;
	}
	public static String getCommand() // Gets input, resets if not empty, returns null if empty
	{
		if (command_ == null)
			return null;
		else
		{
			String command = command_;
			resetCommand();
			
			return command;
		}
	}
	public static void resetCommand() // Resets all input elements
	{
		command_ = null;
	}

	public static void setClient(boolean isClient)
	{
		isClient_ = isClient;
	}
	
	public static boolean isClient()
	{
		return isClient_;
	}

	public static void setFrame(GameFrame gameFrame)
	{
		gameFrame_ = gameFrame;
	}
	public static GameFrame getFrame()
	{
		return world_.gameFrame_;
	}
	
	@Override
	public GameWorld clone() throws CloneNotSupportedException
	{
		GameWorld world = (GameWorld) super.clone();
		world.instances_ = this.instances_;
		
		return world;
	}
}
