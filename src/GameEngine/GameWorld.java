// By Iacon1
// Created 04/26/2021
// All data about the game world

package GameEngine;

import java.util.ArrayList;

public class GameWorld
{
	protected ArrayList<GameEntity> instances_; // List of game instances

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
}
