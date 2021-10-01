// By Iacon1
// Created 10/01/2021
//

package GameEngine;

public class ImageSprite extends Sprite
{
	private String texturePath;
	
	public ImageSprite(String texturePath)
	{
		super();
		this.texturePath = texturePath;
	}

	@Override
	public void render(UtilCanvas canvas, Point2D pos)
	{
		canvas.drawImage(texturePath, pos, texturePos, textureSize);
	}

}
