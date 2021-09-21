// By Iacon1
// Created 04/26/2021
// All data about the game world and such

package GameEngine;

import java.util.ArrayDeque;
import java.util.ArrayList;

import GameEngine.Client.GameFrame;
import GameEngine.EntityTypes.GameEntity;
import Utils.GappyArrayList;

public class GameInfo
{
	// Things that might need to be communicated
	public static class GameWorld
	{
		protected GappyArrayList<GameEntity> instances; // List of game instances
		public GameWorld()
		{
			instances = new GappyArrayList<GameEntity>();
		}
		
		public int addEntity(GameEntity entity)
		{
			int index = instances.getFirstGap();
			instances.add(entity);
			return index;
		}
		public int findEntity(GameEntity entity)
		{
			return instances.indexOf(entity);
		}
		public GameEntity getEntity(int id)
		{
			return instances.get(id);
		}
		public void removeEntity(int id, boolean recurse) // Removes an entity and, if needed, its children
		{
			GameEntity entity = getEntity(id);
			entity.getParent().removeChild(entity, false);
			entity.clearChildren(true);
			instances.remove(id);
			
		}
		
		public ArrayList<GameEntity> getRootEntities() // Returns every instance with no parent
		{
			ArrayList<GameEntity> array = new ArrayList<GameEntity>();
			for (int i = 0; i < instances.size(); ++i)
			{
				GameEntity instance = instances.get(i);
				if (instance == null) continue;
				if (instance.getParent() == null) array.add(instance);
			}
			
			return array;
		}
		
		/** Returns the list of instances.
		 *  No guarantee that any one entity *isn't* null!
		 */
		public ArrayList<GameEntity> getEntities() // Shows every instance instead of just our children; GameWorld.children ought be empty
		{
			return instances;
		}
	}
	private static transient GameWorld world; // Holds any things we might need to transfer to client
	
	// Things that don't need to be communicated
	
	// Client-side
	private static transient GameFrame frame;
	private static transient boolean isClient; // On if client
	private static transient String command;
	private static transient int possessee; // Client's current player form
	private static transient Point2D camera;

	private GameInfo() // Static class, do not call
	{
		world = new GameWorld();
	}
	
	public static void initWorld(GameWorld world)
	{
		world = new GameWorld();
	}
	public static void setWorld(GameWorld world)
	{
		GameInfo.world = world;
	}
	public static GameWorld getWorld()
	{
		return world;
	}
	
	public static void setFrame(GameFrame frame)
	{
		GameInfo.frame = frame;
	}
	public static GameFrame getFrame()
	{
		return frame;
	}
	
	public static void setClient(boolean isClient)
	{
		GameInfo.isClient = isClient;
	}
	public static boolean isClient()
	{
		return isClient;
	}
	
	public static void resetCommand() // Resets all input elements
	{
		GameInfo.command = null;
	}
	public static void setCommand(String command)
	{
		GameInfo.command = command;
	}
	public static String getCommand() // Gets input, resets if not empty, returns null if empty
	{
		if (GameInfo.command == null)
			return null;
		else
		{
			String command = GameInfo.command;
			resetCommand();
			
			return command;
		}
	}
	
	public static void setPossessee(int id)
	{
		GameInfo.possessee = id;
	}
	public static int getPossessee()
	{
		return possessee;
	}

	public static void setCamera(Point2D camera)
	{
		GameInfo.camera = camera;
	}
	public static Point2D getCamera()
	{
		return camera;
	}
}
