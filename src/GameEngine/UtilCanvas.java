// By Iacon1
// Created 09/14/2021
// A canvas with all the drawing utils

package GameEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import GameEngine.Managers.GraphicsManager;

public abstract class UtilCanvas extends JPanel
{
	public float scaleX_;
	public float scaleY_;
	// Scales the image; Must be integers
	// 1x scale size is given by ConfigManager

	Graphics g_; // TODO This is a hack so we don't have to juggle this in function calls;
	// Set it before drawing and clear before the next set
	
	public boolean setScale(float scaleX, float scaleY)
	{
		boolean changedSize = (scaleX_ != scaleX || scaleY_ != scaleY);
		scaleX_ = scaleX;
		scaleY_ = scaleY;
		return changedSize;
	}
	public Point2D descale(Point2D point) // Converts a screen coord into a render coord
	{
		return new Point2D(Math.round(point.x_ / scaleX_), Math.round(point.y_ / scaleY_));
	}

	public void drawImageScaled(Image textureFile, Point2D pos, Point2D texturePos, Point2D textureSize)
	{
		int dx1s = (int) (pos.x_ * scaleX_);
		int dy1s = (int) (pos.y_ * scaleY_);
		int dx2s = (int) (dx1s + textureSize.x_ * scaleX_);
		int dy2s = (int) (dy1s + textureSize.y_ * scaleY_);
		
		int sx1 = texturePos.x_;
		int sy1 = texturePos.y_;
		int sx2 = texturePos.x_ + textureSize.x_;
		int sy2 = texturePos.y_ + textureSize.y_;
		
		g_.drawImage(textureFile, dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2, null);
	}
	
	public void drawImageScaled(String textureFile, Point2D pos, Point2D texturePos, Point2D textureSize)
	{
		drawImageScaled(GraphicsManager.getImage(textureFile), pos, texturePos, textureSize);
	}
	
	public void drawText(String text, Font font, Color color, Point2D pos, int sizePixels)
	{
		Color oColor = g_.getColor();
		int sx = (int) (pos.x_ * scaleX_);
		int sy = (int) ((pos.y_ + sizePixels) * scaleY_);
		int sSize = (int) (scaleY_ * sizePixels);
		font = font.deriveFont(GraphicsManager.getFontSize(sSize));
		g_.setFont(font);
		g_.setColor(color);
		g_.drawString(text, sx, sy);
		g_.setColor(oColor); // reset color to before we used one for the text
	}
	
	public void drawText(String text, String font, Color color, Point2D pos, int sizePixels)
	{
		drawText(text, GraphicsManager.getFont(font), color, pos, sizePixels);
	}
}
