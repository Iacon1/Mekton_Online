// By Iacon1
// Created 04/26/2021
// For handling the connection client-side

package Modules.BaseModule;

import Utils.*;
import GameEngine.GameEntity;
import GameEngine.Configurables.ModuleManager;
import Net.StateFactory;
import Net.StatefulConnectionPairThread;
import Net.Server.Server;
import Server.Account;

public class ClientHandlerThread extends StatefulConnectionPairThread
{
	private StateFactory stateFactory_; // Where we get our states
	
	protected volatile String username_ = null; // Client's account name
	
	public Account getAccount()
	{
		return parent_.getAccount(username_);
	}
	protected GameEntity getUserEntity()
	{
		return GameEntity.getEntity(parent_.gameWorld_, parent_.getAccount(username_).possessee);
	}
	
	protected static volatile BaseServer parent_;
	
	public ClientHandlerThread()
	{
		super();
		stateFactory_ = ModuleManager.handlerFactory();
		initState(stateFactory_.getState(0));
	}
	public void setParent(BaseServer parent)
	{
		parent_ = parent;
	}
	
	@Override
	public void onClose()
	{
		Logging.logNotice("Client " + socket_.getInetAddress() + " has disconnected.");
	}
	
	public BaseServer getParent()
	{
		return parent_;
	}

	public void setUsername(String username)
	{
		username_ = username;
	}
	public String getUsername()
	{
		return username_;
	}
}
