// By Iacon1
// Created 06/12/2021
// Yep, this is how we're doing this

package Modules.TestModule;

import GameEngine.GameWorld;
import Modules.MektonCore.Hexmap;

public class TestHexmap extends Hexmap<TestHexData>
{

	public TestHexmap(GameWorld gameWorld, TestHexData testHexData)
	{
		super(gameWorld, testHexData);
	}
	public TestHexmap()
	{
		super();
	}
}