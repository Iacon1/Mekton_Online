package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class ServerWindow
{
	private static Server server_;
	private JFrame frame;


	public static void main(Server server)
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
