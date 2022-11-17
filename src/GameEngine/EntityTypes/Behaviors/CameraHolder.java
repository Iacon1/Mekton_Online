// By Iacon1
// Created 11/16/2022
// Holds a camera

package GameEngine.EntityTypes.Behaviors;

import GameEngine.IntPoint2D;
import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.SpriteEntity;
import GameEngine.Graphics.Camera;
import GameEngine.Graphics.ScreenCanvas;

public class CameraHolder extends Behavior
{
	public static enum CameraType
	{
		centered
	}

	protected CameraType cameraType;
	protected Camera camera;
	
	public CameraHolder(CameraType cameraType)
	{
		this.cameraType = cameraType;
		this.camera = new Camera();
	}
	@Override
	public void update()
	{
		if (!SpriteEntity.class.isAssignableFrom(getParent().getClass())) return; // Needs to be a sprite entity or subclass thereof
		SpriteEntity parent = (SpriteEntity) getParent();
		switch (cameraType)
		{
		case centered:
			IntPoint2D screenDimensions = new IntPoint2D(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
			camera.topLeftCorner = parent.getPos().subtract(screenDimensions.divide(2)); // Parent position minus half the screen size
			camera.z = 0;
		}
	}

	public Camera getCamera() {return camera;}
	
	@Override public void render(ScreenCanvas canvas, Camera camera) {}

}
