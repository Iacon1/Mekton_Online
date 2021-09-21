// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine.EntityTypes;

import java.util.ArrayList;

import GameEngine.ScreenCanvas;
import GameEngine.GameInfo;
import GameEngine.Point2D;


public abstract class GameEntity
{
	private int parentId; // Parent object index; -1 means none
	private int ourId;
	protected ArrayList<Integer> childrenIds; // Children object indices

	private String owner; // Owner account
	
	private Point2D lastCameraPos; // Last camera position
	
	public int getId()
	{
		return ourId;
	}
	
	public GameEntity()
	{
		if (GameInfo.getWorld() != null) ourId = GameInfo.getWorld().addEntity(this);
		// This seems dumb, but note if it's ever null then it will likely be replaced by a new world that already contains us
		parentId = -1;
		owner = null;
		childrenIds = new ArrayList<Integer>();
	}
	public GameEntity(String owner)
	{
		if (GameInfo.getWorld() != null) ourId = GameInfo.getWorld().addEntity(this);
		// This seems dumb, but note if it's ever null then it will likely be replaced by a new world that already contains us
		parentId = -1;
		this.owner = owner;
		childrenIds = new ArrayList<Integer>();
	}
	
	public static GameEntity getEntity(int id)
	{
		return GameInfo.getWorld().getEntity(id);
	}
	
	public GameEntity getParent() // Gets parent object; Returns null if none
	{
		if (parentId == -1) return null;
		else return getEntity(parentId);
	}
	
	public void removeChild(GameEntity child, boolean recurse)
	{
		child.parentId = -1;
		if (recurse) child.clearChildren(true);
		this.childrenIds.remove(Integer.valueOf(child.getId()));
	}
	public void clearChildren(boolean recurse)
	{
		for (int i = childrenIds.size() - 1; i >= 0; --i) removeChild(getEntity(i), recurse);
	}
	public void addChild(GameEntity child) // Adds a new child, replacing its old parent if needed
	{
		if (child.parentId != -1)
			child.getParent().removeChild(child, false);
		child.parentId = this.getId();
		childrenIds.add(child.getId());
	}
	public GameEntity getChild(int i) // Gets child #i
	{
		return getEntity(childrenIds.get(i));
	}
	public ArrayList<GameEntity> getChildren()
	{
		ArrayList<GameEntity> children = new ArrayList<GameEntity>();
		for (int i = 0; i < childrenIds.size(); ++i)
		{
			children.add(getEntity(childrenIds.get(i)));
		}
		
		return children;
	}

	/** Sets the owner to a specific user, or no owner if
	 *  null.
	 * 
	 * @param owner Owner to set to, or null.
	 * 
	 * @return None.
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	public void clearOwner()
	{
		setOwner(null);
	}
	public String getOwner()
	{
		return owner;
	}
	public boolean isOwner(String name)
	{
		return owner.equals(name);
	}
	
	/** Returns the last camera position known.
	 * 
	 */
	public Point2D getLastCameraPos()
	{
		return lastCameraPos.clone();
	}
	
	public abstract String getName(); // Gets object name
	public abstract void update(); // Updates regularly
	/**
	* Draws to canvas.
	* <p>
	* 
	* @param  canvas Canvas to draw to.
	*/
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		lastCameraPos = camera.clone() ;
	}
}
