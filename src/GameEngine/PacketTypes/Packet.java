// By Iacon1
// Created 04/24/2021
// Packet

package GameEngine.PacketTypes;

import Utils.JSONManager;

public abstract class Packet
{
	public final String toJSON()
	{
		return JSONManager.serializeJSON(this);
	}
	
	public final Packet fromJSON(String string)
	{
		return (Packet) JSONManager.deserializeJSON(string, this.getClass());
	}
}
