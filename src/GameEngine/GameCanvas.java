// By Iacon1
// Created 04/25/2021
// Game Canvas

package GameEngine;

import javax.swing.JPanel;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GraphicsHandlerModule;
import GameEngine.Managers.GraphicsManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel
{
	public float scaleX_;
	public float scaleY_;
	// Scales the image; Must be integers
	// 1x scale size is given by ConfigManager
	
	Graphics g_; // TODO This is a hack so we don't have to juggle this in function calls
	
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
		ModuleManager.getHighestOfType(GraphicsHandlerModule.class).drawWorld(this);
		drawText("Hello", "MicrogrammaNormalFix.TTF", GraphicsManager.getColor(255, 0, 0), 0, 0, 32);
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
	
	
	public void drawImageScaled(String img, int dx, int dy, int sx1, int sy1, int w, int h)
	{
		drawImageScaled(GraphicsManager.getImage(img), dx, dy, sx1, sy1, w, h);
	}
	
	public void drawText(String text, Font font, Color color, int x, int y, int sizePixels)
	{
		Color oColor = g_.getColor();
		int sx = (int) (x * scaleX_);
		int sy = (int) ((y + sizePixels) * scaleY_);
		int sSize = (int) (scaleY_ * sizePixels);
		font = font.deriveFont(GraphicsManager.getFontSize(sSize));
		g_.setFont(font);
		g_.setColor(color);
		g_.drawString(text, sx, sy);
		g_.setColor(oColor); // reset color to before we used one for the text
	}
	
	public void drawText(String text, String font, Color color, int x, int y, int sizePixels)
	{
		drawText(text, GraphicsManager.getFont(font), color, x, y, sizePixels);
	}
}
