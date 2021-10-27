// By Iacon1
// Created 06/05/2021
// Mekton tile

package Modules.MektonCore;

import GameEngine.Point2D;

public class MektonHex
{
	// Texture properties (tileset & size defined elsewhere, not controllable per tile)
	
	public Point2D texturePos = new Point2D(0, 0);
	
	// Game properties
	public enum TerrainType
	{
		open(1, 0), // X1 MA cost
		rough(2, 0), // X2 MA cost
		restrictive(3, 0), // X3 MA cost
		solid(-1, 0), // Cannot move through
		deepWater(-1, 0), // Deep enough to swim in
		magma(-1, 1) // Causes damage
		;
		
		private int moveCost; //-1 for impassible
		private int damage; // How many hits per round (2 seconds) this causes, applied to all locations
		private TerrainType(int moveCost, int damage)
		{
			this.moveCost = moveCost;
			this.damage = damage;
		}
		
		
	}
	public TerrainType type = TerrainType.open;
	
	public static int getCost(TerrainType type) // Gets cost of moving through for pathfinding; TODO swimming mecha and magma-meks
	{
		return type.moveCost;
	}
	
	public int getCost()
	{
		return getCost(type);
	}
}
