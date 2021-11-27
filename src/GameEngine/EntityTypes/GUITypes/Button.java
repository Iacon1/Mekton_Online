// By Iacon1
// Created 09/10/2021
// Only responds when its sprite is clicked

package GameEngine.EntityTypes.GUITypes;

import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;

public abstract class Button extends GUISpriteEntity implements InputGetter, CommandRunner
{
	/**
	* Called when the button is clicked.
	* 
	* @param  button Was the mouse left-clicked (0), right-clicked (1), or mid-clicked (2)?
	*/
	public abstract void onButtonClick(int button);
	
	@Override
	public String onMouseClick(int mX, int mY, int button)
	{
		if (pos.x <= mX && mX <= pos.x + sprite.getSize().x && pos.y <= mY && pos.y + sprite.getSize().y <= mY)
			return ("click " + " " + button);
		else return null;
	}
	
	@Override
	public boolean runCommand(String... words)
	{
		if (words[0].equals("click"))
		{
			onButtonClick(Integer.valueOf(words[1]));
			return true;
		}
		else return false;
	}
}
