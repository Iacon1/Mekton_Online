// By Iacon1
// Created 4/21/2021
// Updates the game state and receives input to send to the server

package Client;

import java.awt.Container;
import java.util.HashMap;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.StateGiverModule;
import GameEngine.Net.StateFactory;
import GameEngine.Net.StatefulConnectionPairThread;
import GameEngine.PacketTypes.ServerInfoPacket;

public class GameClientThread extends StatefulConnectionPairThread
{
	private StateFactory stateFactory; // Where we get our states
	
	private ServerInfoPacket serverInfo; // The server info we received
	
	private HashMap<String, Container> containers; // All currently-open UI

	public GameClientThread()
	{
		super();
		stateFactory = ModuleManager.getHighestOfType(StateGiverModule.class).clientFactory();
		containers = new HashMap<String, Container>();
		initState(stateFactory.getState(0));
	}
	
	@Override
	public void onClose()
	{
		if (containers != null) // Close all containers
		{
			for (String name : containers.keySet())
			{
				containers.get(name).setVisible(false);
				containers.get(name).setEnabled(false);
			}
		}
	}
	
	public void setContainer(String name, Container container)
	{
		containers.put(name, container);
	}
	
	public Container getContainer(String name)
	{
		return containers.get(name);
	}

	public void setInfo(ServerInfoPacket serverInfo)
	{
		this.serverInfo = serverInfo;
	}
	public ServerInfoPacket getInfo()
	{
		return serverInfo;
	}
}

