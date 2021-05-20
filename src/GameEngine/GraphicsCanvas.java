// By Iacon1
// Created 04/25/2021
// Game Canvas

package GameEngine;

import java.awt.Canvas;

import javax.swing.JPanel;

import java.awt.*;

public class GraphicsCanvas extends JPanel
{
	GameEntity renderer_; // Draws to us
	
	public void setRenderer(GameEntity renderer)
	{
		renderer_ = renderer;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (renderer_ != null)
			renderer_.render(0, 0, (Graphics2D) g);
	}
}
