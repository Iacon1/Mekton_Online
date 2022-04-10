// By Iacon1
// Created 11/29/2021
//

package Editor;

import javax.swing.UIManager;

import GameEngine.GameInfo;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Managers.GraphicsManager;
import GameEngine.Managers.SoundManager;
import Utils.DebugLogger;
import Utils.Logging;

public final class EditorExec
{

	/** Runs the editor.
	 *
	 *  @param args Not used.
	 */
	public static void main(String[] args)
	{
		Logging.setLogger(new DebugLogger());
		GraphicsManager.init();
		SoundManager.init();
		GameInfo.setClient(false);
		try
		{
			GameInfo.setServerPack("Default");
			ModuleManager.init();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GetPackDialog.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
