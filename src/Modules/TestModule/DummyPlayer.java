// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Utils.Logging;
import Utils.SimpleTimer;

import java.awt.event.KeyEvent;

import GameEngine.GameInfo;
import GameEngine.EntityTypes.InputGetter;

import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexEntity;

public class DummyPlayer extends HexEntity<AxialHexCoord3D> implements InputGetter
{	
	public DummyPlayer()
	{
		super();
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, HexConfigManager.getHexWidth(), HexConfigManager.getHexHeight());
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
	
	private SimpleTimer timer_;
	@Override
	public void onStart()
	{
		if (timer_ == null) timer_ = new SimpleTimer();
		timer_.start();
	}
	@Override
	public void onStop()
	{
		super.onStop();
		Logging.logNotice("Took " + timer_.stopTime() + " millis");
	}
}
