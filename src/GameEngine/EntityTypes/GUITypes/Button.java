// By Iacon1
// Created 09/10/2021
// Only responds when its sprite is clicked

package GameEngine.EntityTypes.GUITypes;

import java.awt.event.MouseEvent;

import GameEngine.Server.Account;

public abstract class Button extends GUISpriteEntity
{
	public abstract void onClick(int userID, int clickType);
	@Override
	public void handleMouse(int userID, MouseEvent event)
	{
		// If a button was clicked in bounds then go.
		if (event.getButton() != MouseEvent.NOBUTTON)
			if (pos.x <= event.getX() && event.getX() <= pos.x + sprite.getSize().x && pos.y <= event.getY() && pos.y + sprite.getSize().y <= event.getY())
				onClick(userID, event.getButton());
	}
}
