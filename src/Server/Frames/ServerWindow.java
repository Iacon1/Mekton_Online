package Server.Frames;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Server.Server;

public class ServerWindow
{
	private Server server_;
	private JFrame frame;


	public static void main(Server server)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ServerWindow window = new ServerWindow();
					window.server_ = server;
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
