// By Iacon1
// Created 04/26/2021
// Has a sprite

package GameEngine.EntityTypes;

import GameEngine.Camera;
import GameEngine.ScreenCanvas;
import GameEngine.Managers.GraphicsManager;

public abstract class SpriteEntity extends GameEntity
{
	private String imagePath_; // Image path

	protected int x_; // X position
	protected int y_; // Y position
	
	private int cTX_; // Offset on image sheet
	private int cTY_; // Offset on image sheet
	
	private int width_; // Width in pixels
	private int height_; // Height in pixels

	public void setSprite(String imagePath, Integer cTX, Integer cTY, Integer width, Integer height) // If any input is null then don't change
	{
		if (imagePath != null) imagePath_ = imagePath;
		if (cTX != null) cTX_ = cTX;
		if (cTY != null) cTY_ = cTY;
		if (width != null) width_ = width;
		if (height != null) height_ = height;
	}

	public int getPX()
	{
		return x_;
	}
	public int getPY()
	{
		return y_;
	}
	public void setPos(int x, int y)
	{
		x_ = x;
		y_ = y;
	}
	@Override
	public void render(ScreenCanvas canvas) 
	{
		canvas.drawImageScaled(GraphicsManager.getImage(imagePath_), x_ - Camera.pX, y_ - Camera.pY, cTX_, cTY_, width_, height_);
	}
}
