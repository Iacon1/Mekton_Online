// By Iacon1
// Created 04/22/2021
// Runs the client

package Client;

import Utils.*;
import GameEngine.DebugLogger;
import GameEngine.GameInfo;
import GameEngine.GraphicsManager;
import GameEngine.SoundManager;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;

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
			ConfigManager.init("Default");
			ModuleManager.init("Default");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GetServerFrame.main(null);
			
			//ClientGameWindow.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
