// By Iacon1
// Created who knows when
// Client game frame - The meat of the game's IO

package GameEngine;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.PacketTypes.GameDataPacket;
import Modules.BaseModule.TabPopulator;
import Modules.MektonCore.Hexmap;
import Utils.MiscUtils;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private String command_;
	private GraphicsCanvas canvas_;
	private boolean queueUpdateRes_;
	
	public void updateUIStuff(GameDataPacket packet) // Updates UI stuff
	{
		float scaleX = getWidth() / ConfigManager.getScreenWidth();
		if (scaleX < 1) scaleX = 1;
		float scaleY = getHeight() / ConfigManager.getScreenHeight();
		if (scaleY < 1) scaleY = 1;
		
		if (ConfigManager.maintainRatio())
		{
			if (getWidth() >= getHeight()) // wider than tall
				scaleY = scaleX; // Heighten to match 
			else // Taller than wide
				scaleX = scaleY; // Lengthen to match
		}

		canvas_.setScale(scaleX, scaleY);
		int newResX = (int) (ConfigManager.getScreenWidth() * scaleX);
		int newResY = (int) (ConfigManager.getScreenHeight() * scaleY);
		// Resize neccessary
		if (getContentPane().getWidth() != newResX || getContentPane().getHeight() != newResY)
		{
			getContentPane().setBounds(0, 0, newResX, newResY);
			queueUpdateRes_ = true;
		}
		else if (queueUpdateRes_)
		{
			getContentPane().setBounds(0, 0, newResX, newResY);
			canvas_.setBounds(getContentPane().getBounds());
			
			queueUpdateRes_ = false;
		}
		
		// TODO bad; Don't mention a module outside of the modules!
		Hexmap<?> map = (Hexmap<?>) packet.ourView.getEntities().get(packet.currentLocationId);
		canvas_.setRenderer(map);
		canvas_.setLayout(null);
		canvas_.repaint();
	}
	
	public void resetCommand() // Resets all input elements
	{
		command_ = null;
	}
	public void setCommand(String command)
	{
		command_ = command;
	}
	
	public String getCommand() // Gets input, resets if not empty, returns null if empty
	{
		if (command_ == null)
			return null;
		else
		{
			String command = command_;
			resetCommand();
			
			return command;
		}
	}
	
	public GameFrame()
	{		
		queueUpdateRes_ = false;
		setTitle(MiscUtils.getProgramName() + " Client: Game Window");
		
		setIconImages(MiscUtils.getIcons(true));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		
		getContentPane().setLayout(null);
		
		getContentPane().setPreferredSize(new Dimension(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight()));
		
		canvas_ = new GraphicsCanvas();
		canvas_.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		getContentPane().add(canvas_);
		
		pack();
	}
}
