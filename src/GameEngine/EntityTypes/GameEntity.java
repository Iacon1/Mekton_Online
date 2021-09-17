// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine.EntityTypes;

import java.util.ArrayList;

import GameEngine.ScreenCanvas;
import GameEngine.GameInfo;
import GameEngine.Point2D;


public abstract class GameEntity
{
	private int parentId_; // Parent object index; -1 means none
	private int ourId_;
	protected ArrayList<Integer> childrenIds_; // Children object indices

	public int getId()
	{
		return ourId_;
	}
	
	public GameEntity()
	{
		if (GameInfo.getWorld() != null) ourId_ = GameInfo.getWorld().addEntity(this);
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
	
	public void removeChild(GameEntity child, boolean recurse)
	{
		child.parentId_ = -1;
		if (recurse) child.clearChildren(true);
		this.childrenIds_.remove(Integer.valueOf(child.getId()));
	}
	public void clearChildren(boolean recurse)
	{
		for (int i = childrenIds_.size() - 1; i >= 0; --i) removeChild(getEntity(i), recurse);
	}
	public void addChild(GameEntity child) // Adds a new child, replacing its old parent if needed
	{
		if (child.parentId_ != -1)
			child.getParent().removeChild(child, false);
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
	public abstract void update(); // Updates regularly
	/**
	* Draws to canvas.
	* <p>
	* 
	* @param  canvas Canvas to draw to.
	*/
	public abstract void render(ScreenCanvas canvas, Point2D camera);
}
