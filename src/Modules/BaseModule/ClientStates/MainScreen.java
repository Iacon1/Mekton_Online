// By Iacon1
// Created 06/16/2021
// Login

package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import GameEngine.GameWorld;
import GameEngine.GameEntity;
import GameEngine.GameFrame;
import GameEngine.PacketTypes.GameDataPacket;
import GameEngine.PacketTypes.Packet;
import Modules.TestModule.DummyPlayer;
import Modules.TestModule.TestHexmap;
import Net.StateFactory;
import Net.ThreadState;
import Utils.JSONManager;

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
		parentThread.setContainer("map", new GameFrame());
		parentThread.getContainer("map");
	}

	public void processInput(String input, GameClientThread parentThread, boolean mono)
	{
		TestHexmap e = JSONManager.deserializeJSON(input, TestHexmap.class);
//		GameDataPacket packet = JSONManager.deserializeJSON(input, GameDataPacket.class);
//		GameWorld.setWorld(packet.ourView);
		GameFrame frame = (GameFrame) parentThread.getContainer("map");
//		frame.updateUIStuff(packet);
	}
	public String processOutput(GameClientThread parentThread, boolean mono)
	{
		String input = GameWorld.getCommand();
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
