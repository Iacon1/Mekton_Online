// By Iacon1
// Created 04/26/2021
// An object with a presence on a map

package GameEngine;

public abstract class PhysicalObject extends SolidEntity
{
	private String imagePath_; // Image path
	private int cTX_; // Offset on image sheet
	private int cTY_; // Offset on image sheet
	private int width_; // Width in pixels
	private int height_; // Height in pixels
	
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
	
	public PhysicalObject() {}
	public PhysicalObject(GameWorld world)
	{
		super(world);
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
	public void setSprite(String imagePath, Integer cTX, Integer cTY, Integer width, Integer height) // If any input is null then don't change
	{
		if (imagePath != null) imagePath_ = imagePath;
		if (cTX != null) cTX_ = cTX;
		if (cTY != null) cTY_ = cTY;
		if (width != null) width_ = width;
		if (height != null) height_ = height;
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
	
	@Override
	public void render(int pX, int pY, GameCanvas canvas) // We don't know where the top-left of the map corresponds to, so we'll let the map decide where to draw us
	{
		canvas.drawImageScaled(GraphicsManager.getImage(imagePath_), pX, pY, cTX_, cTY_, width_, height_);
	}
}
