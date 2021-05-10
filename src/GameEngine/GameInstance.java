// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine;

import java.util.ArrayList;

import GameEngine.CommandListeners.CommandListener;

import java.awt.Graphics2D;

public abstract class GameInstance
{
	private int parentId_; // Parent object index; -1 means none
	protected ArrayList<Integer> childrenIds_; // Children object indices
	protected ArrayList<CommandListener> listeners_;
	
	protected void addCommandListener(CommandListener listener)
	{
		listeners_.add(listener);
	}
	
	protected static GameInstance getInstance(int id)
	{
		return GameWorld.getWorld().instances_.get(id);
	}
	private int getId()
	{
		return GameWorld.getWorld().instances_.indexOf(this);
	}
	
	public GameInstance()
	{
		GameWorld.getWorld().instances_.add(this);
		this.parentId_ = -1;
		childrenIds_ = new ArrayList<Integer>();
		listeners_ = new ArrayList<CommandListener>();
	}
	
	public GameInstance getParent() // Gets parent object; Returns null if none
	{
		if (parentId_ == -1) return null;
		else return getInstance(parentId_);
	}
	
	public void removeChild(GameInstance child) // Removes a child without destroying it
	{
		child.parentId_ = -1;
		this.childrenIds_.remove(child.getId());
	}
	public void addChild(GameInstance child) // Adds a new child, replacing its old parent if needed
	{
		if (child.parentId_ != -1)
			child.getParent().removeChild(child);
		child.parentId_ = this.getId();
		childrenIds_.add(child.getId());
	}
	public GameInstance getChild(int i) // Gets child #i
	{
		return getInstance(childrenIds_.get(i));
	}
	public ArrayList<GameInstance> getChildren()
	{
		ArrayList<GameInstance> children = new ArrayList<GameInstance>();
		for (int i = 0; i < childrenIds_.size(); ++i)
		{
			children.add(getInstance(childrenIds_.get(i)));
		}
		
		return children;
	}
	public void runCommand(String[] params)
	{
		for (int i = 0; i < listeners_.size(); ++i)
			if (listeners_.get(i).runCommand(this, params)) return;
	}
	
	public abstract String getName(); // Gets object name
	public abstract void render(int pX, int pY, Graphics2D g); // Renders to g @ x, y on screen
}
