package GameEngine.Graphics.RenderTokens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import GameEngine.Graphics.RenderQueue;
import GameEngine.Managers.GraphicsManager;

public class TextRenderToken extends RenderQueue.RenderToken
{
	String text;
	String fontName;
	Color color;
	int sx;
	int sy;
	int heightPixels;
	
	public TextRenderToken()
	{
		this.text = null;
		this.fontName = null;
		this.color = null;
		this.sx = 0;
		this.sy = 0;
		this.heightPixels = 0;
	}
	public TextRenderToken(String text, String fontName, Color color, int sx, int sy, int heightPixels)
	{
		this.text = text;
		this.fontName = fontName;
		this.color = color;
		this.sx = sx;
		this.sy = sy;
		this.heightPixels = heightPixels;
	}
	@Override
	public void render(Graphics2D g)
	{
		Color oColor = g.getColor();
		Font font = GraphicsManager.getFont(fontName).deriveFont(GraphicsManager.getFontSize(heightPixels));
		g.setFont(font);
		g.setColor(color);
		
		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; ++i) g.drawString(lines[i], sx, sy + i * heightPixels + 1);
		g.setColor(oColor); // reset color to before we used one for the text
	}
}
