// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import GameEngine.GameWorld;
import GameEngine.GraphicsManager;
import GameEngine.GameCanvas;
import GameEngine.PhysicalObject;
import GameEngine.SoundManager;
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
	public void render(int pX, int pY, GameCanvas canvas)
	{
		super.render(pX, pY, canvas);
	}
}
