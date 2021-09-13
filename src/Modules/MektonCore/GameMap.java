// By
// Iacon1
// Created 04/25/2021
//

package Modules.MektonCore;

import java.util.ArrayList;
import java.util.HashMap;

import Modules.HexUtilities.HexCamera;
import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexData;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.AxialCoord;
import Modules.HexUtilities.HexStructures.AxialCoord3D;
import GameEngine.GameCanvas;
import GameEngine.PixelCoord;
import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.SpriteEntity;
import GameEngine.Managers.GraphicsManager;
import Utils.Instancer;
import Utils.Logging;
import Utils.MiscUtils;

public class GameMap<T extends HexData> extends GameEntity
{	
	private String tileset_; // Tileset
	private String zFog_; // A z-fog tile, which has the same resolution as the tileset but is laid out in a repeating square grid
	private ArrayList<ArrayList<ArrayList<T>>> hexes_; // Hex grid; q -> r -> z
	private Instancer<T> instancer_;

	public GameMap(T hexTemplate, String tileset, String zFog)
	{
		super();
		hexes_ = new ArrayList<ArrayList<ArrayList<T>>>();
		instancer_ = new Instancer<T>(hexTemplate);
		
		tileset_ = tileset;
		zFog_ = zFog;
	}

	/** Sets the width, length, and height of the map; Clears the map!
	 * 
	 * @param width Length in q-axis.
	 * @param length Length in r-axis.
	 * @param height Length in z-axis.
	 */
	public void setDimensions(int columns, int rows, int height) // Sets new dimensions for map
	{
		hexes_ = new ArrayList<ArrayList<ArrayList<T>>>();
		for (int i = 0; i < rows; ++i)
		{
			hexes_.add(new ArrayList<ArrayList<T>>());
			for (int j = 0; j < columns; ++j)
			{
				hexes_.get(i).add(new ArrayList<T>());
				for (int k = 0; k < height; ++k)
				{
					hexes_.get(i).get(j).add(instancer_.getInstance());
				}
			}
		}
	}
	
	public T getHex(AxialCoord3D coord)
	{
		return hexes_.get(coord.q_).get(coord.r_ + (int) Math.floorDiv(coord.q_, 2)).get(coord.z_);
	}
	
	public SpriteEntity findEntity(AxialCoord3D coord) // returns a game instance at that position if available
	{
		for (int i = 0; i < childrenIds_.size(); ++i)
		{
			HexEntity obj = (HexEntity) getChild(i); // Please only put physical objects in here
			if (obj.getPos() == coord) return obj;
		}
		
		return null;
	}
	
	@Override
	public String getName()
	{
		return "Map"; // TODO
	}
	@Override
	public void update() {}
	@Override
	public void render(GameCanvas canvas)
	{
		if (hexes_ == null || hexes_.size() == 0 || hexes_.get(0).size() == 0 || hexes_.get(0).get(0).size() == 0) return;
		int hexWidth = HexConfigManager.getHexWidth(); // Hex width
		int hexHeight = HexConfigManager.getHexHeight(); // Hex height	
		
		for (int k = 0; k < Math.min(hexes_.get(0).get(0).size(), HexCamera.hZ); ++k) // O(hZ) base, O(hZ * [children + ScreenW * ScreenH + mapW * mapL]) ~= O(n^3)
		{
			if (k != 0) // Draw Z-fog
			{
				for (int i = 0; i < ConfigManager.getScreenWidth(); ++i) // O(ScreenW) base, O(ScreenW * ScreenH) total
				{
					for (int j = 0; j < ConfigManager.getScreenHeight(); ++j) // O(ScreenH)
					{
						int hX = i * hexWidth;
						int hY = j * hexHeight;
						
						canvas.drawImageScaled(GraphicsManager.getImage(zFog_), HexCamera.pX + hX, HexCamera.pY + hY, 0, 0, hexWidth, hexHeight);
					}
				}
			}
			
			for (int i = 0; i < hexes_.size(); ++i) // columns
				for (int j = (int) -Math.floorDiv(i, 2); j < hexes_.get(0).size() - (int) Math.floorDiv(i, 2); ++j) // rows; x + floor ( q / 2) = -
				{
					AxialCoord3D hexCoord = new AxialCoord3D(i, j, k);
					PixelCoord pixelCoord = AxialCoord.hex2Pixel(hexCoord);
					T t = getHex(hexCoord);
					
					canvas.drawImageScaled(tileset_, HexCamera.pX + pixelCoord.x_, HexCamera.pY + pixelCoord.y_, t.tX_ * hexWidth, t.tY_ * hexHeight, hexWidth, hexHeight);
				}
			
			for (int t = 0; t < getChildren().size(); ++t) // O(children)
			{
				HexEntity entity = (HexEntity) getChildren().get(t);
				AxialCoord3D pos3D = (AxialCoord3D) entity.getPos();
				if (pos3D.z_ == k) entity.render(canvas);
			}
		}
	}
	// TODO really slow
}
