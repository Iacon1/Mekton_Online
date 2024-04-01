// By Iacon1
// Created 11/22/2021
// Chat bar. TODO Add log display features

package GameEngine.EntityTypes.GUITypes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import GameEngine.GameInfo;

public class ChatBox extends Textbar
{
	public ChatBox(String font, Color color, int heightPixels)
	{
		super("ChatBox", font, color, true, heightPixels);
		sprite.setBasicParams(0, 0, 64, 64);
		setBounds(0, 0, 0, 0);
	}
	
	@Override
	public void onSubmit()
	{
		GameInfo.getServer().runCommand(this.getOwner(), buffer);
		clear();
	}

	@Override
	public void onStart() {}
	@Override
	public void onStop() {}
	@Override
	public void onAnimStop() {}

	@Override
	public String getName()
	{
		return "Chat Box";
	}

	@Override public void handleMouse(int userID, MouseEvent event) {}

	@Override public void handleKeyboard(int userID, KeyEvent event) {} // TODO

	@Override
	public String runCommand(String... words) {return null;}
}
