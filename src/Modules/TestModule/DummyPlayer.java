// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.MektonCore.MektonMap;
import Modules.MektonCore.EntityTypes.Human;

import java.awt.Color;
import java.awt.event.KeyEvent;

import GameEngine.GameInfo;
import GameEngine.ImageSprite;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;

import Modules.HexUtilities.HexConfigManager;

import Utils.MiscUtils;

public class DummyPlayer extends Human implements InputGetter, CommandRunner
{	
	public DummyPlayer()
	{
		super();
		setSprite(new ImageSprite(GameInfo.getServerPackResource("DummyPlayer.PNG")));
		setSpriteParams(0, 0, HexConfigManager.getHexWidth(), 2 * HexConfigManager.getHexHeight());
		setBounds(HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight(), 0, -HexConfigManager.getHexHeight());
	}
	public DummyPlayer(String owner, MektonMap map)
	{
		super(owner, map);
		setSprite(new ImageSprite(GameInfo.getServerPackResource("DummyPlayer.PNG")));
		setSpriteParams(0, 0, HexConfigManager.getHexWidth(), 2 * HexConfigManager.getHexHeight());
		setBounds(HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight(), 0, -HexConfigManager.getHexHeight());
	}
	
	@Override
	public String getName()
	{
		return "Dummy Player";
	}
	
	@Override
	public void onAnimStop() {}

	@Override
	public void onMouseClick(int mX, int mY, int button)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMousePress(int mX, int mY, int button)
	{
		AxialHexCoord3D point = mapToken.get().fromPixel(new Point2D(mX, mY));
		if (button == 0) GameInfo.setCommand("move -q " + point.q + " -r " + point.r);
	}

	@Override
	public void onMouseRelease(int mX, int mY, int button)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(int code)
	{
		switch (code)
		{
		case KeyEvent.VK_Q: GameInfo.setCommand("move -n -w"); break;
		case KeyEvent.VK_E: GameInfo.setCommand("move -n -e"); break;
		case KeyEvent.VK_W: GameInfo.setCommand("move -n"); break;
		case KeyEvent.VK_A: GameInfo.setCommand("move -s -w"); break;
		case KeyEvent.VK_D: GameInfo.setCommand("move -s -e"); break;
		case KeyEvent.VK_S: GameInfo.setCommand("move -s"); break;
		case KeyEvent.VK_SPACE: GameInfo.setCommand("move -u"); break;
		case KeyEvent.VK_SHIFT: GameInfo.setCommand("move -d"); break;
		}
	}

	@Override
	public void onKeyRelease(int code)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart()
	{
	}
	@Override
	public void onStop()
	{
		super.onStop();
	}

	@Override
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		super.render(canvas, camera);
		
		if (isPossessee())
		{
			String text =
					"Action points: " + MiscUtils.floatPrecise(remainingActions(), 2) + "\n" +
					statSummary();
			
			int fontSize = 20;
			Point2D textSize = canvas.textSize(text, "MicrogrammaNormalFix.TTF", fontSize);
			canvas.drawRectangle(Color.black, new Point2D(0, 0), textSize);
			canvas.drawText(text, "MicrogrammaNormalFix.TTF", Color.red, new Point2D(0, 0), fontSize);
		}
	}
	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		
	}
}
