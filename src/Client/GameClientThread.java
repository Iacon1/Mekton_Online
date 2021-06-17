// By Iacon1
// Created 4/21/2021
// Updates the game state and receives input to send to the server

package Client;

import java.awt.Container;

import GameEngine.GameWorld;
import GameEngine.Configurables.ModuleManager;
import GameEngine.PacketTypes.ServerInfoPacket;
import Net.StateFactory;
import Net.StatefulConnectionPairThread;

public class GameClientThread extends StatefulConnectionPairThread
{
	private StateFactory stateFactory_; // Where we get our states
	
	private ServerInfoPacket serverInfo_; // The server info we received
	
	private Container container_; // The currently-open GUI
	
	private GameWorld gameWorld_;
	
	public GameClientThread()
	{
		super();
		stateFactory_ = ModuleManager.clientFactory();
		initState(stateFactory_.getState(0));
	}
	
	@Override
	public void onClose()
	{
		if (container_ != null)
		{
			container_.setVisible(false);
			container_.setEnabled(false);
		}
	}
	
	public void setContainer(Container container)
	{
		container_ = container;
	}
	
	public Container getContainer()
	{
		return container_;
	}

	public void setInfo(ServerInfoPacket serverInfo)
	{
		serverInfo_ = serverInfo;
	}
	public ServerInfoPacket getInfo()
	{
		return serverInfo_;
	}
	
	public void setWorld(GameWorld world)
	{
		gameWorld_ = world;
	}
	public GameWorld getWorld()
	{
		return gameWorld_;
	}
}

