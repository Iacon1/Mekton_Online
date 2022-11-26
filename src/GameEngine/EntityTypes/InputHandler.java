// By Iacon1
// Created 09/10/2021
// That which listens to input

package GameEngine.EntityTypes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import GameEngine.Server.Account;

public interface InputHandler
{
	/**
	* Called when mouse is used, generates a command to run as this entity.
	* 
	* @param  input Mouse event to handle.
	*/
	public void handleMouse(int userID, MouseEvent event);
	/**
	* Called when keyboard is used, generates a command to run as this entity.
	* 
	* @param  input Key event to handle.
	*/
	public void handleKeyboard(int userID, KeyEvent event);
}
