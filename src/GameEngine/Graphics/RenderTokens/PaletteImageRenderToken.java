package GameEngine.Graphics.RenderTokens;

import java.awt.Color;
import java.awt.Graphics2D;

import GameEngine.Managers.GraphicsManager;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public class PaletteImageRenderToken extends ImageRenderToken
{
	private Color[] palette;
	
	public PaletteImageRenderToken()
	{
		super();
		palette = new Color[0];
	}
	public PaletteImageRenderToken(String textureFile, int dx1s, int dy1s, int dx2s, int dy2s, int sx1, int sy1, int sx2, int sy2, Color... palette)
	{
		super(textureFile, dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2);
		this.palette = palette;
	}
	
	@Override
	public String toString()
	{
		String output = super.toString();
		output += "|" + JSONManager.serializeJSON(palette);
		
		return output;
	}
	public static PaletteImageRenderToken fromString(String string)
	{
		String[] s = string.split("\\|");
		return new PaletteImageRenderToken(s[0],
				Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4]),
				Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]), Integer.parseInt(s[8]),
				JSONManager.deserializeJSON(s[9], Color[].class));
	}
	@Override
	public void render(Graphics2D g)
	{
		Logging.logNotice(MiscUtils.arrayToString(GraphicsManager.getPalette(textureFile), ","));
		Logging.logNotice(MiscUtils.arrayToString(palette, ", "));
		g.drawImage(GraphicsManager.getImage(textureFile, palette), dx1s, dy1s, dx2s, dy2s, sx1, sy1, sx2, sy2, null);
	}
}
