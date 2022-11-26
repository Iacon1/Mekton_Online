// By Iacon1
// Created 04/27/2021
// Data given to a player on update

package GameEngine.PacketTypes;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClientInputPacket extends Packet
{
	public Queue<InputEvent> inputs;

	public ClientInputPacket()
	{	
		inputs = new LinkedList<InputEvent>();
	}
}
