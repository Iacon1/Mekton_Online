// By Iacon1
// Created 04/26/2021
// An object with a presence on a map

package GameEngine;

public abstract class SpriteEntity extends GameEntity
{
	private String imagePath_; // Image path
	private int cTX_; // Offset on image sheet
	private int cTY_; // Offset on image sheet
	private int width_; // Width in pixels
	private int height_; // Height in pixels
	

	
	public SpriteEntity() {}
	public SpriteEntity(GameInfo world)
	{
		super(world);
	}
	
	public void setSprite(String imagePath, Integer cTX, Integer cTY, Integer width, Integer height) // If any input is null then don't change
	{
		if (imagePath != null) imagePath_ = imagePath;
		if (cTX != null) cTX_ = cTX;
		if (cTY != null) cTY_ = cTY;
		if (width != null) width_ = width;
		if (height != null) height_ = height;
	}
	
	@Override
	public void render(int pX, int pY, GameCanvas canvas) // We don't know where the top-left of the map corresponds to, so we'll let the map decide where to draw us
	{
		canvas.drawImageScaled(GraphicsManager.getImage(imagePath_), pX, pY, cTX_, cTY_, width_, height_);
	}
}
