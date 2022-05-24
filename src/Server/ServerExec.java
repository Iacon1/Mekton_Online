// By Iacon1
// Created 04/26/2021
// Runs the server

package Server;

import javax.swing.UIManager;

import GameEngine.GameInfo;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.WorldMakingModule;
import Utils.DebugLogger;
import Utils.JSONManager;
import Utils.Logging;

public class ServerExec
{

	public static void main(String[] args)
	{
//		Logging.setLogger(new DebugLogger());
		Logging.setLogger(new ServerLogger());
		
		try
		{
			GameInfo.setServerPack("Default"); // TODO changeable
			ModuleManager.init();
			JSONManager.invalidate();
			ModuleManager.getHighestOfType(WorldMakingModule.class).newWorld(); // TODO loading
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ServerStartDialog.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
