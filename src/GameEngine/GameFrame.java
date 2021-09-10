// By Iacon1
// Created who knows when
// Client game frame - The meat of the game's IO

package GameEngine;

import java.awt.Dimension;

import javax.swing.JFrame;

import GameEngine.Configurables.ConfigManager;
import GameEngine.PacketTypes.GameDataPacket;
import Utils.MiscUtils;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private GameCanvas canvas_;
	private boolean queueUpdateRes_;
	
	public void checkScale()
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
	}
	public void updateUIStuff(GameDataPacket packet) // Updates UI stuff
	{	
		checkScale();
		canvas_.repaint();
	}
	
	public GameFrame()
	{		
		GameInfo.setFrame(this);
		queueUpdateRes_ = false;
		setTitle(MiscUtils.getProgramName() + " Client: Game Window");
		
		setIconImages(MiscUtils.getIcons(true));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		
		getContentPane().setLayout(null);
		
		getContentPane().setPreferredSize(new Dimension(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight()));
		
		canvas_ = new GameCanvas();
		canvas_.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		getContentPane().add(canvas_);
		
		pack();
	}
}
