// By Iacon1
// Created 05/31/2021
// Module file

package Modules.TestModule;

import GameEngine.ScreenCanvas;
import GameEngine.Account;
import GameEngine.GameInfo;
import GameEngine.GameServer;
import GameEngine.Point2D;
import GameEngine.Configurables.ModuleTypes.GraphicsHandlerModule;
import GameEngine.Configurables.ModuleTypes.Module;
import GameEngine.Configurables.ModuleTypes.PlayerHandlerModule;
import GameEngine.Configurables.ModuleTypes.ServerMakingModule;
import GameEngine.Configurables.ModuleTypes.WorldMakingModule;
import GameEngine.EntityTypes.GameEntity;

import Modules.BaseModule.BaseServer;
import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.MektonCore.MektonHexData;
import Modules.MektonCore.MektonMap;

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
		MektonMap map = new MektonMap("Resources/Server Packs/Default/Tilesets/DummyTileset.PNG", "Resources/Server Packs/Default/Tilesets/ZFog.PNG");
		map.setDimensions(18, 9, 1, new MektonHexData());
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
		((MektonMap) GameInfo.getWorld().getRootEntities().get(0)).render(canvas, GameInfo.getCamera(), possessee.getHexPos().z_);
		(GameInfo.getWorld().getEntity(GameInfo.getGUI())).render(canvas, GameInfo.getCamera());
	}

	@Override
	public GameEntity makePlayer(Account account)
	{
		DummyPlayer player = new DummyPlayer(); // Adds a guy to the map

		account.possessee = player.getId();
		GameInfo.setPossessee(account.possessee);
		GameInfo.getWorld().getRootEntities().get(0).addChild(player);
		player.setHexPos(new AxialHexCoord3D(2, 2, 0));
		
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
