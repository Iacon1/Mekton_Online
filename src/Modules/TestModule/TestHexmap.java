// By Iacon1
// Created 06/12/2021
// Yep, this is how we're doing this

package Modules.TestModule;

import Modules.MektonCore.Hexmap;

public class TestHexmap extends Hexmap<TestHexData>
{

	public TestHexmap(String tileset, TestHexData testHexData)
	{
		super(tileset, testHexData);
	}
	public TestHexmap()
	{
		super();
	}
}