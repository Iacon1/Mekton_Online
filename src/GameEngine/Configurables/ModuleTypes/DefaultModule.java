// By Iacon1
// Created 06/29/2023
// Default Module

package GameEngine.Configurables.ModuleTypes;

public class DefaultModule implements Module
{
	private ModuleConfig moduleConfig;
	
	public DefaultModule()
	{
		
	}
	public DefaultModule(ModuleConfig defaultConfig)
	{
		initModule(defaultConfig);
	}
	@Override
	public ModuleConfig getConfig()
	{
		return moduleConfig;
	}

	@Override
	public void initModule(ModuleConfig moduleConfig)
	{
		this.moduleConfig = moduleConfig;
	}

}
