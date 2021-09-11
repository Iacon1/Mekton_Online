// By Iacon1
// Created 09/10/2021
//

package Modules.MektonCore;

import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.SpriteEntity;

public abstract class HexEntity extends SpriteEntity
{
	private int hX_; // x in hexes
	private int hY_; // y in hexes
	private int hZ_; // Z (of top-front-left corner) on map in hexes;
	
	private void alignCoords()
	{
		x_ = Hexmap.GX2SX(hX_, ConfigManager.getHexWidth());
		y_ = Hexmap.GY2SY(hY_, ConfigManager.getHexHeight(), Math.floorMod(hX_, 2) == 1);
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
	
	public int getX() // In hexes
	{
		return hX_ / ConfigManager.getHexWidth();
	}
	public int getY()
	{
		return hY_ / ConfigManager.getScreenHeight();
	}
	public int getZ()
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
	
	public void move(Direction direction, int distance)
	{
		switch (direction)
		{
		case down: hZ_ -= 1; break;
		case up: hZ_ += 1; break;
		
		case north: hY_ -= 1; break;
		case northEast:
			hX_ += 1; 
			if (hX_ % 2 == 1) hY_ -= 1; // Odd columns are drawn down, evens aren't
			break;
		case northWest:
			hX_ -= 1;
			if (hX_ % 2 == 1) hY_ -= 1; // Odd columns are drawn down, evens aren't
			break;
			
		case south: hY_ += 1; break;
		case southEast:
			hX_ += 1; 
			if (hX_ % 2 == 0) hY_ += 1; // Odd columns are drawn down, evens aren't
			break;
		case southWest:
			hX_ -= 1;
			if (hX_ % 2 == 0) hY_ += 1; // Odd columns are drawn down, evens aren't
			break;
		}
		alignCoords();
	}
}
