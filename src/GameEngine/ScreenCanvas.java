// By Iacon1
// Created 04/25/2021
// Game Canvas

package GameEngine;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GraphicsHandlerModule;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class ScreenCanvas extends UtilCanvas
{
	static private IntPoint2D camera = null;
	
	public static IntPoint2D getCamera()
	{
		return new IntPoint2D(camera);
	}
	public static void setCamera(IntPoint2D camera)
	{
		ScreenCanvas.camera = camera;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		resetImage();
		ModuleManager.getHighestOfType(GraphicsHandlerModule.class).drawWorld(this);
		renderImage(g);
	}
}
