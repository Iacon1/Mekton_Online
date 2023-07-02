// By Iacon1
// Created 05/30/2021
// A module contains any code that might be different for different servers. Multiple can be loaded at once, with each having a priority.
// Any module must implement this interface

package GameEngine.Configurables.ModuleTypes;

import javax.swing.JPanel;

import GameEngine.Editor.ModulePane;

public interface Module
{
	public static class ModuleConfig
	{
		public String moduleName; // Module's display name
		public String engineVersion; // Engine version module is meant for
		public String moduleVersion; // Module's version
		public String moduleDescription; // Module's description
		
		public String[] dependencies;
		public String[] incompatibilities;
	}
	
	public ModuleConfig getConfig(); // Gives the config
	public void initModule(ModuleConfig defaultConfig); // Any special stuff the module needs to do on loading
	
	public default JPanel getEditorPanel()
	{
		return new ModulePane(getConfig());
	}
}
