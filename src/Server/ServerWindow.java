package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;

import GameEngine.Server.GameServer;

public class ServerWindow
{
	private static GameServer server;
	private JFrame frame;

	public static void main(GameServer server)
	{
		ServerWindow.server = server;
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ServerWindow window = new ServerWindow();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerWindow()
	{
		frame = new ServerMainFrame(server);
		frame.setVisible(true);
	}

}
