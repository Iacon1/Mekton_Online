// By Iacon1
// Created 11/22/2021
// A box that text can be entered into

package GameEngine.EntityTypes.GUITypes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import GameEngine.ImageSprite;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.InputGetter;

public abstract class Textbar extends GUISpriteEntity implements InputGetter
{
	private String font;
	private Color color;
	private int heightPixels;
	private boolean selfClearing;
	
	private String buffer;
	private boolean selected;
	
	
	public Textbar()
	{
		super();
		this.font = null;
		this.color = null;
		this.heightPixels = 0;
		this.selfClearing = false;
		
		buffer = new String();
		selected = false;
	}
	public Textbar(String owner, String image, String font, Color color, boolean selfClearing, int heightPixels)
	{
		super(owner);
		super.setSprite(new ImageSprite(image));
		this.font = font;
		this.color = color;
		this.heightPixels = heightPixels;
		this.selfClearing = selfClearing;
		
		buffer = new String();
		selected = false;
	}
	
	public void clear()
	{
		buffer = null;
	}
	public abstract void onEnter(String text);
	@Override
	public void onKeyPress(int code)
	{
		if (!selected) return;
		
		switch (code)
		{
		case KeyEvent.VK_BACK_SPACE:
			if (buffer.length() > 0) buffer = buffer.substring(0, buffer.length() - 1);
			break;
		case KeyEvent.VK_ENTER:
			onEnter(buffer);
			selected = false;
			if (selfClearing) clear();
			break;
		default:
			buffer += KeyEvent.getKeyText(code);
			break;
		}
	}
	
	@Override
	public void onMouseClick(int mX, int mY, int button)
	{
		if (button != 0) return;
		
		if (pos.x <= mX && mX <= pos.x + sprite.getSize().x && pos.y <= mY && pos.y + sprite.getSize().y <= mY)
			selected = true;
		else selected = false;
	}
	@Override
	public void render(ScreenCanvas canvas, Point2D camera) 
	{
		super.render(canvas, camera);
		if (buffer != null) canvas.drawText(buffer, font, color, pos.add(spriteOff), heightPixels);
	}
}
