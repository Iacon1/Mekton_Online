// By Iacon1
// Created 09/14/2021
// A canvas with all the drawing utils

package GameEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameEngine.Configurables.ConfigManager;
import GameEngine.Managers.GraphicsManager;

@SuppressWarnings("serial")
public abstract class UtilCanvas extends JPanel
{
	public float scaleX = 1f;
	public float scaleY = 1f;
	// Scales the image; Must be integers
	// 1x scale size is given by ConfigManager
	
	BufferedImage image;
	
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
	
	public void drawRectangle(Color color, Point2D pos, Point2D size)
	{
		Graphics2D g = image.createGraphics();
		Color oColor = image.getGraphics().getColor();
		int dx1s = (int) (pos.x);
		int dy1s = (int) (pos.y);
		int dx2s = (int) (dx1s + size.x);
		int dy2s = (int) (dy1s + size.y);
		
		g.setColor(color);
		
		g.fillRect(dx1s, dy1s, dx2s, dy2s);
		g.setColor(oColor); // reset color to before we used one for the text
		g.dispose();
	}
	
	public void drawImage(Image textureFile, Point2D pos, Point2D texturePos, Point2D textureSize)
	{
		Graphics2D g = image.createGraphics();
		int dx1s = (int) (pos.x);
		int dy1s = (int) (pos.y);
		int dx2s = (int) (dx1s + textureSize.x);
		int dy2s = (int) (dy1s + textureSize.y);
		
		int sx1 = texturePos.x;
		int sy1 = texturePos.y;
		int sx2 = texturePos.x + textureSize.x;
		int sy2 = texturePos.y + textureSize.y;
		
		g.drawImage(textureFile, dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2, null);
		g.dispose();
	}
	
	public void drawImage(String textureFile, Point2D pos, Point2D texturePos, Point2D textureSize)
	{
		drawImage(GraphicsManager.getImagePath(textureFile), pos, texturePos, textureSize);
	}
	
	public void drawText(String text, Font font, Color color, Point2D pos, int heightPixels)
	{
		Graphics2D g = image.createGraphics();
		Color oColor = image.getGraphics().getColor();
		int sx = (int) pos.x;
		int sy = (int) pos.y + heightPixels;
		font = font.deriveFont(GraphicsManager.getFontSize(heightPixels));
		
		g.setFont(font);
		g.setColor(color);
		
		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; ++i) g.drawString(lines[i], sx, sy + i * heightPixels + 1);
		g.setColor(oColor); // reset color to before we used one for the text
		g.dispose();
	}
	public void drawText(String text, String font, Color color, Point2D pos, int heightPixels)
	{
		drawText(text, GraphicsManager.getFontPath(font), color, pos, heightPixels);
	}
	public Point2D textSize(String text, Font font, int heightPixels)
	{
		Graphics2D g = image.createGraphics();
		font = font.deriveFont(GraphicsManager.getFontSize(heightPixels));
		
		g.setFont(font);

		String[] lines = text.split("\n");
		
		int sizeX = 0;
		for (int i = 0; i < lines.length; ++i) sizeX = Math.max(sizeX, g.getFontMetrics().stringWidth(lines[i]));
		g.dispose();
		
		int sizeY = lines.length * heightPixels;
		
		return new Point2D(sizeX, sizeY);
	}
	public Point2D textSize(String text, String font, int heightPixels)
	{
		return textSize(text, GraphicsManager.getFontPath(font), heightPixels);
	}
	
	protected void resetImage()
	{
		image = new BufferedImage(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight(), BufferedImage.TYPE_3BYTE_BGR);
	}
	protected void renderImage(Graphics g)
	{
		int bX = (int) scaleX * ConfigManager.getScreenWidth();
		int bY = (int) scaleY * ConfigManager.getScreenHeight();
		Image image = this.image.getScaledInstance(ConfigManager.getScreenWidth(),  ConfigManager.getScreenHeight(), Image.SCALE_FAST);
		g.drawImage(image, 0, 0,  bX, bY, 0, 0, ConfigManager.getScreenWidth(),  ConfigManager.getScreenHeight(), null); 
	}
}
