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
				HexEntity hexObject = (HexEntity) GameInfo.getWorld().getEntities().get(possessee);
				if (hexObject.getSpeed() != 0) return;
				switch (params[1])
				{
				case "north": case "no": case "n":
					hexObject.moveDirectional(HexDirection.north, 1, 2);
					break;
				case "northwest": case "nw":
					hexObject.moveDirectional(HexDirection.northWest, 1, 2);
					break;
				case "northeast": case "ne":
					hexObject.moveDirectional(HexDirection.northEast, 1, 2);
					break;
					
				case "south": case "so": case "s":
					hexObject.moveDirectional(HexDirection.south, 1, 2);
					break;
				case "southwest": case "sw":
					hexObject.moveDirectional(HexDirection.southWest, 1, 2);
					break;
				case "southeast": case "se":
					hexObject.moveDirectional(HexDirection.southEast, 1, 2);
					break;
					
				case "up":
					hexObject.moveDirectional(HexDirection.up, 1, 2);
					break;
				case "down": case "do":
					hexObject.moveDirectional(HexDirection.down, 1, 2);
					break;
				}
				break;
			}
		}
	}

}
