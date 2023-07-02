// By Iacon1
// Created 04/26/2021
// Runs the server

package Server;

import javax.swing.UIManager;

import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.WorldMakingModule;
import GameEngine.Managers.GraphicsManager;
import Utils.DebugLogger;
import Utils.DataManager;
import Utils.Logging;

public class ServerExec
{

	public static void main(String[] args)
	{
		Logging.setLogger(new DebugLogger());
//		Logging.setLogger(new ServerLogger());
		
		try
		{
			GameInfo.setServerPack("Default"); // TODO changeable
			ModuleManager.loadModules();
			GraphicsManager.init(true);
			DataManager.invalidate();
			WorldMakingModule worldGenerator = ModuleManager.getHighestOfType(WorldMakingModule.class);
			if (worldGenerator != null) worldGenerator.newWorld(); // TODO loading
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ServerStartDialog.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
