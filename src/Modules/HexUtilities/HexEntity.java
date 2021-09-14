// By Iacon1
// Created 09/10/2021
//

package Modules.HexUtilities;

import GameEngine.EntityTypes.SpriteEntity;
import Modules.HexUtilities.HexStructures.HexCoord;

public abstract class HexEntity<T extends HexCoord> extends SpriteEntity // T is coordinate type
{
	T hexPos_;
	
	private void alignCoords()
	{
		this.setPos(hexPos_.toPixel());
	}
	
	public T getHexPos()
	{
		return hexPos_;
	}
	public void setHexPos(T pos)
	{
		hexPos_ = pos;
		alignCoords();
	}
	
	/**
	* Move to a (2D) hex coord at a set speed.
	* <p>
	*
	* @param  target Target position. (in hexes)
	* @param  speed  Speed to move at. (in pixels!)
	*/
	public void moveTargetHex(T target, int speed)
	{
		hexPos_ = target;
		moveTargetSpeed(target.toPixel(), speed);
	}
	/**
	* Move in 2D hexes at a set speed.
	* <p>
	*
	* @param  hX    How far right (or left, if negative) to move.
	* @param  hY    How far down (or up, if negative) to move
	* @param  speed Speed to move at. (in pixels!)
	*/
	public void moveDeltaHex(T delta, int speed)
	{
		moveTargetHex(hexPos_.rAdd(delta), speed);
	}

	public void moveDirectional(HexDirection dir, int distance, int speed)
	{
		T delta = hexPos_.getUnitVector(dir).rMultiply(distance);
		moveDeltaHex(delta, speed);
	}
	
	public boolean isPresentAt(T pos)
	{
		return hexPos_ == pos;
	}
	@Override
	public void onStop()
	{
		alignCoords();
	}
}
