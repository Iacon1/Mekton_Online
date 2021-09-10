// By Iacon1
// Created 09/10/2021
//

package Modules.MektonCore;

import GameEngine.GameInfo;
import GameEngine.SpriteEntity;

public abstract class HexEntity extends SpriteEntity
{
	private int x_; // X (of top-front-left corner) on map
	private int y_; // Y (of top-front-left corner) on map
	private int z_; // Z (of top-front-left corner) on map
	
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
	
	public int getX()
	{
		return x_;
	}
	public int getY()
	{
		return y_;
	}
	public int getZ()
	{
		return z_;
	}
	public void setPos(Integer x, Integer y, Integer z) // If any input is null then don't change
	{
		if (x != null) x_ = x;
		if (y != null) y_ = y;
		if (z != null) z_ = z;
	}
	
	public void move(Direction direction, int distance)
	{
		switch (direction)
		{
		case down: z_ -= 1; break;
		case up: z_ += 1; break;
		
		case north: y_ -= 1; break;
		case northEast:
			x_ -= 1; 
			if (x_ % 2 == 1) y_ -= 1; // Odd columns are drawn down, evens aren't
			break;
		case northWest:
			x_ += 1;
			if (x_ % 2 == 1) y_ -= 1; // Odd columns are drawn down, evens aren't
			break;
			
		case south: y_ += 1; break;
		case southEast:
			x_ -= 1; 
			if (x_ % 2 == 0) y_ += 1; // Odd columns are drawn down, evens aren't
			break;
		case southWest:
			x_ += 1;
			if (x_ % 2 == 0) y_ += 1; // Odd columns are drawn down, evens aren't
			break;
		}
	}
}
