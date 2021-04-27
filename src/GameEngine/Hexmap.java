// By Iacon1
// Created 04/25/2021
//

package GameEngine;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Utils.MiscUtils;

public class Hexmap extends GameInstance
{
	private static class HexData // Stores data for the hexes
	{
		public boolean solid_; // Is this a wall?
		public String imagePath_ = "Resources/Server Packs/Default/Tileset.PNG"; // Image path
		public int tX_ = 0; // Tileset X offset in hexes
		public int tY_ = 0; // Tileset Y offset in hexes
	}
	
	ArrayList<ArrayList<ArrayList<HexData>>> hexes_; // A set of rows (x/width) of columns (y/length) of pillars (z/height)
	
	public Hexmap()
	{
		super();
		hexes_ = new ArrayList<ArrayList<ArrayList<HexData>>>();
		addChild(new DummyInstance());
		GameInstance dummy = children_.get(0);
		dummy.x_ = 9;
		dummy.y_ = 4;
		dummy.z_ = 0;
		children_.set(0, dummy);
	}

	public void setDimensions(int x, int y, int z) // Sets new dimensions for map
	{
		hexes_ = MiscUtils.resizeArrayList(hexes_, x);
		for (int i = 0; i < x; ++i)
		{
			if (hexes_.get(i) == null)
				hexes_.set(i, new ArrayList<ArrayList<HexData>>());
			ArrayList<ArrayList<HexData>> xSlice = hexes_.get(i); // A slice along the x axis; Only has y & z axes
			xSlice = MiscUtils.resizeArrayList(xSlice, y);
			
			for (int j = 0; j < y; ++j)
			{
				if (xSlice.get(j) == null)
					xSlice.set(j, new ArrayList<HexData>());
				ArrayList<HexData> ySlice = xSlice.get(j); // A slice along the y axis; Only has z axis
				ySlice = MiscUtils.resizeArrayList(ySlice, z);
				
				for (int k = 0; k < z; ++k)
					ySlice.set(k, new HexData());
				
				xSlice.set(j, ySlice);
			}
			
			hexes_.set(i, xSlice);
		}
	}
	public int getMapWidth() // Get map size in x-direction
	{
		return hexes_.size();
	}	
	public int getMapLength() // Get map size in y-direction
	{
		return hexes_.get(0).size();
	}
	public int getMapHeight() // Get map size in z-direction
	{
		return hexes_.get(0).get(0).size();
	}
	
	public void setHex(int x, int y, int z, HexData data) // Sets the hex data at a coordinate
	{
		ArrayList<ArrayList<HexData>> xSlice = hexes_.get(x); // A slice along the x axis; Only has y & z axes
		ArrayList<HexData> ySlice = xSlice.get(y); // A slice along the y axis; Only has z axis
		HexData zSlice = ySlice.get(z); // A slice along the x axis; Only has y & z axes
		
		zSlice = data;
		ySlice.set(z, zSlice);
		xSlice.set(y,  ySlice);
		hexes_.set(x,  xSlice);
	}
	public HexData getHex(int x, int y, int z) // Gets the hex data @ (x, y, z)
	{
		ArrayList<ArrayList<HexData>> xSlice = hexes_.get(x); // A slice along the x axis; Only has y & z axes
		ArrayList<HexData> ySlice = xSlice.get(y); // A slice along the y axis; Only has z axis
		HexData zSlice = ySlice.get(z); // A slice along the x axis; Only has y & z axes
		
		return zSlice;
	}
	public GameInstance findEntity(int x, int y, int z) // returns a game instance at that position if available
	{
		for (int i = 0; i < children_.size(); ++i)
		{
			GameInstance obj = children_.get(i);
			if (obj.x_ == x && obj.y_ == y && obj.z_ == z)
				return obj;
		}
		
		return null;
	}
	private int GX2SX(int gX, int hexSize) // gX relative to corner-of-screen
	{
		return (int) Math.round(gX * 0.5 * hexSize);
	}
	private int GY2SY(int gY, int hexSize, boolean shift) // gY relative to corner-of-screen
	{
		if (shift)
			return (int) Math.round(gY * 1.5 * hexSize + 0.75 * hexSize);
		else
			return (int) Math.round(gY * 1.5 * hexSize);
	}
	
	public void render(int pX, int pY, Graphics2D g) // Draws starting @ x, y as a left corner @ z to g
	{
		int x = 0;
		int y = 0;
		int z = 1;
		int hexSize = ConfigManager.getHexSize(); // Hex width & height
		int w = ConfigManager.getScreenWidth() / hexSize;
		int h = ConfigManager.getScreenHeight() / hexSize;
		
		boolean shift = false; // Shift down on y-axis by half-column?
		
		for (int i = x; i < Math.min(getMapWidth(), x + w); ++i)
		{
			for (int j = y; j < Math.min(getMapLength(), y + h); ++j)
			{
				for (int k = 0; k < z; ++k)
				{
					HexData hex = getHex(i, j, k);
					int cX = GX2SX(i - x, hexSize);
					int cY = GY2SY(j - y, hexSize, shift);
					int cTX = hex.tX_ * hexSize;
					int cTY = hex.tY_ * hexSize;
					
					g.drawImage(GraphicsManager.getImage(hex.imagePath_), pX + cX, pY + cY, pX + cX + hexSize, pY + cY + hexSize, cTX, cTY, cTX + hexSize, cTY + hexSize, null);
					GameInstance instance = findEntity(i, j, k);
					if (instance != null)
						instance.render(pX + cX, pY + cY, g);
				}
			}
			shift = !shift;
		}
	}
}
