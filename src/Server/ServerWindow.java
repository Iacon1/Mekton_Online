package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;

import GameEngine.GameServer;

public class ServerWindow
{
	private static GameServer server_;
	private JFrame frame;

	public static void main(GameServer server)
	{
		server_ = server;
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
		frame = new ServerMainFrame(server_);
		frame.setVisible(true);
	}

}
