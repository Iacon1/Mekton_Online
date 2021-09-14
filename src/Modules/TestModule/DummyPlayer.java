// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.HexUtilities.DirectionalHexEntity;
import Modules.HexUtilities.HexConfigManager;

public class DummyPlayer extends DirectionalHexEntity<AxialHexCoord3D>
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
	public void onStart() {}

	@Override
	public void onAnimStop() {}
}
