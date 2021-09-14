// By Iacon1
// Created 09/13/2021
// Hex map with Z axis

package Modules.HexUtilities.HexStructures.Axial;

import java.util.ArrayList;
import java.util.function.Supplier;

import Modules.HexUtilities.HexStructures.HexMap;

public class AxialHex3DMap<M extends AxialHexMap<T>, T> implements HexMap<AxialHexCoord3D, T>
{
	private ArrayList<M> maps_;
	private transient Supplier<M> supplier_; // Should be OK to transient
	
	private int columns_; // Size of any one map
	private int rows_; 
	private int levels_; // # of maps
	
	public AxialHex3DMap(Supplier<M> supplier)
	{
		supplier_ = supplier;
	}
	
	
	/** Sets the width, length, and height of the map; Clears the map!
	 * 
	 * @param columns Length in q-axis.
	 * @param rows    Length in r-axis.
	 * @param levels  Length in z-axis.
	 */
	public void setDimensions(int columns, int rows, int levels) // Sets new dimensions for map
	{
		columns_ = columns;
		rows_ = rows;
		levels_ = levels;
		
		maps_ = new ArrayList<M>();
		for (int k = 0; k < levels; ++k)
		{
			M m = supplier_.get();
			m.setDimensions(columns, rows);
			maps_.add(m);
		}
	}
	public int getColumns()
	{
		return columns_;
	}
	public int getRows()
	{
		return rows_;
	}
	public int getLevels()
	{
		return levels_;
	}
	
	/** Returns the first valid r-value for a given q-value
	 * 
	 * @param q Column we're looking at.
	 */
	public int firstRow(int q) {return maps_.get(0).firstRow(q);}
	/** Returns the last valid r-value for a given q-value.
	 * 
	 *  @param q Column we're looking at.
	 */
	public int lastRow(int q) {return maps_.get(0).lastRow(q);}
	
	@Override
	public boolean inBounds(AxialHexCoord3D coord)
	{
		if (0 < coord.z_ || coord.z_ >= levels_) return false;
		else return maps_.get(0).inBounds(coord);
	}
	@Override
	public void setHex(AxialHexCoord3D coord, T t)
	{
		if (!inBounds(coord)) return;
		else maps_.get(coord.z_).setHex(coord, t);
	}
	@Override
	public T getHex(AxialHexCoord3D coord)
	{
		if (!inBounds(coord)) return null;
		else return maps_.get(coord.z_).getHex(coord);
	}

}
