// By Iacon1
// Created 04/26/2021
// All data about the game world and such

package GameEngine;

import java.util.ArrayDeque;
import java.util.ArrayList;

import GameEngine.EntityTypes.GameEntity;

public class GameInfo
{
	// Things that might need to be communicated
	public static class GameWorld
	{
		protected ArrayList<GameEntity> instances_; // List of game instances
		protected ArrayDeque<Integer> gaps_; // Gaps can form in the entity list when things are removed
		public GameWorld()
		{
			instances_ = new ArrayList<GameEntity>();
			gaps_ = new ArrayDeque<Integer>();
		}
		
		public int addEntity(GameEntity entity)
		{
			if (gaps_.isEmpty())
			{
				instances_.add(entity);
				return instances_.size() - 1;
			}
			else
			{
				int gap = gaps_.pop();
				instances_.set(gap, entity);
				return gap;
			}
		}
		public int findEntity(GameEntity entity)
		{
			return instances_.indexOf(entity);
		}
		public GameEntity getEntity(int id)
		{
			return instances_.get(id);
		}
		public void removeEntity(int id, boolean recurse) // Removes an entity and, if needed, its children
		{
			GameEntity entity = getEntity(id);
			entity.getParent().removeChild(entity, false);
			entity.clearChildren(true);
			gaps_.push(id);
			instances_.set(id, null);
			
		}
		
		public ArrayList<GameEntity> getRootEntities() // Returns every instance with no parent
		{
			ArrayList<GameEntity> array = new ArrayList<GameEntity>();
			for (int i = 0; i < instances_.size(); ++i)
			{
				GameEntity instance = instances_.get(i);
				if (instance == null) continue;
				if (instance.getParent() == null) array.add(instance);
			}
			
			return array;
		}
		
		/** Returns the list of instances.
		 *  No guarantee that it's not null!
		 */
		public ArrayList<GameEntity> getEntities() // Shows every instance instead of just our children; GameWorld.children_ ought be empty
		{
			return instances_;
		}
	}
	private static transient GameWorld world_; // Holds any things we might need to transfer to client
	
	// Things that don't need to be communicated
	
	// Client-side
	private static transient GameFrame frame_;
	private static transient boolean isClient_; // On if client
	private static transient String command_;
	private static transient int possessee_; // Client's current player form
	private static transient Point2D camera_;
	private static transient int gui_;
	
	private GameInfo() // Static class, do not call
	{
		world_ = new GameWorld();
	}
	
	public static void initWorld(GameWorld world)
	{
		world_ = new GameWorld();
	}
	public static void setWorld(GameWorld world)
	{
		world_ = world;
	}
	public static GameWorld getWorld()
	{
		return world_;
	}
	
	public static void setFrame(GameFrame frame)
	{
		frame_ = frame;
	}
	public static GameFrame getFrame()
	{
		return frame_;
	}
	
	public static void setClient(boolean isClient)
	{
		isClient_ = isClient;
	}
	public static boolean isClient()
	{
		return isClient_;
	}
	
	public static void resetCommand() // Resets all input elements
	{
		command_ = null;
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
	
	public static void setPossessee(int id)
	{
		possessee_ = id;
	}
	public static int getPossessee()
	{
		return possessee_;
	}

	public static void setCamera(Point2D camera)
	{
		camera_ = camera;
	}
	public static Point2D getCamera()
	{
		return camera_;
	}
	
	public static void setGUI(int gui)
	{
		gui_ = gui;
	}
	public static int getGUI()
	{
		return gui_;
	}
}
