// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine;

import java.util.ArrayList;

import java.awt.Graphics2D;

public abstract class GameEntity
{
	private int parentId_; // Parent object index; -1 means none
	protected ArrayList<Integer> childrenIds_; // Children object indices
	
	private int getId()
	{
		return GameWorld.getWorld().instances_.indexOf(this);
	}
	
	public GameEntity()
	{
		GameWorld.getWorld().instances_.add(this);
		this.parentId_ = -1;
		childrenIds_ = new ArrayList<Integer>();
	}
	
	public static GameEntity getEntity(int id)
	{
		return GameWorld.getWorld().instances_.get(id);
	}
	
	public GameEntity getParent() // Gets parent object; Returns null if none
	{
		if (parentId_ == -1) return null;
		else return getEntity(parentId_);
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
		return getEntity(childrenIds_.get(i));
	}
	public ArrayList<GameEntity> getChildren()
	{
		ArrayList<GameEntity> children = new ArrayList<GameEntity>();
		for (int i = 0; i < childrenIds_.size(); ++i)
		{
			children.add(getEntity(childrenIds_.get(i)));
		}
		
		return children;
	}
	
	public abstract String getName(); // Gets object name
	public abstract void render(int pX, int pY, Graphics2D g); // Renders to g @ x, y on screen
}
