// By Iacon1
// Created 05/31/2021
// Module file

package Modules.TestModule;

import GameEngine.Camera;
import GameEngine.ScreenCanvas;
import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleTypes.GraphicsHandlerModule;
import GameEngine.Configurables.ModuleTypes.Module;
import GameEngine.Configurables.ModuleTypes.PlayerHandlerModule;
import GameEngine.Configurables.ModuleTypes.ServerMakingModule;
import GameEngine.Configurables.ModuleTypes.WorldMakingModule;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.GUITypes.GUIPin;
import Modules.BaseModule.BaseServer;
import Modules.HexUtilities.HexCamera;
import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Server.Account;
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
		BaseServer<TestAccount> server = new BaseServer<TestAccount>();
		return server;
	}
	
	@Override
	public void newWorld()
	{
		GameInfo.setWorld(new GameInfo.GameWorld());
		TestHexmap map = new TestHexmap();
		map.setDimensions(18, 9, 1, new TestHexData());
	}
	@Override
	public void loadWorld(String server)
	{
		return;
	}

	@Override
	public void drawWorld(ScreenCanvas canvas)
	{
		if (GameInfo.getWorld() == null) return;
		HexEntity<AxialHexCoord3D> possessee = (HexEntity<AxialHexCoord3D>) GameInfo.getWorld().getEntity(GameInfo.getPossessee());
//		HexCamera.pX = possessee.getPX() - ConfigManager.getScreenWidth() / 2;
//		HexCamera.pY = possessee.getPY() - ConfigManager.getScreenHeight() / 2;
		HexCamera.hZ = possessee.getPos().z_ + 1;
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
		player.setPos(new AxialHexCoord3D(2, 2, 0));
		
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
