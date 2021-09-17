// By Iacon1
// Created 09/17/2021
// A test

package Modules.TestModule;

import GameEngine.EntityTypes.GUITypes.GUISpriteEntity;
import Modules.HexUtilities.HexConfigManager;

public class TestMenu extends GUISpriteEntity
{
	public TestMenu(String owner)
	{
		super(owner);
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight());
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


}
