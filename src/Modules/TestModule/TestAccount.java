// By Iacon1
// Created 06/17/2021
// Test account implementation

package Modules.TestModule;

import GameEngine.GameInfo;
import Modules.HexUtilities.HexDirection;
import Modules.HexUtilities.HexEntity;
import Server.Account;

public class TestAccount extends Account
{

	@Override
	public void runCommand(String[] params)
	{
		{
			switch (params[0])
			{
			case "move": // TODO objects with MA
				HexEntity physObject = (HexEntity) GameInfo.getWorld().getEntities().get(possessee);
				switch (params[1])
				{
				case "north": case "no": case "n":
					physObject.moveDirectional(HexDirection.north, 2, 2);
					break;
				case "northwest": case "nw":
					physObject.moveDirectional(HexDirection.northWest, 2, 2);
					break;
				case "northeast": case "ne":
					physObject.moveDirectional(HexDirection.northEast, 2, 2);
					break;
					
				case "south": case "so": case "s":
					physObject.moveDirectional(HexDirection.south, 2, 2);
					break;
				case "southwest": case "sw":
					physObject.moveDirectional(HexDirection.southWest, 2, 2);
					break;
				case "southeast": case "se":
					physObject.moveDirectional(HexDirection.southEast, 2, 2);
					break;
					
				case "up":
					physObject.moveDirectional(HexDirection.up, 2, 2);
					break;
				case "down": case "do":
					physObject.moveDirectional(HexDirection.down, 2, 2);
					break;
				}
				break;
			}
		}
	}

}
