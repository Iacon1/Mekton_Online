package GameEngine.Graphics.RenderTokens;

import java.awt.Graphics2D;

import GameEngine.IntPoint2D;
import GameEngine.Graphics.Camera;
import GameEngine.Graphics.RenderQueue;
import GameEngine.Graphics.UtilCanvas;
import GameEngine.Managers.GraphicsManager;
import Utils.Logging;
import Utils.MiscUtils;

public class ImageRenderToken extends RenderQueue.RenderToken
{
	private String textureFile;
	private int dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2;
	
	public ImageRenderToken()
	{
		this.textureFile = null;
		this.dx1s = 0;
		this.dy1s = 0;
		this.dx2s = 0;
		this.dy2s = 0;
		this.sx1  = 0;
		this.sy1  = 0;
		this.sx2  = 0;
		this.sy2  = 0;
	}
	public ImageRenderToken(String textureFile, int dx1s, int dy1s, int dx2s, int dy2s, int sx1, int sy1, int sx2, int sy2)
	{
		this.textureFile = textureFile;
		this.dx1s = dx1s;
		this.dy1s = dy1s;
		this.dx2s = dx2s;
		this.dy2s = dy2s;
		this.sx1  = sx1;
		this.sy1  = sy1;
		this.sx2  = sx2;
		this.sy2  = sy2;
	}
	
	@Override
	public String toString()
	{
		String output = textureFile;
		output += "|" + Integer.toString(dx1s);
		output += "|" + Integer.toString(dy1s);
		output += "|" + Integer.toString(dx2s);
		output += "|" + Integer.toString(dy2s);
		output += "|" + Integer.toString(sx1);
		output += "|" + Integer.toString(sy1);
		output += "|" + Integer.toString(sx2);
		output += "|" + Integer.toString(sy2);
		
		return output;
	}
	public static ImageRenderToken fromString(String string)
	{
		String[] s = string.split("\\|");
		return new ImageRenderToken(s[0],
				Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4]),
				Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]), Integer.parseInt(s[8]));
	}
	@Override
	public void render(Graphics2D g)
	{
		g.drawImage(GraphicsManager.getImage(textureFile), dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2, null);
	}
}
