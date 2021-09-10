// By Iacon1
// Created 04/24/2021
// Packet

package GameEngine.PacketTypes;

import Utils.JSONManager;

public class Packet
{
//	public String type;
	
	public Packet()
	{
//		type = this.getClass().getCanonicalName();
	}
	
	public final String toJSON()
	{
		return JSONManager.serializeJSON(this);
	}
	
	public static final <T extends Packet> T fromJSON(String string, Class<T> packetClass) // TODO This is useless RN
	{
		T t = JSONManager.deserializeJSON(string, packetClass);
		return t;
	}
	
/*
	public static final String getType(String string)
	{
		return ((Packet) JSONManager.deserializeJSON(string, Packet.class)).type;
	}
*/
}
