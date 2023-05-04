// By Iacon1
// Created 06/16/2021
// Login

package GameEngine.Client.ClientStates;

import java.util.Timer;
import java.util.TimerTask;

import GameEngine.GameInfo;
import GameEngine.Client.GameClientThread;
import GameEngine.Client.GameFrame;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import GameEngine.PacketTypes.GameDataPacket;
import Utils.JSONManager;

public class MainScreen implements ThreadState<GameClientThread>
{
	private StateFactory factory;
	private boolean frameLoaded = false;
	private GameDataPacket packet;
	private Object packetLock = new Object();
	private Timer updateTimer = new Timer();
	private GameFrame frame;
	
	private class UpdateTask extends TimerTask
	{
		public void run()
		{
			synchronized (packetLock) {if (frameLoaded && packet != null) frame.updateUIStuff(packet.renderQueue);}
		}
	}
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
		
		updateTimer.scheduleAtFixedRate(new UpdateTask(), 1000 / ConfigManager.getFramerateCap(), 1000 / ConfigManager.getFramerateCap());
	}

	@Override
	public void processInput(String input, GameClientThread parentThread)
	{
		synchronized (packetLock)
		{
			packet = JSONManager.deserializeJSON(input, GameDataPacket.class);
		
			if (packet == null)
				return;
			if (!frameLoaded) frame = (GameFrame) parentThread.getContainer("main");
			
			if (frame == null && frameLoaded) parentThread.close();
			else if (frame == null) return;
			else frameLoaded = true;
		}
	}

	@Override
	public String processOutput(GameClientThread parentThread)
	{
		if (GameInfo.clientInput != null && !GameInfo.clientInput.inputs.isEmpty()) // We got input
		{
			String output = JSONManager.serializeJSON(GameInfo.clientInput);
			GameInfo.clearInput();
			return output;
		} else
			return null;
	}

	@Override
	public StateFactory getFactory()
	{
		return factory;
	}

}
