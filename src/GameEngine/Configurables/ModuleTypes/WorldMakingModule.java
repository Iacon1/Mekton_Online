// By Iacon1
// Created 09/10/2021
// Handles making and loading the world

package GameEngine.Configurables.ModuleTypes;

public interface WorldMakingModule extends Module
{
	public void newWorld(); // Sets up a new world; Only used the first time the server is run
	public void loadWorld(String server); // Loads a saved world
}
