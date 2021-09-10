// By Iacon1
// Created 06/17/2021
//

package Modules.MektonCore;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.google.gson.GsonBuilder;

import GameEngine.GameFrame;
import GameEngine.GameEntity;
import GameEngine.GameInfo;
import GameEngine.GameCanvas;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GSONModule;
import GameEngine.Configurables.ModuleTypes.Module;
import GameEngine.Configurables.ModuleTypes.Module.ModuleConfig;
import Net.StateFactory;
import Server.Account;
import Server.GameServer;
import Utils.GSONConfig.AbsFactory;

public class MektonCore implements Module, GSONModule
{
	private ModuleConfig config_;

	@Override
	public ModuleConfig getModuleConfig()
	{
		config_ = new ModuleConfig();

		return config_;
	}

	@Override
	public void initModule()
	{
	}

	@Override
	public void addToBuilder(GsonBuilder builder)
	{
		builder.registerTypeAdapterFactory(new AbsFactory<HexData>(HexData.class));
	}
}
