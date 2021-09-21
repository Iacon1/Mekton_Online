// By Iacon1
// Created 09/16/2021
//

package Modules.TestModule;

import GameEngine.GameInfo;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.MektonCore.MektonHex;
import Modules.MektonCore.MektonMap;
import Utils.Logging;

public class EditMap extends MektonMap implements InputGetter, CommandRunner
{	
	public EditMap(String tileset, String zFog)
	{
		super(tileset, zFog);
	}
	@Override
	public void runCommand(String[] params)
	{
		AxialHexCoord3D coord = new AxialHexCoord3D(0, 0, 0);
		coord.q = Integer.valueOf(params[0]);
		coord.r = Integer.valueOf(params[1]);
		MektonHex data = new MektonHex();
		data.texturePos_.x = 1;
		data.type = MektonHex.TerrainType.solid;
		setHex(coord, data);
	}

	@Override
	public void onMouseClick(int mX, int mY, int button)
	{
		if (button == 2) // Right click
		{
			AxialHexCoord coord = new AxialHexCoord(0, 0);
			coord = coord.fromPixel(new Point2D(mX, mY).add(getLastCameraPos()));
			
			GameInfo.setCommand("@" + this.getId() + " " + coord.q + " " + coord.r);
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyRelease(int code)
	{
		// TODO Auto-generated method stub
		
	}
}
