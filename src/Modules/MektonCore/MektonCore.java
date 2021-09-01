// By Iacon1
// Created 06/17/2021
//

package Modules.MektonCore;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import GameEngine.GameFrame;
import GameEngine.GameEntity;
import GameEngine.GameWorld;
import GameEngine.GameCanvas;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.Module;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.Module.ModuleConfig;
import Net.StateFactory;
import Server.Account;
import Server.GameServer;

public class MektonCore implements Module
{
	private ModuleConfig config_;

	@Override
	public ModuleConfig getConfig()
	{
		config_ = new ModuleConfig();
		
		config_.doesImplement_.put("makeServer", false);
		config_.doesImplement_.put("setup", false);
		config_.doesImplement_.put("loadWorld", false);
		
		config_.doesImplement_.put("drawWorld", false);
		
		config_.doesImplement_.put("makePlayer", false);
		config_.doesImplement_.put("wakePlayer", false);
		config_.doesImplement_.put("sleepPlayer", false);
		config_.doesImplement_.put("deletePlayer", false);
		
		config_.doesImplement_.put("clientFactory", false);
		config_.doesImplement_.put("handlerFactory", false);
		
		return config_;
	}

	@Override
	public void init()
	{
	}

	@Override
	public GameServer makeServer()
	{
		return null;
	}
	@Override
	public GameWorld setup()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameWorld loadWorld(String server)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawWorld(GameWorld world, GameCanvas canvas)
	{
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public GameEntity makePlayer(GameServer server, Account account)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GameEntity wakePlayer(GameServer server, Account account)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GameEntity sleepPlayer(GameServer server, Account account)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GameEntity deletePlayer(GameServer server, Account account)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateFactory clientFactory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateFactory handlerFactory()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
