// By Iacon1
// Created 05/09/2021
// Returns true if a command was found

package GameEngine.CommandListeners;

import GameEngine.GameEntity;

public abstract class CommandListener
{
	public CommandListener() {}
	
	public abstract boolean runCommand(GameEntity source, String[] params);
}
