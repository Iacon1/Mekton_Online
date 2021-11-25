// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.MektonCore.EntityTypes.Human;
import Modules.MektonCore.EntityTypes.MektonMap;

import java.awt.Color;
import java.awt.event.KeyEvent;

import GameEngine.EntityToken;
import GameEngine.GameInfo;
import GameEngine.ImageSprite;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.Alignable;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;
import GameEngine.EntityTypes.SpriteEntity;
import GameEngine.Managers.GraphicsManager;
import Modules.BaseModule.ChatBox;
import Modules.HexUtilities.HexConfigManager;

import Utils.MiscUtils;

public class DummyPlayer extends Human implements InputGetter, CommandRunner
{	
	EntityToken<ChatBox> chatBox;
	
	public DummyPlayer()
	{
		super();
		setSprite(new ImageSprite("DummyPlayer"));
		setSpriteParams(0, 0, HexConfigManager.getHexWidth(), 2 * HexConfigManager.getHexHeight());
		setBounds(HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight(), 0, -HexConfigManager.getHexHeight());
	}
	public DummyPlayer(MektonMap map)
	{
		super(map);
		setSprite(new ImageSprite("DummyPlayer"));
		setSpriteParams(0, 0, HexConfigManager.getHexWidth(), 2 * HexConfigManager.getHexHeight());
		setBounds(HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight(), 0, -HexConfigManager.getHexHeight());
		
		ChatBox chatBox = new ChatBox("MicrogrammaNormalFix", Color.red, 20);
		chatBox.setOwnerID(0); // TODO find value of possessor dynamically
		addChild(chatBox);
		this.chatBox = new EntityToken<ChatBox>(chatBox.getId());
		chatBox.align(AlignmentPoint.west, null, AlignmentPoint.west);
		addChild(chatBox);
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
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		super.render(canvas, camera);
		
		if (isPossessee())
		{
			String text =
					"Action points: " + MiscUtils.floatPrecise((float) remainingActions(), 2) + "\n" +
					statSummary();
			
			int fontSize = 20;
			Point2D textSize = canvas.textSize(text, "MicrogrammaNormalFix", fontSize);
			canvas.drawRectangle(Color.black, new Point2D(0, 0), textSize);
			canvas.drawText(text, "MicrogrammaNormalFix", Color.red, new Point2D(0, 0), fontSize);
		}
		chatBox.get().setPos(0, 0);
		chatBox.get().render(canvas, camera);
	}
	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		
	}
}
