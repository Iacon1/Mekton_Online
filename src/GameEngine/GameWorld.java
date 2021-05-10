// By Iacon1
// Created 04/26/2021
// All data about the game world

package GameEngine;

import java.util.ArrayList;

import Utils.JSONManager;

public class GameWorld
{
	protected ArrayList<GameInstance> instances_; // List of game instances
	private transient static GameWorld world_;
	
	public static void init()
	{
		world_ = new GameWorld();
		world_.instances_ = new ArrayList<GameInstance>();
	}
	
	public static void setWorld(GameWorld world)
	{
		world_ = world;
	}
	
	public GameWorld copy() // Returns a copy of self. Warning: Copy's instance list refers to our instances!
	{
		GameWorld world = new GameWorld();
		world.instances_ = new ArrayList<GameInstance>(instances_);
		
		return world;
	}
	
	public ArrayList<GameInstance> getRootInstances() // Returns every instance with no parent
	{
		ArrayList<GameInstance> array = new ArrayList<GameInstance>();
		for (int i = 0; i < instances_.size(); ++i)
		{
			GameInstance instance = instances_.get(i);
			if (instance.getParent() == null) array.add(instance);
		}
		
		return array;
	}
	public ArrayList<GameInstance> getInstances() // Shows every instance instead of just our children; GameWorld.children_ ought be empty
	{
		return instances_;
	}
	
	public void processCommand(GameInstance source, String command) // Process instance executing a command
	{
		String[] parsedCommand = command.split(" ");
		
		source.runCommand(parsedCommand);
	}
	
	public static GameWorld getWorld()
	{
		return world_;
	}
}
