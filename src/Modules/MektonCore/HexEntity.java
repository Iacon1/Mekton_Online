// By Iacon1
// Created 09/10/2021
//

package Modules.MektonCore;

import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.KineticSpriteEntity;

public abstract class HexEntity extends KineticSpriteEntity
{
	private int hX_; // x in hexes
	private int hY_; // y in hexes
	private int hZ_; // Z (of top-front-left corner) on map in hexes;
	
	private void alignCoords()
	{
		x_ = Hexmap.GX2SX(hX_, ConfigManager.getHexWidth());
		y_ = Hexmap.GY2SY(hY_, ConfigManager.getHexHeight(), hX_);
	}
	public enum Direction
	{
		down, // -z
		up, // +z
		
		north, // -y
		northWest, // -x, -y
		northEast, // +x, -y
		
		south, // +y
		southWest, // -x, +y
		southEast; // +x, +y
	}
	
	public int getHX() // In hexes
	{
		return hX_ / ConfigManager.getHexWidth();
	}
	public int getHY()
	{
		return hY_ / ConfigManager.getScreenHeight();
	}
	public int getHZ()
	{
		return hZ_;
	}
	public void setPos(Integer x, Integer y, Integer z) // If any input is null then don't change
	{
		if (x != null) hX_ = x;
		if (y != null) hY_ = y;
		if (z != null) hZ_ = z;
		alignCoords();
	}
	/**
	* Move to a (2D) hex coord at a set speed.
	* <p>
	*
	* @param  hX    Hex x coord to move to.
	* @param  hY    Hex y coord to move to.
	* @param  speed Speed to move at. (in pixels!)
	*/
	public void moveTargetHex(int hX, int hY, int speed)
	{
		int tX = Hexmap.GX2SX(hX, ConfigManager.getHexWidth());
		int tY = Hexmap.GY2SY(hY, ConfigManager.getHexHeight(), hX);
		
		moveTargetSpeed(tX, tY, speed);
	}
	/**
	* Move in 2D hexes at a set speed.
	* <p>
	*
	* @param  hX    How far right (or left, if negative) to move.
	* @param  hY    How far down (or up, if negative) to move
	* @param  speed Speed to move at. (in pixels!)
	*/
	public void moveDeltaHex(int hX, int hY, int speed)
	{
		moveTargetHex(hX + hX_, hY + hY_, speed);
	}

	public void moveDirectional(Direction direction, int distance, int speed)
	{
		int dX = 0, dY = 0, dZ = 0;
		switch (direction)
		{
		case down:
			dZ = -distance; break;
		case up:
			dZ = distance; break;
		
		case north:
			dY = -distance; break;
		case northEast:
			dX = distance; 
			if (Hexmap.getShift(dX + hX_)) dY = -distance;
			break;
		case northWest:
			dX = -distance;
			if (Hexmap.getShift(dX + hX_)) dY = -distance;
			break;
			
		case south:
			dY = distance; break;
		case southEast:
			dX = distance; 
			if (!Hexmap.getShift(dX + hX_)) dY = distance;
			break;
		case southWest:
			dX = -distance;
			if (!Hexmap.getShift(dX + hX_)) dY = distance;
			break;
		}
//		alignCoords();
		moveDeltaHex(dX, dY, speed);
		hX_ += dX;
		hY_ += dY;
		hZ_ += dZ;
	}
	
	@Override
	public void onStop()
	{
		alignCoords();
	}
}
