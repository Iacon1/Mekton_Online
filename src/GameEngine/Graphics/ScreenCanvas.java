// By Iacon1
// Created 04/25/2021
// Game Canvas

package GameEngine.Graphics;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class ScreenCanvas extends UtilCanvas
{
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		resetImage();
		renderImage(g);
	}
}
