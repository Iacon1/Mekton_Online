// By Iacon1
// Created 09/11/2021
// An entity that occupies multiple hexes, where hX_, hY_, and hZ_ are the center

package Modules.HexUtilities;

import Modules.HexUtilities.HexStructures.HexCoord;

public abstract class MetaHexEntity<T extends HexCoord> extends HexEntity<T>
{
	private int radius_;
	
	public void setRadius(int radius)
	{
		radius_ = radius;
	}
	
	@Override
	public boolean isPresentAt(T pos)
	{
		return pos_.withinDistance(radius_).contains(pos);
	}
}
