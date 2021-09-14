// By Iacon1
// Created 09/14/2021
//

package Modules.MektonCore;

import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexDirection;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.HexCoord;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord;

public abstract class DirectionalHexEntity<T extends HexCoord> extends HexEntity<T>
{
	// Changing the tX changes which direction you face
	/* 0 tW - south 
	 * 1 tW - southEast
	 * 2 tW - northEast
	 * 3 tW - north
	 * 4 tW - northWest
	 * 5 tW - southWest
	 */
	HexDirection dir_;
	
	public void setDirection(HexDirection dir)
	{
		int mult = 0;
		switch (dir)
		{
		case north: mult = 3; break;
		case northWest: mult = 4; break;
		case northEast: mult = 2; break;
	
		case south: mult = 0; break;
		case southWest: mult = 5; break;
		case southEast: mult = 1; break;
		default: return;
		}
		
		this.cTX_ = HexConfigManager.getHexWidth() * mult;
		this.dir_ = dir;
	}
	
	@Override
	public void moveDirectional(HexDirection dir, int distance, int speed)
	{
		super.moveDirectional(dir, distance, speed);
		setDirection(dir);
	}
}
