// By Iacon1
// Created 09/11/2021
// Hex utilities

package Modules.HexUtilities;

import GameEngine.Configurables.ModuleTypes.Module;

public class HexUtilities implements Module
{

	@Override
	public ModuleConfig getModuleConfig()
	{
		ModuleConfig config = new ModuleConfig();
		return null;
	}

	@Override
	public void initModule()
	{
		HexConfigManager.init("default");
	}

}
