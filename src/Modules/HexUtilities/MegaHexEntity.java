// By Iacon1
// Created 09/11/2021
// An entity that occupies multiple hexes, where hX_, hY_, and hZ_ are the center

package Modules.HexUtilities;

public abstract class MegaHexEntity extends HexEntity
{
	private int radius_; // In hexes
	private int height_; // In hexes
	
	public void setRadius(int radius)
	{
		radius_ = radius;
	}
	public void setHeight(int height)
	{
		height_ = height;
	}
	
	@Override
	public boolean isPresentAt(int hX, int hY, int hZ)
	{
		return false;//	return hX == hX_ && hY == hY_ && hZ == hZ_;
	}
}
