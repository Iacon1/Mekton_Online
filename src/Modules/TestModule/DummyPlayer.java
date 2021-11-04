// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.MektonCore.MektonMap;
import Modules.MektonCore.EntityTypes.Human;
import Modules.MektonCore.EntityTypes.MapEntity;
import Modules.MektonCore.EntityTypes.MektonActor;

import java.awt.Color;
import java.awt.event.KeyEvent;

import GameEngine.GameInfo;
import GameEngine.ImageSprite;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;

import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexDirection;

import Utils.MiscUtils;

public class DummyPlayer extends Human implements InputGetter, CommandRunner
{	
	public DummyPlayer()
	{
		super();
		setSprite(new ImageSprite("Resources/Server Packs/Default/DummyPlayer.PNG"));
		setSpriteParams(0, 0, HexConfigManager.getHexWidth(), 2 * HexConfigManager.getHexHeight());
		setBounds(HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight(), 0, -HexConfigManager.getHexHeight());
	}
	public DummyPlayer(String owner, MektonMap map)
	{
		super(owner, map);
		setSprite(new ImageSprite("Resources/Server Packs/Default/DummyPlayer.PNG"));
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
		if (button == 0) GameInfo.setCommand("moveMouse " + point.q + " " + point.r + " " + point.z);
		// TODO Auto-generated method stub
		
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
		case KeyEvent.VK_Q: GameInfo.setCommand("move nw"); break;
		case KeyEvent.VK_W: GameInfo.setCommand("move no"); break;
		case KeyEvent.VK_E: GameInfo.setCommand("move ne"); break;
		case KeyEvent.VK_A: GameInfo.setCommand("move sw"); break;
		case KeyEvent.VK_S: GameInfo.setCommand("move so"); break;
		case KeyEvent.VK_D: GameInfo.setCommand("move se"); break;
		case KeyEvent.VK_SPACE: GameInfo.setCommand("move up"); break;
		case KeyEvent.VK_SHIFT: GameInfo.setCommand("move do"); break;
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
	public boolean runCommand(String... params)
	{
		{
			switch (params[0])
			{
			case "moveMouse":
					AxialHexCoord3D target = new AxialHexCoord3D(Integer.valueOf(params[1]), Integer.valueOf(params[2]), hexPos.z);
					movePath(mapToken.get().pathfind(hexPos, target), 2);
					return true;
			case "move": // TODO objects with MA
				switch (params[1])
				{
				case "north": case "no": case "n":
					moveDirectionalAct(HexDirection.north, 1, 2);
					return true;
				case "northwest": case "nw":
					moveDirectionalAct(HexDirection.northWest, 1, 2);
					return true;
				case "northeast": case "ne":
					moveDirectionalAct(HexDirection.northEast, 1, 2);
					return true;
					
				case "south": case "so": case "s":
					moveDirectionalAct(HexDirection.south, 1, 2);
					return true;
				case "southwest": case "sw":
					moveDirectionalAct(HexDirection.southWest, 1, 2);
					return true;
				case "southeast": case "se":
					moveDirectionalAct(HexDirection.southEast, 1, 2);
					return true;
					
				case "up":
					moveDirectionalAct(HexDirection.up, 1, 2);
					return true;
				case "down": case "do":
					moveDirectionalAct(HexDirection.down, 1, 2);
					return true;
				}
			default: return false;
			}
		}
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
