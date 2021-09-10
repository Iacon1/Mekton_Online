// By Iacon1
// Created 06/16/2021
// Map screen

package Modules.BaseModule.HandlerStates;

import Modules.BaseModule.ClientHandlerThread;
import Modules.BaseModule.PacketTypes.GameDataPacket;
import Net.StateFactory;
import Net.ThreadState;
import Utils.JSONManager;
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

	public void processInput(String input, ClientHandlerThread parentThread, boolean mono)
	{
		if (input != null)
		{
			Logging.logNotice("Client " + parentThread.getAccount().username + " used command: \"" + input + "\"");
			parentThread.getParent().runCommand(parentThread.getAccount().username, input);
		}
	}
	public String processOutput(ClientHandlerThread parentThread, boolean mono)
	{
		GameDataPacket packet = new GameDataPacket();
		return JSONManager.serializeJSON(packet);
	}

	@Override
	public void processInputTrio(String input, ClientHandlerThread parentThread) {processInput(input, parentThread, false);}
	@Override
	public String processOutputTrio(ClientHandlerThread parentThread) {return processOutput(parentThread, false);}
	
	@Override
	public void processInputMono(String input, ClientHandlerThread parentThread) {processInput(input, parentThread, true);}
	@Override
	public String processOutputMono(ClientHandlerThread parentThread) {return processOutput(parentThread, true);}
	
	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}
}
