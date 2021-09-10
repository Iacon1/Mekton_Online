// By Iacon1
// Created 09/01/2021
// Entities with a sprite and event listeners
// Note that these would only run on the client, so it'd be best if they just give commands
// TODO GUI layer that runs on client end?

package GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class SolidEntity extends GameEntity
{
	public abstract void onClick(boolean inBounds, boolean left); // When the mouse is clicked, be it in-bounds or out-of-bounds
	public abstract void onKeyPress(int key); // When a key is pressed
	public abstract void onKeyRelease(int key); // When a key is pressed
	
	private class GameKeyListener implements KeyListener
	{
		@Override
	    public void keyTyped(KeyEvent e) {} // ?

	    @Override
	    public void keyPressed(KeyEvent e)
	    {
	    	onKeyPress(e.getKeyCode());
	    }

	    @Override
	    public void keyReleased(KeyEvent e)
	    {
	        onKeyRelease(e.getKeyCode());
	    }
	}
	private GameKeyListener gameKeyListener_ = new GameKeyListener();
	
	public SolidEntity() {super();}
	public SolidEntity(GameWorld world)
	{
		super(world);
	}
	
	@Override
	public abstract String getName();
	
	public final void registerKeyEvent() // Activates key listeners
	{
		ClientInfo.getFrame().addKeyListener(gameKeyListener_);
	}
}
