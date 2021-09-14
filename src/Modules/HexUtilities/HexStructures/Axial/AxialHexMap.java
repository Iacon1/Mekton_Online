// By Iacon1
// Created 09/13/2021
// Maps an AxialHexCoord to a T
// https://www.redblobgames.com/grids/hexagons/#map-storage

package Modules.HexUtilities.HexStructures.Axial;

import java.util.ArrayList;

import Modules.HexUtilities.HexStructures.HexMap;

public abstract class AxialHexMap<T> implements HexMap<AxialHexCoord, T>
{
	private ArrayList<ArrayList<T>> hexes_;
	
	private int columns_; // Size of array
	private int rows_;
	
	/** Sets the columns & rows of the map; Clears the map!
	 * 
	 * @param columns Length in q-axis.
	 * @param rows Length in r-axis.
	 */
	public void setDimensions(int columns, int rows) // Sets new dimensions for map
	{
		columns_ = columns;
		rows_ = rows;
		
		hexes_ = new ArrayList<ArrayList<T>>();
		for (int i = 0; i < columns; ++i)
		{
			hexes_.add(new ArrayList<T>());
			for (int j = 0; j < rows; ++j)
			{
				hexes_.get(i).add(null); // !
			}
		}
	}
	public int getColumns()
	{
		return columns_;
	}
	public int getRows()
	{
		return columns_;
	}

	/** Returns the first valid r-value for a given q-value
	 * 
	 * @param q Column we're looking at.
	 */
	public abstract int firstRow(int q);	
	/** Returns the last valid r-value for a given q-value.
	 * 
	 *  @param q Column we're looking at.
	 */
	public int lastRow(int q) {return (rows_ - 1) + firstRow(q);}
	
	public boolean inBounds(AxialHexCoord coord)
	{
		if (coord.r_ < firstRow(coord.q_) || coord.r_ > lastRow(coord.q_)) // OOB r
			return false;
		else if (coord.q_ < 0 || coord.q_ >= columns_) // OOB q
			return false;
		else return true;
	}
	public void setHex(AxialHexCoord coord, T t)
	{
		if (!inBounds(coord)) return;
		else
		{
			int r = coord.r_ - firstRow(coord.q_);
			hexes_.get(coord.q_).set(r, t);
		}
	}
	public T getHex(AxialHexCoord coord)
	{
		if (!inBounds(coord)) return null;
		else
		{
			int r = coord.r_ - firstRow(coord.q_);
			return hexes_.get(coord.q_).get(r);
		}
	}
}
