// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.TransSerializable;
import Modules.MektonCore.HexEntity;

public class DummyPlayer extends HexEntity implements TransSerializable
{	
	public DummyPlayer()
	{
		super();
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, ConfigManager.getHexWidth(), ConfigManager.getHexHeight());
	}
	
	@Override
	public String getName()
	{
		return "Dummy Player";
	}

	@Override
	public void preSerialize() {}

	@Override
	public void postDeserialize()
	{
	}
}
