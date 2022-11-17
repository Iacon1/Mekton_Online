// By Iacon1
// Created 09/10/2021
// In charge of calling any neccessary draws to the canvas

package GameEngine.Configurables.ModuleTypes;

import GameEngine.EntityTypes.GameEntity;
import GameEngine.Graphics.ScreenCanvas;
import GameEngine.Graphics.UtilCanvas;

public interface GraphicsHandlerModule extends Module
{
	/** Draws the game world. 
	 *  @param viewer The viewer to draw from the perspective of.
	 *  @param canvas The screen canvas to draw to.
	 */
	public void drawWorld(GameEntity viewer, ScreenCanvas canvas); // Draws the world
}
