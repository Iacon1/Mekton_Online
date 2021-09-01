// By Iacon1
// Created 05/10/2021
// Contains password & username

package GameEngine.PacketTypes;

public class LoginPacket extends Packet // TODO encrypt these (maybe encrypt *all* packets?)
{
	public String username;
	private int hash;
	
	public void setHash(String password) // Sets the hash
	{
		hash = password.hashCode(); // More secure probably possible
	}
	
	public int getHash() // TODO find a way around this
	{
		return hash;
	}
	
	public boolean eqHash(int newHash)
	{
		return hash == newHash;
	}
	
	public boolean newUser; // A new user?
}
