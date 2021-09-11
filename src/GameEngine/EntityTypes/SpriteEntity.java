// By Iacon1
// Created 04/26/2021
// An object with a presence on a map

package GameEngine.EntityTypes;

import GameEngine.GameCanvas;
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
	
	/**
	* Called when mouse is clicked.
	* <p>
	* 
	* @param  cX     Camera's uppermost-leftmost corner's X-position
	* @param  cY     Camera's uppermost-leftmost corner's Y-position
	* @param  canvas Canvas to draw to
	*/
	@Override
	public void render(GameCanvas canvas) 
	{
		canvas.drawImageScaled(GraphicsManager.getImage(imagePath_), x_ - canvas.cX_, y_ - canvas.cY_, cTX_, cTY_, width_, height_);
	}
}
