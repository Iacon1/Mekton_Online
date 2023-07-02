// By Iacon1
// Created 06/16/2021
// Map screen

package GameEngine.Server.HandlerStates;

import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GraphicsHandlerModule;
import GameEngine.Graphics.ScreenCanvas;
import GameEngine.Graphics.UtilCanvas;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import GameEngine.PacketTypes.ClientInputPacket;
import GameEngine.PacketTypes.GameDataPacket;
import GameEngine.Server.ClientHandlerThread;
import Utils.DataManager;

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
		if (input == null) return;
		ClientInputPacket packet = DataManager.deserialize(input, ClientInputPacket.class);
		synchronized ((GameInfo.updateLock))
		{
			parentThread.getAccount().processOtherInputs(packet.inputs);
		}
	}
	@Override
	public String processOutput(ClientHandlerThread parentThread)
	{
		ScreenCanvas canvas = new ScreenCanvas();
		ModuleManager.getHighestOfType(GraphicsHandlerModule.class).drawWorld(parentThread.getAccount().getPossessee(), canvas);
		GameDataPacket packet = new GameDataPacket(canvas.getRenderQueue());
		return DataManager.serialize(packet);
	}
	
	@Override
	public StateFactory getFactory()
	{
		return factory;
	}
}
