// By Iacon1
// Created 09/13/2021
// Hex math for a specific type

package Modules.HexUtilities;

import java.util.ArrayList;

import Utils.Instancer;
import GameEngine.PixelCoord;
import Modules.HexUtilities.HexStructures.HexCoord;

public class HexMath
{
	/** Adds delta's individual coords to coord's and returns the result.
	 * 
	 * @param coord Starting coord.
	 * @param delta Coord to add.
	 */
	public static <T extends HexCoord> T rAdd(HexCoord coord, HexCoord delta)
	{
		return coord.rAdd(delta);
	}
	
	/** Multiplies our individual coords by factor and returns the result.
	 * 
	 * @param coord  Starting coord.
	 * @param factor Factor to multiply by.
	 */
	public static <T extends HexCoord> T rMultiply(HexCoord coord, int factor)
	{
		return coord.rMultiply(factor);
	}
	/** Gets the unit vector for a particular direction.
	 *  
	 * @param hexClass Type of coordinate to use.
	 * @param dir      Direction to get.
	 */
	public static <T extends HexCoord> T getUnitVector(Class<T> hexClass, HexDirection dir)
	{return new Instancer<T>(hexClass).getInstance().getUnitVector(dir);}
	
	/** Gets source's neighbor in a particular direction.
	 *  
	 * @param source Source to point out from.
	 * @param dir    Direction to get.
	 */
	public static <T extends HexCoord> T getNeighbor(HexCoord source, HexDirection dir) {return source.getNeighbor(dir);}

	/** Gets the distance to the target.
	 * 
	 * @param start  Starting point.
	 * @param target Coord to measure to.
	 */
	public static int distance(HexCoord start, HexCoord target) {return start.distance(target);}
	
	/** Gets a straight line to the target.
	 *  
	 * @param start  Starting point.
	 * @param target Coord to go to.
	 */
	public static <T extends HexCoord> ArrayList<T> straightLine(HexCoord start, HexCoord target) {return start.straightLine(target);}

	/** Gets all hexes within a certain distance of us.
	 *
	 * @param center Coordinate to center around.
	 * @param r      Radius.
	 * @return 
	 */
	public static <T extends HexCoord> ArrayList<T> withinDistance(HexCoord center, int r) {return center.withinDistance(r);}
	
	/** Converts to a pixel coordinate.
	 * 
	 * @param coord Coordinate to convert
	 */
	public static PixelCoord toPixel(HexCoord coord) {return coord.toPixel();}
}
