// By Iacon1
// Created 05/10/2021
// Account data

package Server;

import GameEngine.CommandRunner;
import GameEngine.GameInfo;

import GameEngine.PacketTypes.LoginPacket;

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
	
	public void setHash(LoginPacket packet) // Sets the hash
	{
		hash = packet.getHash(); // More secure probably possible
	}
	public boolean eqHash(LoginPacket packet) // Is the given password equal to our hash?
	{
		return packet.eqHash(hash);
	}
	
	@Override
	public abstract void runCommand(String[] params); // Commands that a player can type

	
}
