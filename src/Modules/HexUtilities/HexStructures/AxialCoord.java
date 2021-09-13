// By Iacon1
// Created 09/12/2021
// Axial coordinates as defined by https://www.redblobgames.com/grids/hexagons/#coordinates-axial

package Modules.HexUtilities.HexStructures;

import java.util.ArrayList;

import GameEngine.PixelCoord;
import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexDirection;
import Utils.MiscUtils;

public class AxialCoord
{
	public int q_; // "Column"
	public int r_; // "Row"
	public int s() // "Side"?
	{
		return -(q_ + r_);
	}

	public AxialCoord(int q, int r)
	{
		q_ = q;
		r_ = r;
	}
	
	/** Adds a & b's q & r coords together and returns the result.
	 * 
	 * @param a Coord #1.
	 * @param b Coord #2.
	 */
	public static AxialCoord rAdd(AxialCoord a, AxialCoord b) // r stands for raw, as in raw data
	{
		return new AxialCoord(a.q_ + b.q_, a.r_ + b.r_);
	}
	/** Adds delta's q & r coords to our own and returns the result.
	 * 
	 * @param delta Coord to add.
	 */
	public AxialCoord rAdd(AxialCoord delta)
	{
		return rAdd(this, delta);
	}
	public AxialCoord multiply(int factor)
	{
		return new AxialCoord(this.q_ * factor, this.r_ * factor);
	}
	/** gets the unit vector for a particular direction.
	 *  
	 * @param dir Direction to get.
	 */
	public static AxialCoord getUnitVector(HexDirection dir) // https://www.redblobgames.com/grids/hexagons/#neighbors-axial
	{
		switch (dir)
		{
		case north: return new AxialCoord(0, -1);
		case northWest: return new AxialCoord(-1, 0);
		case northEast: return new AxialCoord(1, -1);
		
		case south: return new AxialCoord(0, 1);
		case southWest: return new AxialCoord(-1, 1);
		case southEast: return new AxialCoord(1, 0);
		default: return null;
		}
	}
	
	/** gets the neighbor of coord in a particular direction.
	 *  
	 * @param coord Starting coordinate.
	 * @param dir   Direction to get.
	 */
	public static AxialCoord getNeighbor(AxialCoord coord, HexDirection dir) // https://www.redblobgames.com/grids/hexagons/#neighbors-axial
	{
		AxialCoord delta = getUnitVector(dir);
		return coord.rAdd(delta);
	}

	/** gets the distance between two coords.
	 *  
	 * @param a Coord #1.
	 * @param b Coord #2.
	 */
	public static int distance(AxialCoord a, AxialCoord b) // https://www.redblobgames.com/grids/hexagons/#distances-cube
	{	
		int dQ = a.q_ - a.q_;
		int dR = a.r_ - b.r_;
		int dS = a.s() - b.s();
		return MiscUtils.multiMax(dQ, dR, dS);
	}
	
	/** gets a straight line between two coords.
	 *  
	 * @param a Coord #1.
	 * @param b Coord #2.
	 */
	public static ArrayList<AxialCoord> straightLine(AxialCoord a, AxialCoord b) // https://www.redblobgames.com/grids/hexagons/#line-drawing
	{
		ArrayList<AxialCoord> line = new ArrayList<AxialCoord>();
		
		int dist = distance(a, b);
		
		float fAQ = (float) a.q_;
		float fAR = (float) a.r_;
		float fBQ = (float) b.q_;
		float fBR = (float) b.r_;
		for (int i = 0; i < dist; ++i)
		{
			float t = 1.0f / ((float) dist) * (float) i;
			int q = (int) MiscUtils.lerp(fAQ, fBQ, t);
			int r = (int) MiscUtils.lerp(fAR, fBR, t);
			
			line.add(new AxialCoord(q, r));
		}
		
		return line;
	}

	/** gets all hexes within a certain distance of coord.
	 *  
	 * @param center Center coordinate.
	 * @param r      Radius.
	 */
	public static ArrayList<AxialCoord> withinDistance(AxialCoord center, int r) // https://www.redblobgames.com/grids/hexagons/#range
	{
		ArrayList<AxialCoord> within = new ArrayList<AxialCoord>();
		
		for (int i = -r; i <= r; ++i)
			for (int j = Math.max(-r, -(i + r)); j <= Math.min(r, r - i); ++j)
			{
				AxialCoord delta = new AxialCoord(i, j);
				within.add(center.rAdd(delta));
			}
		
		return within;
	}
	
	public static PixelCoord hex2Pixel(AxialCoord coord) // https://www.redblobgames.com/grids/hexagons/#hex-to-pixel
	{
		float uX = 3.0f / 2.0f * (float) coord.q_;
		float uY = (1.0f / 2.0f * (float) coord.q_ + (float) coord.r_) * (float) Math.sqrt((double) 3.0f);
		
		return new PixelCoord((int) ((float) HexConfigManager.getHexRadius() * uX), (int) ((float) HexConfigManager.getHexRadius() * uY));
	}
}