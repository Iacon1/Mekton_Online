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
	public float scaleX;
	public float scaleY;
	// Scales the image; Must be integers
	// 1x scale size is given by ConfigManager

	Graphics g; // TODO This is a hack so we don't have to juggle this in function calls;
	// Set it before drawing and clear before the next set
	
	public boolean setScale(float scaleX, float scaleY)
	{
		boolean changedSize = (scaleX != scaleX || scaleY != scaleY);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		return changedSize;
	}
	public Point2D descale(Point2D point) // Converts a screen coord into a render coord
	{
		return new Point2D(Math.round(point.x / scaleX), Math.round(point.y / scaleY));
	}

	public void drawImageScaled(Image textureFile, Point2D pos, Point2D texturePos, Point2D textureSize)
	{
		int dx1s = (int) (pos.x * scaleX);
		int dy1s = (int) (pos.y * scaleY);
		int dx2s = (int) (dx1s + textureSize.x * scaleX);
		int dy2s = (int) (dy1s + textureSize.y * scaleY);
		
		int sx1 = texturePos.x;
		int sy1 = texturePos.y;
		int sx2 = texturePos.x + textureSize.x;
		int sy2 = texturePos.y + textureSize.y;
		
		g.drawImage(textureFile, dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2, null);
	}
	
	public void drawImageScaled(String textureFile, Point2D pos, Point2D texturePos, Point2D textureSize)
	{
		drawImageScaled(GraphicsManager.getImage(textureFile), pos, texturePos, textureSize);
	}
	
	public void drawText(String text, Font font, Color color, Point2D pos, int sizePixels)
	{
		Color oColor = g.getColor();
		int sx = (int) (pos.x * scaleX);
		int sy = (int) ((pos.y + sizePixels) * scaleY);
		int sSize = (int) (scaleY * sizePixels);
		font = font.deriveFont(GraphicsManager.getFontSize(sSize));
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, sx, sy);
		g.setColor(oColor); // reset color to before we used one for the text
	}
	
	public void drawText(String text, String font, Color color, Point2D pos, int sizePixels)
	{
		drawText(text, GraphicsManager.getFont(font), color, pos, sizePixels);
	}
}
