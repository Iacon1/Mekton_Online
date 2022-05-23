// By Iacon1
// Created 06/16/2021
// Map screen

package GameEngine.Server.HandlerStates;

import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import GameEngine.PacketTypes.GameDataPacket;
import GameEngine.Server.ClientHandlerThread;
import Utils.JSONManager;

public class MainScreen implements ThreadState<ClientHandlerThread>
{
	private StateFactory factory;
	
	public MainScreen(StateFactory factory)
	{
		this.factory = factory;
	}
	
	@Override
	public void onEnter(ClientHandlerThread parentThread) {}

	@Override
	public void processInput(String input, ClientHandlerThread parentThread)
	{
		if (input != null)
		{
			parentThread.getParent().runCommand(parentThread.getAccount().username, input);
		}
	}
	@Override
	public String processOutput(ClientHandlerThread parentThread)
	{
		GameDataPacket packet = new GameDataPacket(parentThread.getAccount());
		return JSONManager.serializeJSON(packet);
	}
	
	@Override
	public StateFactory getFactory()
	{
		return factory;
	}
}
