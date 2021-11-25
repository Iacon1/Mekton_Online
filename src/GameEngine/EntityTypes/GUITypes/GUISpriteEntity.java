// By Iacon1
// Created 09/10/2021
// Has a sprite but doesn't respond to camera
// Has an owner, and won't appear to other players

package GameEngine.EntityTypes.GUITypes;

import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.SpriteEntity;

public abstract class GUISpriteEntity extends SpriteEntity
{
	private int ownerID;
	
	public GUISpriteEntity()
	{
		super();
	}
	public GUISpriteEntity(int ownerID)
	{
		super();
		this.ownerID = ownerID;
	}

	@Override
	public void render(ScreenCanvas canvas, Point2D camera) 
	{
		if (sprite != null) sprite.render(canvas, pos.add(spriteOff));
	}
}
