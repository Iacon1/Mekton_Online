// By
// Iacon1
// Created 04/25/2021

/* Notes:
 * If you are at z = 1 then you are *above* on the tiles at z = 0, not *in* them
 * Level 0 should be a ground level, then; Comprised entirely of ground tiles that you cannot walk through
 *
 * Make a hex null to make it air/vacuum
 */
package Modules.MektonCore;

import java.util.function.Supplier;

import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.HexUtilities.HexStructures.Axial.AxialHexMapRectangle;
import Utils.Logging;
import Modules.HexUtilities.HexStructures.HexMap;
import Modules.HexUtilities.HexStructures.Axial.AxialHex3DMap;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord;
import GameEngine.ScreenCanvas;
import GameEngine.Point2D;
import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.SpriteEntity;
import GameEngine.Managers.GraphicsManager;

public class MektonMap extends GameEntity implements HexMap<AxialHexCoord3D, MektonHexData>
{	
	private String tileset_; // Tileset
	private String zFog_; // A translucent image the same size as the screen that renders between Z-levels
	private AxialHex3DMap<AxialHexMapRectangle<MektonHexData>, MektonHexData> map_;
	
	public MektonMap(String tileset, String zFog)
	{
		super();
		Supplier<AxialHexMapRectangle<MektonHexData>> supplier = () -> new AxialHexMapRectangle<MektonHexData>();
		map_ = new AxialHex3DMap<AxialHexMapRectangle<MektonHexData>, MektonHexData>(supplier);
		
		tileset_ = tileset;
		zFog_ = zFog;
	}
	public MektonMap()
	{
		super();
		Supplier<AxialHexMapRectangle<MektonHexData>> supplier = () -> new AxialHexMapRectangle<MektonHexData>();
		map_ = new AxialHex3DMap<AxialHexMapRectangle<MektonHexData>, MektonHexData>(supplier);
		
		tileset_ = null;
		zFog_ = null;
	}
	
	/** Sets the width, length, and height of the map; Clears the map!
	 * 
	 * @param columns    Length in q-axis.
	 * @param rows       Length in r-axis.
	 * @param levels     Length in z-axis.
	 * @param defaultHex Default hex data.
	 */
	public void setDimensions(int columns, int rows, int levels, MektonHexData defaultHex) // Sets new dimensions for map
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
	public void setHex(AxialHexCoord3D coord, MektonHexData hex)
	{
		map_.setHex(coord, hex);
	}
	public MektonHexData getHex(AxialHexCoord3D coord)
	{
		return map_.getHex(coord);
	}
	
	/** Finds the highest hex at a position below a certain threshold, if one exists, and
	 *  returns its z-value (or -1 if none found).
	 * 
	 *  @param coord Coordinate to look at.
	 *  @param zMax  Maximum altitude to check.
	 */
	public int findHighestHex(AxialHexCoord coord, int zMax) // O(z); TODO memoize
	{
		if (zMax >= map_.getLevels()) zMax = map_.getLevels() - 1;
		if (!map_.inBounds(new AxialHexCoord3D(coord.q_, coord.r_, zMax))) return -1;
		
		for (int k = zMax; k >= 0; --k)
		{
			AxialHexCoord3D coord3D = new AxialHexCoord3D(coord.q_, coord.r_, k);
			if (map_.getHex(coord3D) != null) return k;
		}
		
		return -1;
	}
	
	public SpriteEntity findEntity(AxialHexCoord3D coord) // returns a game instance at that position if available
	{
		for (int i = 0; i < childrenIds_.size(); ++i)
		{
			HexEntity obj = (HexEntity) getChild(i); // Please only put physical objects in here
			if (obj.getHexPos().equals(coord)) return obj;
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
	private void drawHexes(ScreenCanvas canvas, Point2D camera, int k, int cameraZ, int hexWidth, int hexHeight)
	{
		if (k >= map_.getLevels()) return; // Cannot draw hexes above this
		// TODO optimization using BakingCanvas
		for (int i = 0; i < map_.getColumns(); ++i) // columns
			for (int j = map_.firstRow(i); j <= map_.lastRow(i); ++j)
			{
				if (k != findHighestHex(new AxialHexCoord(i, j), cameraZ)) continue;
				
				AxialHexCoord3D hexCoord = new AxialHexCoord3D(i, j, k);
				Point2D pixelCoord = hexCoord.toPixel();
				MektonHexData hex = getHex(hexCoord);
				if (hex.texturePos_.x_ != 0)
					Logging.logNotice("special");
				canvas.drawImageScaled(tileset_, pixelCoord.subtract(camera), new Point2D(hex.texturePos_.x_ * hexWidth, hex.texturePos_.y_ * hexHeight), new Point2D(hexWidth, hexHeight));
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
	public void render(ScreenCanvas canvas, Point2D camera, int z)
	{
		if (map_ == null || map_.getColumns() == 0 || map_.getRows() == 0 || map_.getLevels() == 0) return;
		int hexWidth = HexConfigManager.getHexWidth(); // Hex width
		int hexHeight = HexConfigManager.getHexHeight(); // Hex height	
		
		for (int k = 0; k <= z; ++k) // O(n + n * n^2 + n * w) = O(n^3)
		{
			if (k < z - 1) drawZFog(canvas, hexWidth, hexHeight); // O(1)
			
			drawHexes(canvas, camera, k, z, hexWidth, hexHeight); // O(n^2)
			
			drawChildren(canvas, camera, k); // O(w)
		}
	}
	// TODO really slow
	@Override
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		render(canvas, camera, 0);
	}
}
