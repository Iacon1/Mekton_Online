// By Iacon1
// Created 09/10/2021
// Has a sprite but doesn't respond to camera

package GameEngine.EntityTypes.GUITypes;

import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Managers.GraphicsManager;

public abstract class GUISpriteEntity extends GameEntity
{
	private String imagePath_; // Image path

	protected int x_; // X position
	protected int y_; // Y position
	
	private int cTX_; // Offset on image sheet
	private int cTY_; // Offset on image sheet
	
	protected int width_; // Width in pixels
	protected int height_; // Height in pixels

	public void setSprite(String imagePath, Integer cTX, Integer cTY, Integer width, Integer height) // If any input is null then don't change
	{
		if (imagePath != null) imagePath_ = imagePath;
		if (cTX != null) cTX_ = cTX;
		if (cTY != null) cTY_ = cTY;
		if (width != null) width_ = width;
		if (height != null) height_ = height;
	}

	@Override
	public void render(ScreenCanvas canvas) 
	{
		canvas.drawImageScaled(GraphicsManager.getImage(imagePath_), x_, y_, cTX_, cTY_, width_, height_);
	}
}
