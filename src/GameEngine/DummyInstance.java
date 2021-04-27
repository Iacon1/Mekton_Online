// By Iacon1
// Created 04/26/2021
//

package GameEngine;

import java.awt.Graphics2D;

public class DummyInstance extends GameInstance
{
	private String path_ = "Resources/Server Packs/Default/DummyPlayer.PNG";
	private int cTX_ = 0;
	private int cTY_ = 0;
	
	@Override
	public void render(int pX, int pY, Graphics2D g)
	{
		int hexSize = ConfigManager.getHexSize();
		g.drawImage(GraphicsManager.getImage(path_), pX, pY, pX + hexSize, pY + hexSize, cTX_, cTY_, cTX_ + hexSize, cTY_ + hexSize, null);
	}

}
