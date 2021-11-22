// By Iacon1
// Created 11/22/2021
// A box that text can be entered into

package GameEngine.EntityTypes.GUITypes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import GameEngine.Point2D;
import GameEngine.ScreenCanvas;

public abstract class Textbox extends Button
{
	private String font;
	private String buffer;
	private Color color;
	private int heightPixels;
	
	public Textbox(String font, Color color, int heightPixels)
	{
		this.font = font;
		this.color = color;
		this.heightPixels = heightPixels;
	}
	
	public abstract void onEnter(String text);
	@Override
	public void onKeyPress(int code)
	{
		switch (code)
		{
		case KeyEvent.VK_BACK_SPACE:
			if (buffer.length() > 0) buffer = buffer.substring(0, buffer.length() - 1);
			break;
		case KeyEvent.VK_ENTER:
			onEnter(buffer);
			break;
		default:
			buffer += KeyEvent.getKeyText(code);
			break;
		}
	}
	
	@Override
	public void render(ScreenCanvas canvas, Point2D camera) 
	{
		canvas.drawText(buffer, font, color, pos.add(spriteOff), heightPixels);
	}
}
