// By Iacon1
// Created 06/12/2021
// Yep, this is how we're doing this

package Modules.TestModule;

import GameEngine.GameInfo;

import Modules.MektonCore.Hexmap;

public class TestHexmap extends Hexmap<TestHexData>
{

	public TestHexmap(GameInfo.GameWorld gameWorld, TestHexData testHexData)
	{
		super(gameWorld, testHexData);
	}
	public TestHexmap()
	{
		super();
	}
}