// By Iacon1
// Created 06/17/2021
// Test account implementation

package Modules.TestModule;

import GameEngine.GameInfo;
import Modules.MektonCore.HexEntity;
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
					physObject.moveDirectional(HexEntity.Direction.north, 1, 2);
					break;
				case "northwest": case "nw":
					physObject.moveDirectional(HexEntity.Direction.northWest, 1, 2);
					break;
				case "northeast": case "ne":
					physObject.moveDirectional(HexEntity.Direction.northEast, 1, 2);
					break;
					
				case "south": case "so": case "s":
					physObject.moveDirectional(HexEntity.Direction.south, 1, 2);
					break;
				case "southwest": case "sw":
					physObject.moveDirectional(HexEntity.Direction.southWest, 1, 2);
					break;
				case "southeast": case "se":
					physObject.moveDirectional(HexEntity.Direction.southEast, 1, 2);
					break;
					
				case "up":
					physObject.moveDirectional(HexEntity.Direction.up, 1, 2);
					break;
				case "down": case "do":
					physObject.moveDirectional(HexEntity.Direction.down, 1, 2);
					break;
				}
				break;
			}
		}
	}

}
