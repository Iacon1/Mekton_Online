// By Iacon1
// Created 06/16/2021
// Login

package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import GameEngine.PacketTypes.GameDataPacket;
import Modules.BaseModule.ClientFrames.ClientMainGameFrame;
import Modules.BaseModule.ClientFrames.ClientGameWindow;
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
		ClientGameWindow.main(parentThread);
	}

	@Override
	public void processInput(String input, GameClientThread parentThread)
	{
		GameDataPacket packet = new GameDataPacket();
		packet = (GameDataPacket) packet.fromJSON(input);
		parentThread.setWorld(packet.ourView);
		ClientMainGameFrame frame = (ClientMainGameFrame) parentThread.getContainer();
		frame.updateUIStuff(packet);
	}
	
	@Override
	public String processOutput(GameClientThread parentThread)
	{
		ClientMainGameFrame frame = (ClientMainGameFrame) parentThread.getContainer();
		String input = frame.getCommand();
		if (input != null) // We got input
			return input;
		return null;
	}

	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}

}
