// By Iacon1
// Created 05/10/2021
// Account data

package Server;

import GameEngine.EntityTypes.CommandRunner;

public abstract class Account implements CommandRunner
{
	public String username;
	public int possessee; // Entity our user is possessing
	
	private int hash;
	
	public Account()
	{
		username = null;
		possessee = -1;
		hash = -1;
	}
	
	public void setHash(String password) // Sets the hash
	{
		hash = password.hashCode(); // More secure probably possible
	}
	public boolean eqHash(String password) // Is the given password equal to our hash?
	{
		return (password.hashCode() == hash);
	}
	
	@Override
	public abstract void runCommand(String[] params); // Commands that a player can type
}
