// By Iacon1
// Created who knows when
// Client game frame - The meat of the game's IO

package GameEngine;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.InputGetter;
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
	public void updateUIStuff() // Updates UI stuff
	{	
		checkScale();
		canvas_.repaint();
	}
	
	private class EntityInputListener implements KeyListener, MouseListener
	{
		private int worldHash_; // TODO stupid way to keep up-to-date
		
		private boolean hasUpdated()
		{
			if (GameInfo.getWorld().hashCode() == worldHash_) return false;
			else
			{
				worldHash_ = GameInfo.getWorld().hashCode();
				return true;
			}
		}
		
		private ArrayList<InputGetter> inputGetters_;
		private void findInputGetters() // Finds all input getters
		{
			if (!hasUpdated()) return; // Don't want to call this often
			else
			{
				ArrayList<GameEntity> entities = GameInfo.getWorld().getEntities();
				inputGetters_.clear();
				for (int i = 0; i < entities.size(); ++i)
				{
					if (InputGetter.class.isAssignableFrom(entities.get(i).getClass())) inputGetters_.add((InputGetter) entities.get(i));
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			findInputGetters();
			
			int mX = canvas_.descaleX(e.getX());
			int mY = canvas_.descaleY(e.getY());
			
			switch (e.getButton())
			{
			case MouseEvent.NOBUTTON: return;
			case MouseEvent.BUTTON1:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMouseClick(mX, mY, 0);
				break;
			case MouseEvent.BUTTON2:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMouseClick(mX, mY, 1);
				break;
			case MouseEvent.BUTTON3:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMouseClick(mX, mY, 2);
				break;
			}
		}
		@Override
		public void mousePressed(MouseEvent e)
		{
			findInputGetters();
			
			int mX = canvas_.descaleX(e.getX());
			int mY = canvas_.descaleY(e.getY());
			
			switch (e.getButton())
			{
			case MouseEvent.NOBUTTON: return;
			case MouseEvent.BUTTON1:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMousePress(mX, mY, 0);
				break;
			case MouseEvent.BUTTON2:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMousePress(mX, mY, 1);
				break;
			case MouseEvent.BUTTON3:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMousePress(mX, mY, 2);
				break;
			}
		}
		@Override
		public void mouseReleased(MouseEvent e)
		{
			findInputGetters();
			
			int mX = canvas_.descaleX(e.getX());
			int mY = canvas_.descaleY(e.getY());
			
			switch (e.getButton())
			{
			case MouseEvent.NOBUTTON: return;
			case MouseEvent.BUTTON1:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMouseRelease(mX, mY, 0);
				break;
			case MouseEvent.BUTTON2:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMouseRelease(mX, mY, 1);
				break;
			case MouseEvent.BUTTON3:
				for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onMouseRelease(mX, mY, 2);
				break;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) // I don't think this can actually trigger bc of how the canvas is set up
		{
			return;
		}
		@Override
		public void mouseExited(MouseEvent e) // See mouseEntered
		{
			return;
		}

		@Override
		public void keyTyped(KeyEvent e) // Not really useful to us I think; TODO Maybe?
		{
			return;
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			findInputGetters();
			
			for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onKeyPress(e.getKeyCode());
		}
		@Override
		public void keyReleased(KeyEvent e)
		{
			findInputGetters();

			for (int i = 0; i < inputGetters_.size(); ++i) inputGetters_.get(i).onKeyRelease(e.getKeyCode());
		}
		
		public EntityInputListener()
		{
			worldHash_ = 0;
			inputGetters_ = new ArrayList<InputGetter>();
		}
	}
	public void registerInputListener()
	{
		EntityInputListener listener = new EntityInputListener();
		
		canvas_.addMouseListener(listener);
		canvas_.addKeyListener(listener);
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
		
		registerInputListener();
	}
}
