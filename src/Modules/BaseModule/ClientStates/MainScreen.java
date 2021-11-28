// By Iacon1
// Created 06/16/2021
// Login

package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import GameEngine.GameInfo;
import GameEngine.Client.GameFrame;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import Modules.BaseModule.PacketTypes.GameDataPacket;
import Utils.JSONManager;

public class MainScreen implements ThreadState<GameClientThread>
{
	private StateFactory factory;
	private boolean frameLoaded = false;
	
	public MainScreen(StateFactory factory)
	{
		this.factory = factory;
	}
	
	@Override
	public void onEnter(GameClientThread parentThread)
	{
		parentThread.setContainer("main", new GameFrame());
		parentThread.getContainer("main");
		frameLoaded = false;
	}

	public void processInput(String input, GameClientThread parentThread, boolean mono)
	{
		GameDataPacket packet = JSONManager.deserializeJSON(input, GameDataPacket.class);
		if (packet == null) return;
		GameInfo.setWorld(packet.ourView);
		GameFrame frame = (GameFrame) parentThread.getContainer("main");
		
		if (frame == null && frameLoaded) parentThread.close();
		else if (frame == null) return;
		else frameLoaded = true;
		
		frame.updateUIStuff();
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
		return factory;
	}

}
