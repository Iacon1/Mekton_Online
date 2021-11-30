// By Iacon1
// Created 09/10/2021
// Handles making, waking, sleeping, and deleting players

package GameEngine.Configurables.ModuleTypes;

import GameEngine.EntityTypes.GameEntity;
import GameEngine.Server.Account;

public interface PlayerHandlerModule extends Module
{
	public GameEntity makePlayer(Account account); // Makes a new player entity for a new account
	public GameEntity wakePlayer(Account account); // Wakes up a returning account's current possessee when they login
	public GameEntity sleepPlayer(Account account); // Wakes up a returning account's current possessee when they logout
	public GameEntity deletePlayer(Account account); // Deletes or detaches a deleted account's current possessee
}
