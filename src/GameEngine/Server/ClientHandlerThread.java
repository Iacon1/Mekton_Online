// By Iacon1
// Created 04/26/2021
// For handling the connection client-side

package GameEngine.Server;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.PlayerHandlerModule;
import GameEngine.Configurables.ModuleTypes.StateGiverModule;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Net.StateFactory;
import GameEngine.Net.StatefulConnectionPairThread;
import GameEngine.Server.HandlerStates.HandlerStateFactory;
import Utils.Logging;

public class ClientHandlerThread extends StatefulConnectionPairThread
{
	private StateFactory stateFactory; // Where we get our states
	
	protected volatile String username = null; // Client's account name
	
	public Account getAccount()
	{
		return parent.getAccount(username);
	}
	protected GameEntity getUserEntity()
	{
		return parent.getAccount(username).getPossessee();
	}
	
	protected static volatile BaseServer parent;
	
	public ClientHandlerThread()
	{
		super();
		StateGiverModule stateFactoryFactory = ModuleManager.getHighestOfType(StateGiverModule.class);
		if (stateFactoryFactory == null) // Use default factory
			stateFactory = new HandlerStateFactory();
		else stateFactory = stateFactoryFactory.handlerFactory();
	 
		initState(stateFactory.getState(0));
	}
	public void setParent(BaseServer parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void runFunc()
	{
		super.runFunc();
	}
	
	@Override
	public void onClose()
	{
		Logging.logNotice("Client " + socket.getInetAddress() + " has disconnected.");
		ModuleManager.getHighestOfType(PlayerHandlerModule.class).sleepPlayer(getAccount());
	}
	
	public BaseServer getParent()
	{
		return parent;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return username;
	}
}
