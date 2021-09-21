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
	public GUISpriteEntity()
	{
		super();
	}
	public GUISpriteEntity(String owner)
	{
		super(owner);
	}

	@Override
	public void render(ScreenCanvas canvas, Point2D camera) 
	{
		canvas.drawImageScaled(texturePath, pos, texturePos, textureSize);
	}
}
