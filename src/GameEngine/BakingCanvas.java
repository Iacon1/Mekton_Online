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
	private DrawFunc func_;

	public static interface DrawFunc
	{
		public void draw(BakingCanvas canvas);
	}

	@Override
	public void printComponent(Graphics g)
	{
		super.printComponent(g);
		g_ = g;
		func_.draw(this);
		g_ = null;
		func_ = null;
	}
	public Image bake(DrawFunc func)
	{
		int w = ConfigManager.getScreenWidth();
		int h = ConfigManager.getScreenHeight();
		int type = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage image = new BufferedImage(w, h, type);
		g_ = image.createGraphics();
		
		func_ = func;
		this.print(g_);
		
		return image;
	}
}
