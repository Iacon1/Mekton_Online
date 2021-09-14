// By Iacon1
// Created 04/26/2021
// Runs the server

package Server;

import javax.swing.UIManager;

import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.WorldMakingModule;
import Utils.DebugLogger;
import Utils.Logging;

public class ServerExec
{

	public static void main(String[] args)
	{
//		Logging.setLogger(new DebugLogger());
		Logging.setLogger(new ServerLogger());
		
		try
		{
			ConfigManager.init(null);
			ModuleManager.init("Default");
			ModuleManager.getHighestOfType(WorldMakingModule.class).newWorld(); // TODO loading
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ServerStartDialog.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
