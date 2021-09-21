// By Iacon1
// Created 09/14/2021
// Canvas that can convert itself into an image

package GameEngine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import GameEngine.Configurables.ConfigManager;

@SuppressWarnings("serial")
public class BakingCanvas extends UtilCanvas
{
	private DrawFunc func;

	public static interface DrawFunc
	{
		public void draw(BakingCanvas canvas);
	}

	@Override
	public void printComponent(Graphics g)
	{
		super.printComponent(g);
		this.g = g;
		func.draw(this);
		this.g = null;
		func = null;
	}
	public Image bake(DrawFunc func)
	{
		int w = ConfigManager.getScreenWidth();
		int h = ConfigManager.getScreenHeight();
		int type = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage image = new BufferedImage(w, h, type);
		g = image.createGraphics();
		
		this.func = func;
		this.print(g);
		
		return image;
	}
}
