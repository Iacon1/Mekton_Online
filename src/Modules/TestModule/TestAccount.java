// By Iacon1
// Created 06/17/2021
// Test account implementation

package Modules.TestModule;

import GameEngine.GameInfo;
import GameEngine.SpriteEntity;
import Modules.MektonCore.HexEntity;
import Server.Account;

public class TestAccount extends Account
{

	@Override
	public void runCommand(GameInfo.GameWorld world, String[] params)
	{
		{
			switch (params[0])
			{
			case "move": // TODO objects with MA
				HexEntity physObject = (HexEntity) world.getEntities().get(possessee);
				switch (params[1])
				{
				case "north": case "no": case "n":
					physObject.move(HexEntity.Direction.north, 1);
					break;
				case "northwest": case "nw":
					physObject.move(HexEntity.Direction.northWest, 1);
					break;
				case "northeast": case "ne":
					physObject.move(HexEntity.Direction.northEast, 1);
					break;
					
				case "south": case "so": case "s":
					physObject.move(HexEntity.Direction.south, 1);
					break;
				case "southwest": case "sw":
					physObject.move(HexEntity.Direction.southWest, 1);
					break;
				case "southeast": case "se":
					physObject.move(HexEntity.Direction.southEast, 1);
					break;
					
				case "up":
					physObject.move(HexEntity.Direction.up, 1);
					break;
				case "down":
					physObject.move(HexEntity.Direction.down, 1);
					break;
				}
				break;
			}
		}
	}

}
