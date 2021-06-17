// By Iacon1
// Created 05/30/2021
// A module contains any code that might be different for different servers. Multiple can be loaded at once, with each having a priority.

package GameEngine.Configurables;

import java.util.HashMap;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import Net.StateFactory;
import Server.Account;
import Server.Server;

public interface Module
{
	public static class ModuleConfig
	{
		public String moduleName_; // Module's display name 
		public String moduleVersion_; // Module's version
		public HashMap<String, Boolean> doesImplement_; // Does the module implement each function?
		// Functions:
		// setup
		// loadWorld
		
		// makePlayer
		// wakePlayer
		// sleepPlayer
		// deletePlayer
		
		// clientFactory
		// handlerFactory
		// TODO keep this updated
		
		public transient int priority_; // 0 is highest, 1 is second-highest, etc.
		
		public ModuleConfig()
		{
			doesImplement_ = new HashMap<String, Boolean>();
		}
	}
	
	public ModuleConfig getConfig();
	
	public GameWorld setup(); // Sets up a new world; Only used the first time the server is run
	public GameWorld loadWorld(String server); // Loads a saved world
	
	public GameEntity makePlayer(Server server, Account account); // Makes a new player entity for a new account
	public GameEntity wakePlayer(Server server, Account account); // Wakes up a returning account's current possessee when they login
	public GameEntity sleepPlayer(Server server, Account account); // Wakes up a returning account's current possessee when they logout
	public GameEntity deletePlayer(Server server, Account account); // Deletes or detaches a deleted account's current possessee
	
	public StateFactory clientFactory(); // Client factory
	public StateFactory handlerFactory(); // Handler factory
}
