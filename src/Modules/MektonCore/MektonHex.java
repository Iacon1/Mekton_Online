// By Iacon1
// Created 06/05/2021
// Mekton tile

package Modules.MektonCore;

import java.util.HashMap;

import GameEngine.Point2D;

public class MektonHex
{
	// Texture properties (tileset & size defined elsewhere, not controllable per tile)
	
	public Point2D texturePos_ = new Point2D(0, 0);
	
	// Game properties
	public enum TerrainType
	{
		open, // X1 MA cost
		rough, // X2 MA cost
		restrictive, // X3 MA cost
		solid, // X-Inf MA cost (cannot move through)
		deepWater, // Deep enough to swim in
		magma,
	}
	public TerrainType type = TerrainType.open;
	
	public static int getCost(TerrainType type) // Gets cost of moving through for pathfinding; TODO swimming mecha and magma-meks
	{
		switch (type)
		{
		case open: return 1;
		case rough: return 2;
		case restrictive: return 3;
		case solid: return -1;
		case deepWater: return -1;
		case magma: return -1;
		default: return 999;
		}
	}
	
	public int getCost()
	{
		return getCost(type);
	}
}
