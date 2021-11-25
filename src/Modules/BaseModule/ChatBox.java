// By Iacon1
// Created 11/22/2021
// Chat bar. TODO Add log display features

package Modules.BaseModule;

import java.awt.Color;

import GameEngine.GameInfo;
import GameEngine.EntityTypes.GUITypes.Textbar;

public class ChatBox extends Textbar
{
	public ChatBox(int ownerID, String font, Color color, int heightPixels)
	{
		super(ownerID, "ChatBox", font, color, true, heightPixels);
		sprite.setBasicParams(0, 0, 32, 32);
	}
	
	@Override
	public void onMousePress(int mX, int mY, int button) {}

	@Override
	public void onMouseRelease(int mX, int mY, int button) {}

	@Override
	public void onKeyRelease(int code) {}

	@Override
	public void onEnter(String text)
	{
		GameInfo.setCommand(text);
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

}
