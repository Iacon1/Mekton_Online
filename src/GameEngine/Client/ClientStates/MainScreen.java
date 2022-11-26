// By Iacon1
// Created 06/16/2021
// Login

package GameEngine.Client.ClientStates;

import GameEngine.GameInfo;
import GameEngine.Client.GameClientThread;
import GameEngine.Client.GameFrame;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import GameEngine.PacketTypes.GameDataPacket;
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
		JSONManager.invalidate();
		parentThread.setContainer("main", new GameFrame());
		parentThread.getContainer("main");
		frameLoaded = false;
	}

	@Override
	public void processInput(String input, GameClientThread parentThread)
	{
		GameDataPacket packet = JSONManager.deserializeJSON(input, GameDataPacket.class);
		if (packet == null)
			return;
		GameFrame frame = (GameFrame) parentThread.getContainer("main");

		if (frame == null && frameLoaded)
			parentThread.close();
		else if (frame == null)
			return;
		else
			frameLoaded = true;

		frame.updateUIStuff(packet.renderQueue);
	}

	@Override
	public String processOutput(GameClientThread parentThread)
	{
		if (GameInfo.clientInput != null && !GameInfo.clientInput.inputs.isEmpty()) // We got input
		{
			String output = JSONManager.serializeJSON(GameInfo.clientInput);
			GameInfo.clearInput();
			return output;
		}
		else return null;
	}

	@Override
	public StateFactory getFactory()
	{
		return factory;
	}

}
