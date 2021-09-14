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
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g_ = g;
		ModuleManager.getHighestOfType(GraphicsHandlerModule.class).drawWorld(this);
		g_ = null;
	}
}
