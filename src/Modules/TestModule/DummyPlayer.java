// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import GameEngine.GameWorld;
import GameEngine.GraphicsManager;
import GameEngine.KeyBindingManager;
import GameEngine.GameCanvas;
import GameEngine.PhysicalObject;
import GameEngine.SoundManager;
import GameEngine.TransSerializable;
import GameEngine.Configurables.ConfigManager;

public class DummyPlayer extends PhysicalObject implements TransSerializable
{	
	public DummyPlayer(GameWorld world)
	{
		super(world);
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, ConfigManager.getHexWidth(), ConfigManager.getHexHeight());
	}
	
	@Override
	public String getName()
	{
		return "Dummy Player";
	}
	@Override
	public void render(int pX, int pY, GameCanvas canvas)
	{
		super.render(pX, pY, canvas);
	}

	@Override
	public void onKeyPress(int key)
	{
		if (key == KeyBindingManager.getBinding("MOVE_NORTH")) GameWorld.setCommand("move n");
	}
	
	@Override
	public void onClick(boolean inBounds, boolean left)
	{
		
	}

	@Override
	public void onKeyRelease(int key)
	{
		
	}

	@Override
	public void preSerialize() {}

	@Override
	public void postDeserialize()
	{
		if (GameWorld.isClient()) registerKeyEvent();
	}
}
