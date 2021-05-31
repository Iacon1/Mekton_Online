// By Iacon1
// Created 05/30/2021
// A module contains any code that might be different for different servers. Multiple can be loaded at once, with each having a priority.

package GameEngine.Configurables;

import java.util.HashMap;

import GameEngine.GameWorld;

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
		// TODO keep this updated
		
		public transient int priority_; // 0 is highest, 1 is second-highest, etc.
		
		public ModuleConfig()
		{
			doesImplement_ = new HashMap<String, Boolean>();
		}
	}
	
	public ModuleConfig getConfig();
	
	public GameWorld setup(); // Sets up a new world; Only used the first time the server is run
	public GameWorld loadWorld(String server);
}
