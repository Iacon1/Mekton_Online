// By Iacon1
// Created 4/21/2021
// Updates the game state and receives input to send to the server

package Client;

import java.awt.Container;
import java.util.HashMap;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.StateGiverModule;
import GameEngine.PacketTypes.ServerInfoPacket;
import Net.StateFactory;
import Net.StatefulConnectionPairThread;

public class GameClientThread extends StatefulConnectionPairThread
{
	private StateFactory stateFactory_; // Where we get our states
	
	private ServerInfoPacket serverInfo_; // The server info we received
	
	private HashMap<String, Container> containers_; // All currently-open UI

	public GameClientThread()
	{
		super();
		stateFactory_ = ModuleManager.getHighestOfType(StateGiverModule.class).clientFactory();
		containers_ = new HashMap<String, Container>();
		initState(stateFactory_.getState(0));
	}
	
	@Override
	public void onClose()
	{
		if (containers_ != null) // Close all containers
		{
			for (String name : containers_.keySet())
			{
				containers_.get(name).setVisible(false);
				containers_.get(name).setEnabled(false);
			}
		}
	}
	
	public void setContainer(String name, Container container)
	{
		containers_.put(name, container);
	}
	
	public Container getContainer(String name)
	{
		return containers_.get(name);
	}

	public void setInfo(ServerInfoPacket serverInfo)
	{
		serverInfo_ = serverInfo;
	}
	public ServerInfoPacket getInfo()
	{
		return serverInfo_;
	}
}

