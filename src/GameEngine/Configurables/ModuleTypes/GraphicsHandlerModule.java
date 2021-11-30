// By Iacon1
// Created 09/10/2021
// In charge of calling any neccessary draws to the canvas

package GameEngine.Configurables.ModuleTypes;

import GameEngine.ScreenCanvas;

public interface GraphicsHandlerModule extends Module
{
	/** Draws the game world. 
	 *  @param canvas The screen canvas to draw to.
	 */
	public void drawWorld(ScreenCanvas canvas); // Draws the world
}
