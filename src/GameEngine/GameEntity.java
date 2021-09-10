// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine;

import java.util.ArrayList;

public abstract class GameEntity
{
	private int parentId_; // Parent object index; -1 means none
	protected ArrayList<Integer> childrenIds_; // Children object indices
	private transient GameWorld world_; // Our world that we occupy
	
	public int getId()
	{
		return world_.instances_.indexOf(this);
	}
	
	public GameEntity()
	{
		this.world_ = null;
		this.parentId_ = -1;
		childrenIds_ = new ArrayList<Integer>();
	}
	public GameEntity(GameWorld world)
	{
		world_ = world;
		world_.instances_.add(this);
		this.parentId_ = -1;
		childrenIds_ = new ArrayList<Integer>();
	}
	
	public void setWorld(GameWorld world)
	{
		world_ = world;
	}
	
	public static GameEntity getEntity(GameWorld world, int id)
	{
		return world.instances_.get(id);
	}
	
	public GameEntity getParent() // Gets parent object; Returns null if none
	{
		if (parentId_ == -1) return null;
		else return getEntity(world_, parentId_);
	}
	
	public void removeChild(GameEntity child) // Removes a child without destroying it
	{
		child.parentId_ = -1;
		this.childrenIds_.remove(child.getId());
	}
	public void addChild(GameEntity child) // Adds a new child, replacing its old parent if needed
	{
		if (child.parentId_ != -1)
			child.getParent().removeChild(child);
		child.parentId_ = this.getId();
		childrenIds_.add(child.getId());
	}
	public GameEntity getChild(int i) // Gets child #i
	{
		return getEntity(world_, childrenIds_.get(i));
	}
	public ArrayList<GameEntity> getChildren()
	{
		ArrayList<GameEntity> children = new ArrayList<GameEntity>();
		for (int i = 0; i < childrenIds_.size(); ++i)
		{
			children.add(getEntity(world_, childrenIds_.get(i)));
		}
		
		return children;
	}
	
	public abstract String getName(); // Gets object name
	public abstract void render(int pX, int pY, GameCanvas canvas);
}
