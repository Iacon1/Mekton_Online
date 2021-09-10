// By Iacon1
// Created 06/16/2021
// Login

package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import GameEngine.GameFrame;
import GameEngine.GameInfo;
import GameEngine.PacketTypes.GameDataPacket;
import Net.StateFactory;
import Net.ThreadState;

public class MainScreen implements ThreadState<GameClientThread>
{
	private StateFactory factory_;
	
	public MainScreen(StateFactory factory)
	{
		factory_ = factory;
	}
	
	@Override
	public void onEnter(GameClientThread parentThread)
	{
		parentThread.setContainer("main", new GameFrame());
		parentThread.getContainer("main");
	}

	public void processInput(String input, GameClientThread parentThread, boolean mono)
	{
		GameDataPacket packet = new GameDataPacket();
		packet = (GameDataPacket) packet.fromJSON(input);
		GameInfo.setWorld(packet.ourView);
		GameFrame frame = (GameFrame) parentThread.getContainer("main");
		frame.updateUIStuff(packet);
	}
	public String processOutput(GameClientThread parentThread, boolean mono)
	{
		String input = GameInfo.getCommand();
		if (input != null) // We got input
			return input;
		return null;
	}

	@Override
	public void processInputTrio(String input, GameClientThread parentThread) {processInput(input, parentThread, false);}
	@Override
	public String processOutputTrio(GameClientThread parentThread) {return processOutput(parentThread, false);}
	
	@Override
	public void processInputMono(String input, GameClientThread parentThread) {processInput(input, parentThread, true);}
	@Override
	public String processOutputMono(GameClientThread parentThread) {return processOutput(parentThread, true);}
	
	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}

}
