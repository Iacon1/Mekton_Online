// By Iacon1
// Created 09/10/2021
// Only responds when its sprite is clicked

package GameEngine.EntityTypes.GUITypes;

public abstract class Button extends GUISpriteEntity
{
	/**
	* Called when the button is clicked.
	* 
	* @param  button Was the mouse left-clicked (0), right-clicked (1), or mid-clicked (2)?
	*/
	public abstract void onButtonClick(int button);
	
	@Override
	public void onMouseClickGUI(int mX, int mY, int button)
	{
		if (pos.x <= mX && mX <= pos.x + sprite.getSize().x && pos.y <= mY && pos.y + sprite.getSize().y <= mY) onButtonClick(button);
	}
}
