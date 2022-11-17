// By Iacon1
// Created 09/14/2021
// A canvas with all the drawing utils

package GameEngine.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameEngine.IntPoint2D;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Graphics.RenderTokens.ImageRenderToken;
import GameEngine.Graphics.RenderTokens.RectangleRenderToken;
import GameEngine.Graphics.RenderTokens.TextRenderToken;
import GameEngine.Managers.GraphicsManager;

@SuppressWarnings("serial")
public abstract class UtilCanvas extends JPanel
{
	public float scaleX = 1f;
	public float scaleY = 1f;
	// Scales the image; Must be integers
	// 1x scale size is given by ConfigManager
	
	private BufferedImage image;
	private RenderQueue renderQueue;
	
	public UtilCanvas()
	{
		resetImage();
		renderQueue = new RenderQueue();
	}
	public boolean setScale(float scaleX, float scaleY)
	{
		boolean changedSize = (scaleX != scaleX || scaleY != scaleY);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		return changedSize;
	}
	public IntPoint2D descale(IntPoint2D point) // Converts a screen coord into a render coord
	{
		return new IntPoint2D(Math.round(point.x / scaleX), Math.round(point.y / scaleY));
	}
	
	public void addRectangle(Color color, IntPoint2D pos, IntPoint2D size)
	{
		int dx1s = (int) (pos.x);
		int dy1s = (int) (pos.y);
		int dx2s = (int) (dx1s + size.x);
		int dy2s = (int) (dy1s + size.y);
		
		renderQueue.addToken(new RectangleRenderToken(color, dx1s, dy1s, dx2s, dy2s));
	}
	
	public void addImage(String textureFile, IntPoint2D pos, IntPoint2D texturePos, IntPoint2D textureSize)
	{
		int dx1s = (int) (pos.x);
		int dy1s = (int) (pos.y);
		int dx2s = (int) (dx1s + textureSize.x);
		int dy2s = (int) (dy1s + textureSize.y);
		
		int sx1 = texturePos.x;
		int sy1 = texturePos.y;
		int sx2 = texturePos.x + textureSize.x;
		int sy2 = texturePos.y + textureSize.y;
		
		renderQueue.addToken(new ImageRenderToken(textureFile, dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2));
	}
	public void addText(String text, String font, Color color, IntPoint2D pos, int heightPixels)
	{
		int sx = (int) pos.x;
		int sy = (int) pos.y + heightPixels;

		renderQueue.addToken(new TextRenderToken(text, font, color, sx, sy, heightPixels));
	}
	public IntPoint2D textSize(String text, Font font, int heightPixels)
	{
		Graphics2D g = image.createGraphics();
		font = font.deriveFont(GraphicsManager.getFontSize(heightPixels));
		
		g.setFont(font);

		String[] lines = text.split("\n");
		
		int sizeX = 0;
		for (int i = 0; i < lines.length; ++i) sizeX = Math.max(sizeX, g.getFontMetrics().stringWidth(lines[i]));
		g.dispose();
		
		int sizeY = lines.length * heightPixels;
		
		return new IntPoint2D(sizeX, sizeY);
	}
	public IntPoint2D textSize(String text, String font, int heightPixels)
	{
		return textSize(text, GraphicsManager.getFont(font), heightPixels);
	}
	
	public RenderQueue getRenderQueue()
	{
		return renderQueue;
	}
	public void setRenderQueue(RenderQueue renderQueue)
	{
		this.renderQueue = renderQueue;
	}
	
	protected void resetImage()
	{
		image = new BufferedImage(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight(), BufferedImage.TYPE_3BYTE_BGR);
	}
	protected void renderImage(Graphics g)
	{
		renderQueue.render(image);
		
		int bX = (int) scaleX * ConfigManager.getScreenWidth();
		int bY = (int) scaleY * ConfigManager.getScreenHeight();
		Image image = this.image.getScaledInstance(ConfigManager.getScreenWidth(),  ConfigManager.getScreenHeight(), Image.SCALE_FAST);
		g.drawImage(image, 0, 0,  bX, bY, 0, 0, ConfigManager.getScreenWidth(),  ConfigManager.getScreenHeight(), null); 
	}
}
