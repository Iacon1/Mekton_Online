// By Iacon1
// Created 09/01/2021
// Entities with a sprite and event listeners
// Note that these would only run on the client, so it'd be best if they just give commands
// TODO GUI layer that runs on client end?

package GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public abstract class SolidEntity extends GameEntity
{
	public abstract void onClick(boolean inBounds, boolean left); // When the mouse is clicked, be it in-bounds or out-of-bounds
	public abstract void onKeyPress(int key); // When a key is pressed
	public abstract void onKeyRelease(int key); // When a key is pressed
	
	private static transient List<SolidEntity> entities_ = new ArrayList<SolidEntity>();
	private static class GameKeyListener implements KeyListener
	{
		@Override
	    public void keyTyped(KeyEvent e) {} // ?

	    @Override
	    public void keyPressed(KeyEvent e)
	    {
	    	for (int i = 0; i < entities_.size(); ++i) entities_.get(i).onKeyPress(e.getKeyCode());
	    }

	    @Override
	    public void keyReleased(KeyEvent e)
	    {
	    	for (int i = 0; i < entities_.size(); ++i) entities_.get(i).onKeyRelease(e.getKeyCode());
	    }
	}
	
	private static GameKeyListener gameKeyListener_;
	private static boolean registered_;

	public SolidEntity()
	{
		super();
	}
	
	public static final void registerListeners() // Activates key listeners
	{
		if (!registered_)
		{
			if (GameWorld.isClient()) GameWorld.getFrame().addKeyListener(gameKeyListener_);	
			
			registered_ = true;
		}
	}
	
	@Override
	public void cleanup()
	{
		entities_.remove(this);
	}
}
