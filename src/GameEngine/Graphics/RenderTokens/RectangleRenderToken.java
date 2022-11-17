package GameEngine.Graphics.RenderTokens;

import java.awt.Color;
import java.awt.Graphics2D;

import GameEngine.IntPoint2D;
import GameEngine.Graphics.Camera;
import GameEngine.Graphics.RenderQueue;
import GameEngine.Graphics.UtilCanvas;
import GameEngine.Managers.GraphicsManager;

public class RectangleRenderToken extends RenderQueue.RenderToken
{
	private Color color;
	private int dx1s, dy1s, dx2s, dy2s;
	
	public RectangleRenderToken()
	{
		this.color = null;
		this.dx1s = 0;
		this.dy1s = 0;
		this.dx2s = 0;
		this.dy2s = 0;
	}
	public RectangleRenderToken(Color color, int dx1s, int dy1s, int dx2s, int dy2s)
	{
		this.color = color;
		this.dx1s = dx1s;
		this.dy1s = dy1s;
		this.dx2s = dx2s;
		this.dy2s = dy2s;
	}
	
	@Override
	public void render(Graphics2D g)
	{
		Color oColor = g.getColor();
		
		g.setColor(color);
		g.fillRect(dx1s, dy1s, dx2s, dy2s);
		g.setColor(oColor); // reset color to before we used one for the text
	}
}
