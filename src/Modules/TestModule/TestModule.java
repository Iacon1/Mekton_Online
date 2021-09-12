// By Iacon1
// Created 05/31/2021
// Module file

package Modules.TestModule;

import GameEngine.Camera;
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
import Modules.MektonCore.HexCamera;
import Modules.MektonCore.HexEntity;

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
		HexEntity possessee = (HexEntity) GameInfo.getWorld().getEntity(GameInfo.getPossessee());
//		HexCamera.pX = possessee.getPX() - ConfigManager.getScreenWidth() / 2;
//		HexCamera.pY = possessee.getPY() - ConfigManager.getScreenHeight() / 2;
		HexCamera.hZ = possessee.getHZ() + 1;
		GameInfo.getWorld().getRootEntities().get(0).render(canvas);
		Camera.gui.render(canvas);
	}

	@Override
	public GameEntity makePlayer(Account account)
	{
		DummyPlayer player = new DummyPlayer(); // Adds a guy to the map
		new GUIPin(account);
		GUIPin.findPin(account).addChild(new TestHandle());
		account.possessee = player.getId();
		GameInfo.setPossessee(account.possessee);
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
		GameInfo.getWorld().removeEntity(account.possessee, true);
		return null;
	}
	@Override
	public GameEntity deletePlayer(Account account)
	{
		GameInfo.getWorld().removeEntity(account.possessee, true);
		return null;
	}	
}
