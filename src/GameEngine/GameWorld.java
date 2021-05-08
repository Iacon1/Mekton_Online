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
		
		switch (parsedCommand[0])
		{
		case "move": // You want to move
			if (source instanceof PhysicalObject) // Has position
			{
				PhysicalObject physSource = (PhysicalObject) source;
				switch (parsedCommand[1]) // Direction
				{
				case "down":
					physSource.move(PhysicalObject.Direction.down, 1); break;
				case "up":
					physSource.move(PhysicalObject.Direction.up, 1); break;
				
				case "north": case "no": case "n":
					physSource.move(PhysicalObject.Direction.north, 1); break;
				case "northeast": case "ne":
					physSource.move(PhysicalObject.Direction.northEast, 1); break;
				case "northwest": case "nw":
					physSource.move(PhysicalObject.Direction.northWest, 1); break;
				
				case "south": case "so": case "s":
					physSource.move(PhysicalObject.Direction.south, 1); break;
				case "southeast": case "se":
					physSource.move(PhysicalObject.Direction.southEast, 1); break;
				case "southwest": case "sw":
					physSource.move(PhysicalObject.Direction.southWest, 1); break;
				}
			}
			break;
		default: // IDK what you're doing
			break;
		}
	}
	
	public static GameWorld getWorld()
	{
		return world_;
	}
}
