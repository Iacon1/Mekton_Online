// By Iacon1
// Created 06/12/2021
// Yep, this is how we're doing this

package Modules.TestModule;

import Modules.MektonCore.GameMap;

public class TestHexmap extends GameMap<TestHexData>
{

	public TestHexmap()
	{
		super("Resources/Server Packs/Default/Tilesets/DummyTileset.PNG", "Resources/Server Packs/Default/Tilesets/ZFog.PNG");
	}
}