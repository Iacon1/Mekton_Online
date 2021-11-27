// By Iacon1
// Created 11/22/2021
// A box that text can be entered into
// When left-clicked it becomes selected, allowing text entry.
// If selected then it becomes unselected whenever something else is left-clicked.

package GameEngine.EntityTypes.GUITypes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import GameEngine.ImageSprite;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;

public abstract class Textbar extends GUISpriteEntity implements InputGetter, CommandRunner
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
		super.setSprite(new ImageSprite(image));
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

	@Override
	public String onMouseClick(int mX, int mY, int button)
	{
		if (button != 0) return null;
		
		if (pos.x <= mX && mX <= pos.x + sprite.getSize().x && pos.y <= mY && mY <= pos.y + sprite.getSize().y)
			return "select";
		else if (selected) return "unselect";
		else return null;
	}
	@Override
	public String onKeyPress(int code)
	{
		if (!selected) return null;
		return "key " + code;
	}

	@Override
	public boolean runCommand(String... words)
	{
		if (words[0].equals("select"))
		{
			selected = true;
			return true;
		}
		else if (words[0].equals("unselect"))
		{
			selected = false;
			return true;
		}
		else if (words[0].equals("key"))
		{
			int code = Integer.valueOf(words[1]);
			if (code == KeyEvent.VK_BACK_SPACE && buffer != null)
				buffer = buffer.substring(0, buffer.length() - 1);
			else if (code == KeyEvent.VK_ENTER) onSubmit();
			else buffer = buffer + String.valueOf((char) code);
			return true;
		}
		else return false;
	}
	
	@Override
	public void render(ScreenCanvas canvas, Point2D camera) 
	{
		super.render(canvas, camera);
		if (buffer != null) canvas.drawText(buffer, font, color, pos.add(spriteOff), heightPixels);
	}

	public abstract void onSubmit();
	
	
}
