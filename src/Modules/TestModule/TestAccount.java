// By Iacon1
// Created 06/17/2021
// Test account implementation

package Modules.TestModule;

import GameEngine.GameWorld;
import GameEngine.PhysicalObject;
import Server.Account;

public class TestAccount extends Account
{

	@Override
	public void runCommand(GameWorld world, String[] params)
	{
		{
			switch (params[0])
			{
			case "move": // TODO objects with MA
				PhysicalObject physObject = (PhysicalObject) world.getEntities().get(possessee);
				switch (params[1])
				{
				case "north": case "no": case "n":
					physObject.move(PhysicalObject.Direction.north, 1);
					break;
				case "northwest": case "nw":
					physObject.move(PhysicalObject.Direction.northWest, 1);
					break;
				case "northeast": case "ne":
					physObject.move(PhysicalObject.Direction.northEast, 1);
					break;
					
				case "south": case "so": case "s":
					physObject.move(PhysicalObject.Direction.south, 1);
					break;
				case "southwest": case "sw":
					physObject.move(PhysicalObject.Direction.southWest, 1);
					break;
				case "southeast": case "se":
					physObject.move(PhysicalObject.Direction.southEast, 1);
					break;
					
				case "up":
					physObject.move(PhysicalObject.Direction.up, 1);
					break;
				case "down":
					physObject.move(PhysicalObject.Direction.down, 1);
					break;
				}
				break;
			}
		}
	}

}
