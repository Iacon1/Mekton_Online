// By Iacon1
// Created 05/10/2021
// Contains password & username

package GameEngine.PacketTypes;

public class LoginPacket extends Packet // TODO encrypt these
{
	public String username;
	public String password;
	
	public boolean newUser; // A new user?
}
