// By Iacon1
// Created 05/31/2021
// Module file

package Modules.TestModule;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import GameEngine.Hexmap;
import GameEngine.Configurables.Module;
import Modules.TestModule.TestHexmap;
import Net.StateFactory;
import Server.Account;
<<<<<<< Updated upstream
import Server.Server;
=======
import Server.GameServer;
import Utils.JSONManager;
import Utils.MiscUtils;
>>>>>>> Stashed changes

public class TestModule implements Module
{
	private ModuleConfig config_;
	
	
	public TestModule()
	{
		config_ = new ModuleConfig();
		
		config_.doesImplement_.put("setup", true);
		config_.doesImplement_.put("loadWorld", true);
		
		config_.doesImplement_.put("makePlayer", true);
		config_.doesImplement_.put("wakePlayer", true);
		config_.doesImplement_.put("sleepPlayer", false);
		config_.doesImplement_.put("deletePlayer", false);
		
		config_.doesImplement_.put("clientFactory", false);
		config_.doesImplement_.put("handlerFactory", false);
	}
	
	@Override
	public ModuleConfig getConfig()
	{
		return config_;
	}

	@Override
	public GameWorld setup()
	{
		GameWorld gameWorld = new GameWorld();
		TestHexmap map = new TestHexmap(gameWorld, new TestHexData());
		map.setDimensions(18, 9, 1);
		return gameWorld;
	}

	@Override
	public GameWorld loadWorld(String server)
	{
		return null;
	}

	@Override
	public GameEntity makePlayer(Server server, Account account)
	{
		DummyPlayer player = new DummyPlayer(server.gameWorld_); // Adds a guy to the map
		account.possessee = player.getId();
		server.gameWorld_.getRootEntities().get(0).addChild(player);
		player.setPos(2, 2, 0);
		
		return player;
	}
	@Override
	public GameEntity wakePlayer(Server server, Account account)
	{
		DummyPlayer player = new DummyPlayer(server.gameWorld_); // Adds a guy to the map
		account.possessee = player.getId();
		server.gameWorld_.getRootEntities().get(0).addChild(player);
		player.setPos(2, 2, 0);
		
		return player;
	}
	@Override
	public GameEntity sleepPlayer(Server server, Account account)
	{
		return null;
	}
	@Override
	public GameEntity deletePlayer(Server server, Account account)
	{
		return null;
	}

	public StateFactory clientFactory()
	{
		return null;
	}
	public StateFactory handlerFactory()
	{
		return null;
	}
}
