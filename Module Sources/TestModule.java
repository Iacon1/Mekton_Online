// By Iacon1
// Created 05/31/2021
// Test module

import GameEngine.GameWorld;
import GameEngine.Configurables.Module;
import Utils.Logging;

public class TestModule implements Module
{
	private ModuleConfig config_;
	
	public TestModule()
	{
		config_ = new ModuleConfig();
		
		config_.doesImplement_.put("setup", false);
		config_.doesImplement_.put("loadWorld", false);
		
		Logging.logNotice("Test Module Loaded.");
	}
	
	@Override
	public ModuleConfig getConfig()
	{
		return config_;
	}

	@Override
	public GameWorld setup() {return null;}

	@Override
	public GameWorld loadWorld(String server) {return null;}

}
