// By Iacon1
// Created 06/17/2021
//

package Modules.MektonCore;

import com.google.gson.GsonBuilder;

import GameEngine.Configurables.ModuleTypes.GSONModule;
import GameEngine.Configurables.ModuleTypes.Module;
import Utils.GSONConfig.AbsFactory;

public class MektonCore implements Module, GSONModule
{
	private ModuleConfig config;

	@Override
	public ModuleConfig getModuleConfig()
	{
		config = new ModuleConfig();

		return config;
	}

	@Override
	public void initModule()
	{
	}

	@Override
	public void addToBuilder(GsonBuilder builder)
	{
		builder.registerTypeAdapterFactory(new AbsFactory<MektonHex>(MektonHex.class));
	}
}
