// By Iacon1
// Created 04/25/2021
// Main game window

package Client.Frames;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JFrame;

import Utils.Logging;
import Utils.MiscUtils;

import javax.swing.JTabbedPane;

import GameEngine.ConfigManager;
import GameEngine.DebugLogger;
import GameEngine.GraphicsCanvas;
import GameEngine.GraphicsManager;
import GameEngine.Hexmap;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.SpringLayout;
import java.awt.Canvas;

public class ClientGameWindow
{	
	private JFrame frame;
	

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Logging.setLogger(new DebugLogger());
					GraphicsManager.init();
					ClientGameWindow window = new ClientGameWindow();
				}
				catch (Exception e) {Logging.logException(e);}
			}
		});
	}

	public ClientGameWindow()
	{
		frame = new ClientGameFrame();
		frame.setVisible(true);
	}
}
