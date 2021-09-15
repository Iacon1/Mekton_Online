// By Iacon1
// Created 09/10/2021
// Makes the server

package GameEngine.Configurables.ModuleTypes;

import GameEngine.GameServer;

public interface ServerMakingModule
{
	public GameServer makeServer(); // Sets up the server (not what's in the server!)
}
