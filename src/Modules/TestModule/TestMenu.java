// By Iacon1
// Created 09/17/2021
// A test

package Modules.TestModule;

import GameEngine.GameInfo;
import GameEngine.ImageSprite;
import GameEngine.EntityTypes.GUITypes.GUISpriteEntity;
import Modules.HexUtilities.HexConfigManager;

public class TestMenu extends GUISpriteEntity
{
	public TestMenu()
	{
		super();
		setSprite(new ImageSprite(GameInfo.getServerPackResource("DummyPlayer.PNG")));
		setSpriteParams(0, 0, HexConfigManager.getHexWidth(), 2 * HexConfigManager.getHexHeight());
		align(AlignmentPoint.northEast, null, AlignmentPoint.northEast);
	}
	
	@Override
	public void onStart()
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void onStop()
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void onAnimStop()
	{
		// TODO Auto-generated method stub

	}
	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onMouseClickGUI(int mX, int mY, int button)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMousePressGUI(int mX, int mY, int button)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleaseGUI(int mX, int mY, int button)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPressGUI(int code)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyReleaseGUI(int code)
	{
		// TODO Auto-generated method stub
		
	}


}
