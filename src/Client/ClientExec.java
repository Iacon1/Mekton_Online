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

/** Runs the client side.
 *
 */
public class ClientExec
{
	
	/** Runs the client.
	 *
	 *  @param args Not used.
	 */
	public static void main(String[] args)
	{
		Logging.setLogger(new DebugLogger());
		GraphicsManager.init();
		SoundManager.init();
		GameInfo.setClient(true);
		try
		{
			GameInfo.setServerPack("Default");
			ModuleManager.init();
			ConfigManager.init();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GetServerFrame.main(null);
			
			//ClientGameWindow.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
