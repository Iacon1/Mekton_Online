// By Iacon1
// Created 11/22/2021
// Chat bar. TODO Add log display features

package Modules.BaseModule;

import java.awt.Color;

import GameEngine.GameInfo;
import GameEngine.EntityTypes.GUITypes.Textbar;

public class ChatBox extends Textbar
{
	public ChatBox(String font, Color color, int heightPixels)
	{
		super("ChatBox", font, color, true, heightPixels);
		sprite.setBasicParams(0, 0, 32, 32);
	}
	
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

	@Override
	public void onMousePressGUI(int mX, int mY, int button) {}
	@Override
	public void onMouseReleaseGUI(int mX, int mY, int button) {}
	@Override
	public void onKeyReleaseGUI(int code) {}

}
