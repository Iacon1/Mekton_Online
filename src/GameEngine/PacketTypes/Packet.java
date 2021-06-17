// By Iacon1
// Created 04/24/2021
// Packet

package GameEngine.PacketTypes;

import Utils.JSONManager;

public class Packet
{
	public String type;
	
	public Packet()
	{
		type = this.getClass().getCanonicalName();
	}
	
	public final String toJSON()
	{
		return JSONManager.serializeJSON(this);
	}
	
	public final Packet fromJSON(String string)
	{
		return (Packet) JSONManager.deserializeJSON(string, this.getClass());
	}
	
	public static final String getType(String string)
	{
		return ((Packet) JSONManager.deserializeJSON(string, Packet.class)).type;
	}
}
