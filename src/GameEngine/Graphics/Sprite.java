// By Iacon1
// Created 10/01/2021
// Something that can be drawn to the screen

package GameEngine.Graphics;

import GameEngine.IntPoint2D;

public abstract class Sprite
{
	protected IntPoint2D pos;		  // World position to render top-left corner at
	protected IntPoint2D texturePos;  // Offset on texture sheet
	protected IntPoint2D textureSize; // Width, height on texture sheet
	
	public Sprite()
	{
		texturePos = new IntPoint2D(0, 0);
		textureSize = new IntPoint2D(0, 0);
	}
	
	public void setPos(Integer x, Integer y)
	{
		if (x != null) pos.x = x;
		if (y != null) pos.y = y;
	}
	public void setBasicParams(Integer textureX, Integer textureY, Integer width, Integer height)
	{
		if (textureX != null) texturePos.x = textureX;
		if (textureY != null) texturePos.y = textureY;
		
		if (width != null) textureSize.x = width;
		if (height != null) textureSize.y = height;
	}
	
	public IntPoint2D getSize()
	{
		return new IntPoint2D(textureSize);
	}

	public abstract void render(UtilCanvas canvas, IntPoint2D pos);
}
