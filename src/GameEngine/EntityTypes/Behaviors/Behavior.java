// By Iacon1
// Created 02/24/2022
// Behavior for an entity.

package GameEngine.EntityTypes.Behaviors;

import GameEngine.GameInfo;
import GameEngine.IntPoint2D;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Graphics.Camera;
import GameEngine.Graphics.ScreenCanvas;

public abstract class Behavior
{
	private int parentID;
	
	
	public void setParent(GameEntity parent)
	{
		this.parentID = parent.getId();
	}
	public GameEntity getParent()
	{
		return GameInfo.getWorld().getEntity(parentID);
	}
	
	/** Updates the object.
	 * 
	 */
	public abstract void update();

	public abstract void render(ScreenCanvas canvas, Camera camera);
}
