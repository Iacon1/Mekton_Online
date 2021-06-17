// By Iacon1
// Created 04/25/2021
// Main game window

package Client.Frames;

import java.awt.EventQueue;
import javax.swing.JFrame;

import Utils.Logging;
import GameEngine.GraphicsManager;
import Client.GameClientThread;

public class ClientGameWindow
{	
	private JFrame frame;
	
	private static GameClientThread thread_;
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public static void main(GameClientThread thread)
	{
		thread_ = thread;
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					new ClientGameWindow();
				}
				catch (Exception e) {Logging.logException(e);}
			}
		});
	}

	public ClientGameWindow()
	{
		frame = new ClientGameFrame();
		thread_.setContainer(frame);
		frame.setVisible(true);
	}
}
