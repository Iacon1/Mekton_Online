// By Iacon1
// Created 06/16/2021
// Map screen

package Modules.BaseModule.HandlerStates;

import GameEngine.PacketTypes.GameDataPacket;
import Modules.BaseModule.ClientHandlerThread;
import Net.StateFactory;
import Net.ThreadState;
import Utils.Logging;

public class MainScreen implements ThreadState<ClientHandlerThread>
{
	private StateFactory factory_;
	
	public MainScreen(StateFactory factory)
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
		Logging.logNotice(packet.toJSON());
		return packet.toJSON();
	}

	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}
}
