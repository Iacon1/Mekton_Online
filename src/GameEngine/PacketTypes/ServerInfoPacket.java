// By Iacon1
// Created 04/24/2021
// Give server info when starting up

package GameEngine.PacketTypes;

public class ServerInfoPacket extends Packet
{
	public String serverName; // Server name
	public String version; // Version of MtO that the server is running
	public String resourceFolder; // Folder that should hold resources for this game
	public enum Note
	{
		full,
		good;
	}
	public Note note; // Anything special?
	
}
