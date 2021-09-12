// By Iacon1
// Created 06/12/2021
// Yep, this is how we're doing this

package Modules.TestModule;

import Modules.MektonCore.Hexmap;

public class TestHexmap extends Hexmap<TestHexData>
{

	public TestHexmap(TestHexData testHexData)
	{
		super(testHexData, "Resources/Server Packs/Default/Tilesets/DummyTileset.PNG", "Resources/Server Packs/Default/Tilesets/ZFog.PNG");
	}
}