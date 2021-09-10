// By Iacon1
// Created 04/22/2021
// Runs the client

package Client;

import GameEngine.GameInfo;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Managers.GraphicsManager;
import GameEngine.Managers.SoundManager;
import Utils.DebugLogger;
import Utils.Logging;

import javax.swing.UIManager;

public class ClientExec
{
	
	public static void main(String[] args)
	{
		Logging.setLogger(new DebugLogger());
		GraphicsManager.init();
		SoundManager.init();
		GameInfo.setClient(true);
		try
		{
			ModuleManager.init("Default");
			ConfigManager.init("Default");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GetServerFrame.main(null);
			
			//ClientGameWindow.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
