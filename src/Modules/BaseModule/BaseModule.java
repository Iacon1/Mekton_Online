// By Iacon1
// Created 06/17/2021
// Basic functionality

package Modules.BaseModule;

import GameEngine.GameCanvas;
import GameEngine.GameEntity;
import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleTypes.Module;
import GameEngine.Configurables.ModuleTypes.StateGiverModule;
import Modules.BaseModule.ClientStates.ClientStateFactory;
import Modules.BaseModule.HandlerStates.HandlerStateFactory;
import Net.StateFactory;
import Server.Account;
import Server.GameServer;

public class BaseModule implements Module, StateGiverModule
{
	private ModuleConfig config_;
	
	@Override
	public ModuleConfig getModuleConfig()
	{
		config_ = new ModuleConfig();

		return config_;
	}

	@Override
	public void initModule() {}
	
	@Override
	public StateFactory clientFactory()
	{
		return new ClientStateFactory();
	}

	@Override
	public StateFactory handlerFactory()
	{
		return new HandlerStateFactory();
	}
}
