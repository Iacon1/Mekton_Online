// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Utils.Logging;
import Utils.SimpleTimer;

import java.awt.event.KeyEvent;

import GameEngine.GameInfo;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.Alignable;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;

import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexDirection;
import Modules.HexUtilities.HexEntity;

public class DummyPlayer extends HexEntity<AxialHexCoord3D> implements InputGetter, CommandRunner
{	
	private int testMenu1 = -1;
	private int testMenu2 = -1;
	
	public DummyPlayer()
	{
		super();
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight());
	}
	public DummyPlayer(String owner)
	{
		super(owner);
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight());

		if (!GameInfo.isClient())
		{
			testMenu1 = new TestMenu(getOwner()).getId();
			testMenu2 = new TestMenu(getOwner()).getId();
			((Alignable) getEntity(testMenu2)).align(Alignable.AlignmentPoint.northEast, ((Alignable) getEntity(testMenu1)), Alignable.AlignmentPoint.southWest);
		}
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
	public void runCommand(String[] params)
	{
		{
			switch (params[0])
			{
			case "move": // TODO objects with MA
				switch (params[1])
				{
				case "north": case "no": case "n":
					moveDirectional(HexDirection.north, 1, 2);
					break;
				case "northwest": case "nw":
					moveDirectional(HexDirection.northWest, 1, 2);
					break;
				case "northeast": case "ne":
					moveDirectional(HexDirection.northEast, 1, 2);
					break;
					
				case "south": case "so": case "s":
					moveDirectional(HexDirection.south, 1, 2);
					break;
				case "southwest": case "sw":
					moveDirectional(HexDirection.southWest, 1, 2);
					break;
				case "southeast": case "se":
					moveDirectional(HexDirection.southEast, 1, 2);
					break;
					
				case "up":
					moveDirectional(HexDirection.up, 1, 2);
					break;
				case "down": case "do":
					moveDirectional(HexDirection.down, 1, 2);
					break;
				}
				break;
			}
		}
	}

	@Override
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		super.render(canvas, camera);
		if (getEntity(testMenu1) != null) GameInfo.getWorld().getEntity(testMenu1).render(canvas, camera);
		if (getEntity(testMenu2) != null) GameInfo.getWorld().getEntity(testMenu2).render(canvas, camera);
	}
}
