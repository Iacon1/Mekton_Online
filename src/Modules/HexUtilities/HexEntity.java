// By Iacon1
// Created 09/10/2021
//

package Modules.HexUtilities;

import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.KineticSpriteEntity;
import Modules.HexUtilities.HexStructures.AxialCoord;
import Modules.HexUtilities.HexStructures.AxialCoord3D;
import Modules.MektonCore.GameMap;

public abstract class HexEntity extends KineticSpriteEntity // T is coordinate type
{
	AxialCoord3D pos_;
	
	private void alignCoords()
	{
		
	}
	
	public AxialCoord3D getPos()
	{
		return pos_;
	}
	public void setPos(AxialCoord3D pos)
	{
		pos_ = pos;
	}
	
	/**
	* Move to a (2D) hex coord at a set speed.
	* <p>
	*
	* @param  target Target position. (in hexes)
	* @param  speed  Speed to move at. (in pixels!)
	*/
	public void moveTargetHex(AxialCoord target, int speed)
	{
		
		moveTargetSpeed(AxialCoord.hex2Pixel(target), speed);
	}
	/**
	* Move in 2D hexes at a set speed.
	* <p>
	*
	* @param  hX    How far right (or left, if negative) to move.
	* @param  hY    How far down (or up, if negative) to move
	* @param  speed Speed to move at. (in pixels!)
	*/
	public void moveDeltaHex(AxialCoord delta, int speed)
	{
		moveTargetHex(pos_.rAdd(delta), speed);
	}

	public void moveDirectional(HexDirection dir, int distance, int speed)
	{
		AxialCoord3D delta = new AxialCoord3D(0, 0, 0);
		switch (dir)
		{
		case down: delta.z_ = -distance; break;
		case up: delta.z_ = distance; break;
		default:
			delta.q_ = AxialCoord.getUnitVector(dir).multiply(distance).q_;
			delta.r_ = AxialCoord.getUnitVector(dir).multiply(distance).r_;
		}
//		alignCoords();
		moveDeltaHex(delta, speed);
		
	}
	
	public boolean isPresentAt(int hX, int hY, int hZ)
	{
		return false; //return hX == hX_ && hY == hY_ && hZ == hZ_;
	}
	@Override
	public void onStop()
	{
		alignCoords();
	}
}
