// By Iacon1
// Created 04/26/2021
//

package Modules.TestModule;

import java.awt.event.KeyEvent;

import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.InputGetter;
import GameEngine.EntityTypes.TransSerializable;
import Modules.MektonCore.HexEntity;
import Utils.Logging;

public class DummyPlayer extends HexEntity implements InputGetter
{	
	public DummyPlayer()
	{
		super();
		setSprite("Resources/Server Packs/Default/DummyPlayer.PNG", 0, 0, ConfigManager.getHexWidth(), ConfigManager.getHexHeight());
	}
	
	@Override
	public String getName()
	{
		return "Dummy Player";
	}

	@Override
	public void onMouseClick(int mX, int mY, int button) {Logging.logNotice("Mouse clicked!");}
	@Override
	public void onMousePress(int mX, int mY, int button) {}

	@Override
	public void onMouseRelease(int mX, int mY, int button) {}

	@Override
	public void onKeyPress(int code) {Logging.logNotice("Key clicked: " + KeyEvent.getKeyText(code));}

	@Override
	public void onKeyRelease(int code)
	{
		// TODO Auto-generated method stub
		
	}
}
