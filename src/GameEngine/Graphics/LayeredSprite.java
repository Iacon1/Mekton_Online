// By Iacon1
// Created 01/21/2023
//
package GameEngine.Graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.HashMap;
import java.util.Map;

import GameEngine.IntPoint2D;
import GameEngine.Managers.GraphicsManager;
import Utils.Logging;
import Utils.MiscUtils;

public class LayeredSprite extends Sprite
{
	private Map<String, String> texturePaths;
	private Map<String, Color[]> palettes;
	
	public LayeredSprite()
	{
		super();
		texturePaths = new HashMap<String, String>();
		palettes = new HashMap<String, Color[]>();
	}
	
	public void setLayer(String name, String texturePath, Color... palette)
	{
		texturePaths.put(name, texturePath);
		palettes.put(name, palette);
	}
	public void setLayer(String name, String texturePath)
	{
		texturePaths.put(name, texturePath);
		Color[] palette = GraphicsManager.getPalette(texturePath);
		palettes.put(name, palette);
	}
	public void setTexture(String name, String texturePath)
	{
		texturePaths.put(name, texturePath);
	}
	public String getTexture(String name)
	{
		return texturePaths.get(name);
	}
	public void setPalette(String name, Color... palette)
	{
		palettes.put(name, palette);
	}
	public Color[] getPalette(String name)
	{
		return palettes.get(name);
	}
	
	@Override
	public void render(UtilCanvas canvas, IntPoint2D pos)
	{
		for (String layerName : texturePaths.keySet())
			canvas.addPaletteImage(texturePaths.get(layerName), pos, texturePos, textureSize, palettes.get(layerName));
	}

}
