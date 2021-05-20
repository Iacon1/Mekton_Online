// By Iacon1
// Created 05/10/2021
// Account data

package Server;

import GameEngine.CommandRunner;
import GameEngine.GameWorld;
import GameEngine.PhysicalObject;

public class Account implements CommandRunner
{
	public String username;
	public int possessee; // Entity our user is possessing
	
	private int hash;
	
	public Account()
	{
		username = null;
		possessee = -1;
		hash = -1;
	}
	
	public void setHash(String password) // Sets the hash
	{
		hash = password.hashCode(); // More secure probably possible
	}
	public boolean eqHash(String password) // Is the given password equal to our hash?
	{
		return password.hashCode() == hash;
	}
	
	@Override
	public void runCommand(String[] params) // Commands that a player can type
	{
		switch (params[0])
		{
		case "move": // TODO objects with MA
			PhysicalObject physObject = (PhysicalObject) GameWorld.getWorld().getEntities().get(possessee);
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
