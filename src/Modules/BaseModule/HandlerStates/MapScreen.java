// By Iacon1
// Created 06/16/2021
// Map screen

package Modules.BaseModule.HandlerStates;

import GameEngine.PacketTypes.GameDataPacket;
import Net.StateFactory;
import Net.ThreadState;
import Server.ClientHandlerThread;
import Utils.Logging;

public class MapScreen implements ThreadState<ClientHandlerThread>
{
	private StateFactory factory_;
	
	public MapScreen(StateFactory factory)
	{
		factory_ = factory;
	}
	
	@Override
	public void onEnter(ClientHandlerThread parentThread) {}

	@Override
	public void processInput(String input, ClientHandlerThread parentThread)
	{
		if (input != null)
		{
			Logging.logNotice("Client " + parentThread.getAccount().username + " used command: \"" + input + "\"");
			parentThread.getParent().runCommand(parentThread.getAccount().username, input);
		}
	}
	
	@Override
	public String processOutput(ClientHandlerThread parentThread)
	{
		GameDataPacket packet = new GameDataPacket();
		packet.viewWorld(parentThread.getParent().gameWorld_);
		return packet.toJSON();
	}

	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}
}
