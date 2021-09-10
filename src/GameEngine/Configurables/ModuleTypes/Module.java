// By Iacon1
// Created 05/30/2021
// A module contains any code that might be different for different servers. Multiple can be loaded at once, with each having a priority.
// Any module must implement this interface

package GameEngine.Configurables.ModuleTypes;

public interface Module
{
	public static class ModuleConfig
	{
		public String moduleName_; // Module's display name 
		public String moduleVersion_; // Module's version
	}
	
	public ModuleConfig getModuleConfig(); // Gives the config
	public void initModule(); // Any special stuff the module needs to do on loading
}
