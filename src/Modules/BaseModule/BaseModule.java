// By Iacon1
// Created 06/17/2021
// Basic functionality

package Modules.BaseModule;

import GameEngine.Configurables.ModuleTypes.Module;
import GameEngine.Configurables.ModuleTypes.StateGiverModule;
import GameEngine.Net.StateFactory;
import Modules.BaseModule.ClientStates.ClientStateFactory;
import Modules.BaseModule.HandlerStates.HandlerStateFactory;

public class BaseModule implements Module, StateGiverModule
{
	private ModuleConfig config;
	
	@Override
	public ModuleConfig getModuleConfig()
	{
		config = new ModuleConfig();

		return config;
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
