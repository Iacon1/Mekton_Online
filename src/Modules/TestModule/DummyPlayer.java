// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.InputGetter;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexConfigManager;

public class DummyPlayer extends HexEntity implements InputGetter
{	
	public DummyPlayer()
	{
		super();
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight());
	}
	
	@Override
	public String getName()
	{
		return "Dummy Player";
	}

	@Override
	public void onMouseClick(int mX, int mY, int button) {}
	@Override
	public void onMousePress(int mX, int mY, int button) {}

	@Override
	public void onMouseRelease(int mX, int mY, int button) {}

	@Override
	public void onKeyPress(int code) {}

	@Override
	public void onKeyRelease(int code)
	{
		// TODO Auto-generated method stub
		
	}
}
