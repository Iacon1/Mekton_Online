// By
// Iacon1
// Created 04/25/2021
//

package Modules.MektonCore;

import java.util.function.Supplier;

import Modules.HexUtilities.HexCamera;
import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexData;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.HexUtilities.HexStructures.Axial.AxialHexMapRectangle;
import Utils.Logging;
import Modules.HexUtilities.HexStructures.HexMap;
import Modules.HexUtilities.HexStructures.Axial.AxialHex3DMap;
import GameEngine.GameCanvas;
import GameEngine.PixelCoord;
import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.SpriteEntity;
import GameEngine.Managers.GraphicsManager;

public class GameMap<T extends HexData> extends GameEntity implements HexMap<AxialHexCoord3D, T>
{	
	private String tileset_; // Tileset
	private String zFog_; // A z-fog tile, which has the same resolution as the tileset but is laid out in a repeating square grid
	private AxialHex3DMap<AxialHexMapRectangle<T>, T> map_;

	public GameMap(String tileset, String zFog)
	{
		super();
		Supplier<AxialHexMapRectangle<T>> supplier = () -> new AxialHexMapRectangle<T>();
		map_ = new AxialHex3DMap<AxialHexMapRectangle<T>, T>(supplier);
		
		tileset_ = tileset;
		zFog_ = zFog;
	}

	/** Sets the width, length, and height of the map; Clears the map!
	 * 
	 * @param columns    Length in q-axis.
	 * @param rows       Length in r-axis.
	 * @param levels     Length in z-axis.
	 * @param defaultHex Default hex data.
	 */
	public void setDimensions(int columns, int rows, int levels, T defaultHex) // Sets new dimensions for map
	{
		map_.setDimensions(columns, rows, levels);
		for (int k = 0; k < levels; ++k)
			for (int i = 0; i < columns; ++i)
				for (int j = map_.firstRow(i); j <= map_.lastRow(i); ++j)
					setHex(new AxialHexCoord3D(i, j, k), defaultHex);
	}
	/** Sets the width, length, and height of the map; Clears the map!
	 * 
	 * @param columns    Length in q-axis.
	 * @param rows       Length in r-axis.
	 * @param levels     Length in z-axis.
	 */
	public void setDimensions(int columns, int rows, int levels) // Sets new dimensions for map
	{
		setDimensions(columns, rows, levels, null);
	}
	
	
	public boolean inBounds(AxialHexCoord3D coord)
	{
		return map_.inBounds(coord);
	}
	public void setHex(AxialHexCoord3D coord, T t)
	{
		map_.setHex(coord, t);
	}
	public T getHex(AxialHexCoord3D coord)
	{
		return map_.getHex(coord);
	}
	
	public SpriteEntity findEntity(AxialHexCoord3D coord) // returns a game instance at that position if available
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
	
	private void drawZFog(GameCanvas canvas, int hexWidth, int hexHeight)
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
	private void drawHexes(GameCanvas canvas, int k, int hexWidth, int hexHeight)
	{
		for (int i = 0; i < map_.getColumns(); ++i) // columns
			for (int j = map_.firstRow(i); j <= map_.lastRow(i); ++j)
			{
				AxialHexCoord3D hexCoord = new AxialHexCoord3D(i, j, k);
				PixelCoord pixelCoord = hexCoord.toPixel();
				T t = getHex(hexCoord);
				
				canvas.drawImageScaled(tileset_, HexCamera.pX + pixelCoord.x_, HexCamera.pY + pixelCoord.y_, t.tX_ * hexWidth, t.tY_ * hexHeight, hexWidth, hexHeight);
			}
	}
	private void drawChildren(GameCanvas canvas, int k)
	{
		for (int t = 0; t < getChildren().size(); ++t) // O(w)
		{
			HexEntity<AxialHexCoord3D> entity = (HexEntity<AxialHexCoord3D>) getChildren().get(t);
			AxialHexCoord3D pos3D = (AxialHexCoord3D) entity.getPos();
			if (pos3D.z_ == k) entity.render(canvas);
		}
	}
	@Override
	public void render(GameCanvas canvas)
	{
		if (map_ == null || map_.getColumns() == 0 || map_.getRows() == 0 || map_.getLevels() == 0) return;
		int hexWidth = HexConfigManager.getHexWidth(); // Hex width
		int hexHeight = HexConfigManager.getHexHeight(); // Hex height	
		
		for (int k = 0; k < Math.min(map_.getLevels(), HexCamera.hZ); ++k) // O(n * m^2 + n * n^2 + n * w)
		{
			if (k != 0) drawZFog(canvas, hexWidth, hexHeight); // O(m^2)
			
			drawHexes(canvas, k, hexWidth, hexHeight); // O(n^2)
			
			drawChildren(canvas, k); // O(w)
		}
	}
	// TODO really slow
}
