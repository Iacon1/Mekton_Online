// By Iacon1
// Created 04/25/2021
// Game Canvas

package GameEngine;

import javax.swing.JPanel;

import GameEngine.Configurables.ModuleManager;

import java.awt.*;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel
{
	public float scaleX_;
	public float scaleY_;
	// Scales the image; Must be integers
	// 1x scale size is given by ConfigManager
	
	Graphics g_; // TODO hack so we don't have to juggle this in function calls
	
	public boolean setScale(float scaleX, float scaleY)
	{
		boolean changedSize = (scaleX_ != scaleX || scaleY_ != scaleY);
		scaleX_ = scaleX;
		scaleY_ = scaleY;
		return changedSize;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g_ = g;
		ModuleManager.drawWorld(ClientInfo.getWorld(), this);
		g_ = null;
	}
	
	// Below are a bunch of output functions
	
	public void drawImageScaled(Image img, int dx, int dy, int sx1, int sy1, int w, int h)
	{
		int dx1s = (int) (dx * scaleX_);
		int dy1s = (int) (dy * scaleY_);
		int dx2s = (int) (dx1s + w * scaleX_);
		int dy2s = (int) (dy1s + h * scaleY_);
		int sx2 = sx1 + w;
		int sy2 = sy1 + h;
		
		g_.drawImage(img, dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2, null);
	}
}
