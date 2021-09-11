// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine.EntityTypes;

import java.util.ArrayList;

import GameEngine.GameCanvas;
import GameEngine.GameInfo;


public abstract class GameEntity
{
	private int parentId_; // Parent object index; -1 means none
	protected ArrayList<Integer> childrenIds_; // Children object indices

	public int getId()
	{
		return GameInfo.getWorld().findEntity(this);
	}
	
	public GameEntity()
	{
		if (GameInfo.getWorld() != null) GameInfo.getWorld().addEntity(this);
		// This seems dumb, but note if it's ever null then it will likely be replaced by a new world that already contains us
		this.parentId_ = -1;
		childrenIds_ = new ArrayList<Integer>();
	}
	
	public static GameEntity getEntity(int id)
	{
		return GameInfo.getWorld().getEntity(id);
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
	/**
	* Draws to canvas.
	* <p>
	* 
	* @param  canvas Canvas to draw to.
	*/
	public abstract void render(GameCanvas canvas);
}
