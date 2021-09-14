// By Iacon1
// Created 09/10/2021
//

package Modules.HexUtilities;

import GameEngine.EntityTypes.KineticSpriteEntity;
import Modules.HexUtilities.HexStructures.HexCoord;

public abstract class HexEntity<T extends HexCoord> extends KineticSpriteEntity // T is coordinate type
{
	T pos_;
	
	private void alignCoords()
	{
		this.setPos(pos_.toPixel().x_, pos_.toPixel().y_);
	}
	
	public T getPos()
	{
		return pos_;
	}
	public void setPos(T pos)
	{
		pos_ = pos;
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
		pos_ = target;
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
		moveTargetHex(pos_.rAdd(delta), speed);
	}

	public void moveDirectional(HexDirection dir, int distance, int speed)
	{
		T delta = pos_.getUnitVector(dir).rMultiply(distance);
		moveDeltaHex(delta, speed);
	}
	
	public boolean isPresentAt(T pos)
	{
		return pos_ == pos;
	}
	@Override
	public void onStop()
	{
		alignCoords();
	}
}
