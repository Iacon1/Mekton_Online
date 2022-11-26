// By Iacon1
// Created 11/22/2021
// A box that text can be entered into
// When left-clicked it becomes selected, allowing text entry.
// If selected then it becomes unselected whenever something else is left-clicked.

package GameEngine.EntityTypes.GUITypes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import GameEngine.IntPoint2D;
import GameEngine.Graphics.Camera;
import GameEngine.Graphics.ScreenCanvas;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.Graphics.SingleSprite;
import GameEngine.Server.Account;
import Utils.MiscUtils;

public abstract class Textbar extends Button
{
	private String font;
	private Color color;
	private int heightPixels;

	protected String buffer;
	private boolean selected;
	
	
	public Textbar()
	{
		super();
		this.font = null;
		this.color = null;
		this.heightPixels = 0;
		
		buffer = new String();
		selected = false;
	}
	public Textbar(String image, String font, Color color, boolean selfClearing, int heightPixels)
	{
		super();
		super.setSprite(new SingleSprite(image));
		this.font = font;
		this.color = color;
		this.heightPixels = heightPixels;
		
		buffer = new String();
		selected = false;
	}

	public void clear()
	{
		buffer = null;
	}
	public boolean isSelected()
	{
		return selected;
	}
	
	@Override
	public void onClick(int userId, int clickType)
	{
	}
	@Override
	public void handleKeyboard(int userId, KeyEvent event)
	{
		if (!selected) return;
		else
		{
			if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE && buffer != null)
				buffer = buffer.substring(0, buffer.length() - 1);
			else if (event.getKeyCode() == KeyEvent.VK_ENTER) onSubmit();
			else
			{
				if (buffer != null) buffer = buffer + (char) event.getKeyCode();
				else buffer = String.valueOf(event.getKeyCode());
			}
		}
	}
	
	@Override
	public void render(ScreenCanvas canvas, Camera camera) 
	{
		super.render(canvas, camera);
		if (buffer != null) canvas.addText(buffer, font, color, getPos().add(spriteOff), heightPixels);
	}

	public abstract void onSubmit();
	
	
}
