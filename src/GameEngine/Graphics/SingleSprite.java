// By Iacon1
// Created 10/01/2021
//

package GameEngine.Graphics;

import GameEngine.IntPoint2D;

public class SingleSprite extends Sprite
{
	private String texturePath;
	
	public SingleSprite(String texturePath)
	{
		super();
		this.texturePath = texturePath;
	}

	@Override
	public void render(UtilCanvas canvas, IntPoint2D pos)
	{
		canvas.addImage(texturePath, pos, texturePos, textureSize);
	}

}
