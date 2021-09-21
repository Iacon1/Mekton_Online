// By Iacon1
// Created 09/10/2021
//

package Modules.HexUtilities;

import java.util.ArrayList;
import java.util.LinkedList;

import GameEngine.Point2D;
import GameEngine.EntityTypes.SpriteEntity;
import Modules.HexUtilities.HexStructures.HexCoord;

public abstract class HexEntity<T extends HexCoord> extends SpriteEntity // T is coordinate type
{
	protected T hexPos_;
	private T targetHexPos_;
	private HexDirection facing_;
	
	private LinkedList<T> path;
	private int baseSpeed;
	
	public HexEntity()
	{
		super();
		path = new LinkedList<T>();
	}
	public HexEntity(String owner)
	{
		super(owner);
	}

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
		targetHexPos_ = target;
		
		setDirection(this.hexPos_.getDirectionTo(targetHexPos_));
		
		float speedFactor = 1f;
		switch (facing_)
		{
		case northWest: case northEast: case southWest: case southEast:
			speedFactor = 5f / 3f; break; // No math basis, just a rough adjustment to fix a mysterious speed difference between diagonal and straight
		default: break;
		}
		baseSpeed = speed;
		moveTargetSpeed(target.toPixel(), (int) (speed * speedFactor));
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
		setDirection(dir);
		
		moveDeltaHex(delta, speed);
	}
	
	public boolean isPresentAt(T pos)
	{
		return hexPos_ == pos;
	}

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
		
		texturePos_.x = textureSize_.x * mult;
		facing_ = dir;
	}
	
	public void movePath(LinkedList<T> path, int speed)
	{
		this.path = path;
		moveTargetHex(path.getFirst(), speed);
	}
			
	@Override
	public void onStop()
	{
		hexPos_ = targetHexPos_;
		if (this.path != null)
		{
			this.path.remove();
			if (this.path.isEmpty()) this.path = null;
			else moveTargetHex(path.getFirst(), baseSpeed);
		}
	}
}
