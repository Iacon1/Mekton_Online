// By Iacon1
// Created 06/16/2021
// Login

package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import Client.Frames.ClientGameFrame;
import Client.Frames.ClientGameWindow;
import GameEngine.PacketTypes.GameDataPacket;
import Net.StateFactory;
import Net.ThreadState;

public class MapScreen implements ThreadState<GameClientThread>
{
	private StateFactory factory_;
	
	public MapScreen(StateFactory factory)
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
		ClientGameFrame frame = (ClientGameFrame) parentThread.getContainer();
		frame.updateUIStuff(packet);
	}
	
	@Override
	public String processOutput(GameClientThread parentThread)
	{
		ClientGameFrame frame = (ClientGameFrame) parentThread.getContainer();
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
