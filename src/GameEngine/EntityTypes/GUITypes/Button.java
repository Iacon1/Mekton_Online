// By Iacon1
// Created 09/10/2021
// Only responds when its sprite is clicked

package GameEngine.EntityTypes.GUITypes;

import GameEngine.EntityTypes.InputGetter;

public abstract class Button extends GUISpriteEntity implements InputGetter
{
	/**
	* Called when the button is clicked.
	* <p>
	* 
	* @param  button Was the mouse left-clicked (0), right-clicked (1), or mid-clicked (2)?
	*/
	public abstract void onButtonClick(int button);
	@Override
	public void onMouseClick(int mX, int mY, int button)
	{
		if (x_ <= mX && mX <= x_ + width_ && y_ <= mY && y_ + height_ <= mY) onButtonClick(button);
	}
}
