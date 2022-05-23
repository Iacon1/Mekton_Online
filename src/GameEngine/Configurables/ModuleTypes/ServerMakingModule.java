// By Iacon1
// Created 09/10/2021
// Makes the server

package GameEngine.Configurables.ModuleTypes;

import GameEngine.Server.Account;
import GameEngine.Server.GameServer;

public interface ServerMakingModule extends Module
{
	public GameServer makeServer(); // Sets up the server (not what's in the server!)
	
	public Account newAccount(); // Make a new account
}
