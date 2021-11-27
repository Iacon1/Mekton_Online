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
	public String onMouseClick(int mX, int mY, int button) {return null;}

	@Override
	public String onMousePress(int mX, int mY, int button)
	{
		AxialHexCoord3D point = mapToken.get().fromPixel(new Point2D(mX, mY));
		if (button == 0) return "move -q " + point.q + " -r " + point.r;
		else return null;
	}

	@Override
	public String onMouseRelease(int mX, int mY, int button) {return null;}

	@Override
	public String onKeyPress(int code)
	{
		if (chatBox.get().isSelected()) return null;
		switch (code)
		{
		case KeyEvent.VK_Q: return "move -n -w";
		case KeyEvent.VK_E: return "move -n -e";
		case KeyEvent.VK_W: return "move -n";
		case KeyEvent.VK_A: return "move -s -w";
		case KeyEvent.VK_D: return "move -s -e";
		case KeyEvent.VK_S: return "move -s"; 
		case KeyEvent.VK_SPACE: return "move -u";
		case KeyEvent.VK_SHIFT: return "move -d";
		}
		return null;
	}

	@Override
	public String onKeyRelease(int code) {return null;}

	@Override
	public void onStart()
	{
	}

	@Override
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		super.render(canvas, camera);
		
		if (false)//isPossessee())
		{
			String text =
					"Action points: " + MiscUtils.floatPrecise((float) remainingActions(), 2) + "\n" +
					statSummary();
			
			int fontSize = 20;
			Point2D textSize = canvas.textSize(text, "MicrogrammaNormalFix", fontSize);
			canvas.drawRectangle(Color.black, new Point2D(0, 0), textSize);
			canvas.drawText(text, "MicrogrammaNormalFix", Color.red, new Point2D(0, 0), fontSize);
		}
		chatBox.get().render(canvas, camera);
	}
	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		
	}
}
