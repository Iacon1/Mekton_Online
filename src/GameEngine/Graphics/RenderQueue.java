// By Iacon1
// Created 11/06/2022
// Stack of items to be rendered.
// Populated serverside and transferred to client.

package GameEngine.Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Queue;
import java.util.LinkedList;

public class RenderQueue
{
	public static abstract class RenderToken
	{
		public RenderToken() {}
		public abstract void render(Graphics2D g);
	}
	private Queue<RenderToken> queue;
	
	public RenderQueue()
	{
		queue = new LinkedList<RenderToken>();
	}
	public void addToken(RenderToken token)
	{
		queue.add(token);
	}

	public void render(BufferedImage image)
	{
		while (!queue.isEmpty())
		{
			Graphics2D g = image.createGraphics();
			RenderToken token = queue.poll();
			token.render(g);
			g.dispose();
		}
	}
}
