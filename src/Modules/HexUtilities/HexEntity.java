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
	protected T hexPos;
	private T targetHexPos;
	private HexDirection facing;
	
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
		this.setPos(hexPos.toPixel());
	}
	
	public T getHexPos()
	{
		return hexPos;
	}
	public void setHexPos(T pos)
	{
		hexPos = pos;
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
		targetHexPos = target;
		
		setDirection(this.hexPos.getDirectionTo(targetHexPos));
		
		float speedFactor = 1f;
		switch (facing)
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
		moveTargetHex(hexPos.rAdd(delta), speed);
	}

	public void moveDirectional(HexDirection dir, int distance, int speed)
	{
		T delta = hexPos.getUnitVector(dir).rMultiply(distance);
		setDirection(dir);
		
		moveDeltaHex(delta, speed);
	}
	
	public boolean isPresentAt(T pos)
	{
		return hexPos == pos;
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
		
		texturePos.x = textureSize.x * mult;
		facing = dir;
	}
	
	public void movePath(LinkedList<T> path, int speed)
	{
		this.path = path;
		moveTargetHex(path.getFirst(), speed);
	}
			
	@Override
	public void onStop()
	{
		hexPos = targetHexPos;
		if (this.path != null)
		{
			this.path.remove();
			if (this.path.isEmpty()) this.path = null;
			else moveTargetHex(path.getFirst(), baseSpeed);
		}
	}
}
