// By Iacon1
// Created 04/26/2021
//

package GameEngine;

import java.awt.Graphics2D;

import GameEngine.Configurables.ConfigManager;

public class DummyPlayer extends PhysicalObject
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
	public void render(int pX, int pY, Graphics2D g)
	{

		super.render(pX, pY, g);
	}
}
