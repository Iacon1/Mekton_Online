// By Iacon1
// Created 04/22/2021
// Runs the client

package Client;

import Utils.*;
import GameEngine.DebugLogger;
import GameEngine.GameWorld;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import Client.Frames.ClientGameWindow;
import Client.Frames.GetServerFrame;

import javax.swing.UIManager;

public class ClientExec
{

	public static void main(String[] args)
	{
		Logging.setLogger(new DebugLogger());
		
		try
		{
			ConfigManager.init("default");
			ModuleManager.init("default");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GetServerFrame.main(null);
			
			//ClientGameWindow.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
