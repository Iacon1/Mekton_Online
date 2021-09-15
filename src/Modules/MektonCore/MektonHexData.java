// By Iacon1
// Created 06/05/2021
// Mekton tile

package Modules.MektonCore;

import GameEngine.Point2D;

public class MektonHexData
{
	// Texture properties (tileset & size defined elsewhere, not controllable per tile)
	
	public Point2D texturePos_ = new Point2D(0, 0);
	
	// Game properties
	public enum TerrainType
	{
		open_, // X1 MA cost
		rough_, // X2 MA cost
		restrictive_, // X3 MA cost
		solid_, // X-Inf MA cost (cannot move through)
		deepWater_, // Deep enough to swim in
		magma_,
	}
	public TerrainType type_ = TerrainType.open_;
}
