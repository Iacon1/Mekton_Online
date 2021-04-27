// By Iacon1
// Created 04/22/2021
// Runs the client

package Client;

import Utils.*;
import GameEngine.DebugLogger;
import Client.Frames.GetServerFrame;

import javax.swing.UIManager;

public class ClientExec
{

	public static void main(String[] args)
	{
		Logging.setLogger(new DebugLogger());
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GetServerFrame.main(null);
		}
		catch (Exception e) {Logging.logException(e);}
	}

}
