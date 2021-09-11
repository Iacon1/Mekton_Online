// By Iacon1
// Created 05/31/2021
// Module file

package Modules.TestModule;

import GameEngine.GameCanvas;
import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleTypes.GraphicsHandlerModule;
import GameEngine.Configurables.ModuleTypes.Module;
import GameEngine.Configurables.ModuleTypes.PlayerHandlerModule;
import GameEngine.Configurables.ModuleTypes.ServerMakingModule;
import GameEngine.Configurables.ModuleTypes.WorldMakingModule;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.GUITypes.GUIPin;
import Modules.BaseModule.BaseServer;
import Server.Account;
import Modules.MektonCore.Hexmap;

import Server.GameServer;

public class TestModule implements Module, WorldMakingModule, ServerMakingModule, GraphicsHandlerModule, PlayerHandlerModule
{
	@Override
	public ModuleConfig getModuleConfig()
	{
		ModuleConfig config = new ModuleConfig();
		
		return config;
	}

	@Override
	public void initModule()
	{
	}

	@Override
	public GameServer makeServer()
	{
		return new BaseServer<TestAccount>();
	}
	
	@Override
	public void newWorld()
	{
		GameInfo.setWorld(new GameInfo.GameWorld());
		TestHexmap map = new TestHexmap(new TestHexData());
		map.setDimensions(18, 9, 1);
	}
	@Override
	public void loadWorld(String server)
	{
		return;
	}

	@Override
	public void drawWorld(GameCanvas canvas)
	{
		if (GameInfo.getWorld() == null) return;
		canvas.cX_ = 0;
		canvas.cY_ = 0;
		((Hexmap) GameInfo.getWorld().getRootEntities().get(0)).setCameraHeight(1);
		GameInfo.getWorld().getRootEntities().get(0).render(canvas);
	}

	@Override
	public GameEntity makePlayer(Account account)
	{
		DummyPlayer player = new DummyPlayer(); // Adds a guy to the map
		new GUIPin(account);
		GUIPin.findPin(account).addChild(new TestHandle());
		account.possessee = player.getId();
		GameInfo.getWorld().getRootEntities().get(0).addChild(player);
		player.setPos(2, 2, 0);
		
		return player;
	}
	@Override
	public GameEntity wakePlayer(Account account)
	{
		if (account.possessee != -1) return null;
		else return makePlayer(account);
	}
	@Override
	public GameEntity sleepPlayer(Account account)
	{
		return null;
	}
	@Override
	public GameEntity deletePlayer(Account account)
	{
		return null;
	}	
}
