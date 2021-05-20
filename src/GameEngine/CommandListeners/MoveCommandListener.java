// By Iacon1
// Created 05/09/2021
//

package GameEngine.CommandListeners;

import GameEngine.GameEntity;
import GameEngine.PhysicalObject;

public class MoveCommandListener extends CommandListener
{
	public MoveCommandListener() {super();}
	@Override
	public boolean runCommand(GameEntity source, String[] params)
	{
		PhysicalObject sourceP = (PhysicalObject) source;
		if (params[0].equals("move")) switch (params[1])
		{
		case "down":
			sourceP.move(PhysicalObject.Direction.down, 1); return true;
		case "up":
			sourceP.move(PhysicalObject.Direction.up, 1); return true;
		case "north": case "no": case "n":
			sourceP.move(PhysicalObject.Direction.north, 1); return true;
		case "northeast": case "ne":
			sourceP.move(PhysicalObject.Direction.northEast, 1); return true;
		case "northwest": case "nw":
			sourceP.move(PhysicalObject.Direction.northWest, 1); return true;
		case "south": case "so": case "s":
			sourceP.move(PhysicalObject.Direction.south, 1); return true;
		case "southeast": case "se":
			sourceP.move(PhysicalObject.Direction.southEast, 1); return true;
		case "southwest": case "sw":
			sourceP.move(PhysicalObject.Direction.southWest, 1); return true;
		default: return false;
		}
		else return false;
	}
}
