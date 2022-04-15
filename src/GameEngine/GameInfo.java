// By Iacon1
// Created 04/26/2021
// All data about the game world and such

package GameEngine;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Client.GameFrame;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Server.Account;
import GameEngine.Server.GameServer;
import Utils.GappyArrayList;
import Utils.JSONManager;
import Utils.MiscUtils;

public class GameInfo
{
	// Things that might need to be communicated
	public static class GameWorld
	{
		protected GappyArrayList<GameEntity> instances; // List of game instances; Important that we note it to be a gappy array list and not a normal one.
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
			if (entity.getParent() != null) entity.getParent().removeChild(entity, false);
			entity.clearChildren(true);
			instances.remove(id);
			
		}
		public void removeEntity(GameEntity entity, boolean recurse)
		{
			removeEntity(entity.getId(), recurse);
		}
		
		public List<GameEntity> getRootEntities() // Returns every instance with no parent
		{
			List<GameEntity> array = new ArrayList<GameEntity>();
			for (int i = 0; i < instances.size(); ++i)
			{
				GameEntity instance = instances.get(i);
				if (instance == null) continue;
				if (instance.getParent() == null) array.add(instance);
			}
			
			return array;
		}
		
		/** Returns the list of instances of a certain type.
		 * 
		 *  @param type The class of the entity type to search for.
		 */
		public <T extends GameEntity> List<T> getEntitiesOfType(Class<T> type)
		{
			List<T> array = new ArrayList<T>();
			
			for (int i = 0; i < instances.size(); ++i)
			{
				GameEntity instance = instances.get(i);
				if (instance == null) continue;
				if (type.isAssignableFrom(instance.getClass())) array.add((T) instance);
			}
			
			return array;
		}
		
		/** Returns the list of instances.
		 */
		public List<GameEntity> getEntities() // Shows every instance
		{
			return instances; //getEntitiesOfType(GameEntity.class);
		}
	}
	private static transient GameWorld world; // Holds any things we might need to transfer to client
	
	// Things that don't need to be communicated
	
	// Client-side
	private static transient GameFrame frame;
	private static transient boolean isClient; // On if client
	private static transient String command;
	private static transient int possessee; // Client's current player form
//	private static transient Point2D camera;
	private static transient String serverPackPrefix; // Server pack's path

	// Server-side
	private static transient GameServer server;
	
	private GameInfo() // Static class, do not call
	{
		world = new GameWorld();
	}
	
	public static void initWorld(GameWorld world)
	{
		world = new GameWorld();
	}
	public static boolean loadWorld()
	{
		String path = MiscUtils.getAbsolute("Local Data/Server/world.JSON");
		world = JSONManager.deserializeJSON(MiscUtils.readText(path), GameWorld.class);
		if (world == null) return false;
		else return true;
	}
	public static void saveWorld()
	{
		String path = MiscUtils.getAbsolute("Local Data/Server/world.JSON");
		MiscUtils.saveText(path, JSONManager.serializeJSON(world));
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
	
	public static void setServer(GameServer server)
	{
		GameInfo.server = server;
	}
	public static GameServer getServer()
	{
		return server;
	}
	public static Account getAccount(int possessorID)
	{
		return server.getAccount(possessorID);
	}
	public static void resetCommand() // Resets all input elements
	{
		GameInfo.command = null;
	}
	public static void setCommand(String command)
	{
		GameInfo.command = command;
	}
	public static void addCommand(String command)
	{
		if (GameInfo.command == null) setCommand(command);
		else GameInfo.command = GameInfo.command + "; " + command;
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

/*	public static void setCamera(Point2D camera)
	{
		GameInfo.camera = camera;
	}
	public static Point2D getCamera()
	{
		return camera;
	}
*/
	
	public static void setServerPack(String serverPack)
	{
		GameInfo.serverPackPrefix = "Resources/Server Packs/" + serverPack + "/";
	}
	public static boolean hasServerPack()
	{
		return serverPackPrefix != null;
	}
	/** Returns the absolute location of a server-pack-relative file or folder.
	 * 
	 */
	public static String getServerPackResource(String resourceName)
	{
		return MiscUtils.getAbsolute(serverPackPrefix + "/" + resourceName);
	}
}
