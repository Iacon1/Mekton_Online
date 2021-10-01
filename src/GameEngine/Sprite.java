// By Iacon1
// Created 10/01/2021
// Something that can be drawn to the screen

package GameEngine;

public abstract class Sprite
{
	protected Point2D texturePos; // Offset on texture sheet
	protected Point2D textureSize; // Width, height on texture sheet
	
	public Sprite()
	{
		texturePos = new Point2D(0, 0);
		textureSize = new Point2D(0, 0);
	}
	
	public void setBasicParams(Integer textureX, Integer textureY, Integer width, Integer height)
	{
		if (textureX != null) texturePos.x = textureX;
		if (textureY != null) texturePos.y = textureY;
		
		if (width != null) textureSize.x = width;
		if (height != null) textureSize.y = height;
	}
	
	public Point2D getSize()
	{
		return textureSize.clone();
	}
	
	public abstract void render(UtilCanvas canvas, Point2D pos);
	
}
