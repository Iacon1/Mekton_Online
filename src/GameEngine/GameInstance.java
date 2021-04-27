// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.io.*;

public abstract class GameInstance
{
	private int id_; // ID; -1 means dummy
	private int parent_; // Parent object ID
	protected ArrayList<Integer> children_ = new ArrayList<Integer>(); // Children object IDs
	
	protected static ArrayList<GameInstance> instances_ = new ArrayList<GameInstance>(); // All game instances
	
	private void createID() // Assigns a new ID
	{
		for (int i = 0; i < instances_.size(); ++i)
		{
			if (instances_.get(i) == null) // Nothing in this slot
			{
				this.id_ = i;
				instances_.set(i, this);
				return;
			}
		}
		
		instances_.add(this); // Whole thing was full
		this.id_ = instances_.size() - 1;
	}
	private void destroyID() // Removes us from the list; Watch out for dead references
	{
		getInstance(parent_).removeChild(this);
		instances_.set(id_, null);
		
	}
	private GameInstance getInstance(int id) // Gets a reference to the gameInstance at id
	{
		return instances_.get(id);
	}
	private GameInstance copy(int id) // Returns a copy of the gameInstance at id
	{
		return null; // TODO HOW? Damn you, Java
	}
	
	public static GameInstance NewInstance()
	{
		GameInstance instance = new GameInstance();
		instance.createID(); // Give us an ID
	}

	
	public GameInstance getParent() // Gets parent object; Returns null if none
	{
		return parent_;
	}
	
	public void removeChild(GameInstance child) // Removes a child without destroying it
	{
		this.children_.remove(child.id_);
	}
	public void addChild(GameInstance child) // Adds a new child, replacing its old parent if needed
	{
		if (getInstance(child.parent_) != null)
			getInstance(child.parent_).removeChild(child);
		child.parent_ = this.id_;
	}
	
	public ArrayList<GameInstance> getChildren()
	{
		ArrayList<GameInstance> children = new ArrayList<GameInstance>();
		
		for (int i = 0; i < children_.size(); ++i)
		{
			return 
		}
	}
	
	public abstract void getName(); // Gets object name
	public abstract void render(int x, int y, Graphics2D g); // Renders to g @ x, y on screen
}
