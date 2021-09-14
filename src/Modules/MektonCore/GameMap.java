// By
// Iacon1
// Created 04/25/2021
// Notes:
// If you are at z = 1 then you are *above* on the tiles at z = 0, not *in* them
// Level 0 should be a ground level, then; Comprised entirely of ground tiles that you cannot walk through

package Modules.MektonCore;

import java.util.function.Supplier;

import Modules.HexUtilities.HexCamera;
import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexData;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.HexUtilities.HexStructures.Axial.AxialHexMapRectangle;
import Modules.HexUtilities.HexStructures.HexMap;
import Modules.HexUtilities.HexStructures.Axial.AxialHex3DMap;
import GameEngine.ScreenCanvas;
import GameEngine.Point2D;
import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.SpriteEntity;
import GameEngine.Managers.GraphicsManager;

public class GameMap<T extends HexData> extends GameEntity implements HexMap<AxialHexCoord3D, T>
{	
	private String tileset_; // Tileset
	private String zFog_; // A translucent image the same size as the screen that renders between Z-levels
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
			if (obj.getHexPos() == coord) return obj;
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
	
	private void drawZFog(ScreenCanvas canvas, int hexWidth, int hexHeight)
	{
		canvas.drawImageScaled(GraphicsManager.getImage(zFog_), new Point2D(0, 0), new Point2D(0, 0), new Point2D(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight()));
	}
	private void drawHexes(ScreenCanvas canvas, Point2D camera, int k, int hexWidth, int hexHeight)
	{
		if (k >= map_.getLevels()) return; // Cannot draw hexes above this
		// TODO optimization using BakingCanvas
		for (int i = 0; i < map_.getColumns(); ++i) // columns
			for (int j = map_.firstRow(i); j <= map_.lastRow(i); ++j)
			{
				AxialHexCoord3D hexCoord = new AxialHexCoord3D(i, j, k);
				Point2D pixelCoord = hexCoord.toPixel();
				T t = getHex(hexCoord);
				
				canvas.drawImageScaled(tileset_, pixelCoord.add(camera), new Point2D(t.tX_ * hexWidth, t.tY_ * hexHeight), new Point2D(hexWidth, hexHeight));
			}
	}
	private void drawChildren(ScreenCanvas canvas, Point2D camera, int k)
	{
		for (int t = 0; t < getChildren().size(); ++t) // O(w)
		{
			HexEntity<AxialHexCoord3D> entity = (HexEntity<AxialHexCoord3D>) getChildren().get(t);
			AxialHexCoord3D pos3D = (AxialHexCoord3D) entity.getHexPos();
			if (pos3D.z_ == k) entity.render(canvas, camera);
		}
	}
	@Override
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		if (map_ == null || map_.getColumns() == 0 || map_.getRows() == 0 || map_.getLevels() == 0) return;
		int hexWidth = HexConfigManager.getHexWidth(); // Hex width
		int hexHeight = HexConfigManager.getHexHeight(); // Hex height	
		
		for (int k = 0; k < HexCamera.hZ; ++k) // O(n + n * n^2 + n * w) = O(n^3)
		{
			if (k < HexCamera.hZ - 1) drawZFog(canvas, hexWidth, hexHeight); // O(1)
			
			drawHexes(canvas, camera, k, hexWidth, hexHeight); // O(n^2)
			
			drawChildren(canvas, camera, k); // O(w)
		}
	}
	// TODO really slow
}
