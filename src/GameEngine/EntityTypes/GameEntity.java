// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine.EntityTypes;

import java.util.ArrayList;
import java.util.List;

import GameEngine.ScreenCanvas;
import GameEngine.Server.Account;
import GameEngine.GameInfo;
import GameEngine.Point2D;


public abstract class GameEntity
{
	private int parentID; // Parent object index; -1 means none
	private int ourId;
	protected List<Integer> childrenIds; // Children object indices

	private int possessorID;
	
	public boolean isPossessee()
	{
		return ourId == GameInfo.getPossessee();
	}
	public int getId()
	{
		return ourId;
	}
	
	public GameEntity()
	{
		if (GameInfo.getWorld() != null) ourId = GameInfo.getWorld().addEntity(this);
		// This seems dumb, but note if it's ever null then it will likely be replaced by a new world that already contains us
		parentID = -1;
		possessorID = -1;
		childrenIds = new ArrayList<Integer>();
	}
	
	public static GameEntity getEntity(int id)
	{
		return GameInfo.getWorld().getEntity(id);
	}
	
	public GameEntity getParent() // Gets parent object; Returns null if none
	{
		if (parentID == -1) return null;
		else return getEntity(parentID);
	}
	
	public void removeChild(GameEntity child, boolean recurse)
	{
		child.parentID = -1;
		if (recurse) child.clearChildren(true);
		this.childrenIds.remove(Integer.valueOf(child.getId()));
	}
	public void clearChildren(boolean recurse)
	{
		for (int i = childrenIds.size() - 1; i >= 0; --i) removeChild(getEntity(i), recurse);
	}
	public void addChild(GameEntity child) // Adds a new child, replacing its old parent if needed
	{
		if (child.parentID != -1)
			child.getParent().removeChild(child, false);
		child.parentID = this.getId();
		childrenIds.add(child.getId());
	}
	public GameEntity getChild(int i) // Gets child #i
	{
		return getEntity(childrenIds.get(i));
	}
	public List<GameEntity> getChildren()
	{
		List<GameEntity> children = new ArrayList<GameEntity>();
		for (int i = 0; i < childrenIds.size(); ++i)
		{
			children.add(getEntity(childrenIds.get(i)));
		}
		
		return children;
	}

	/** Sets the possessorID to a specific account ID or -1 if none.
	 * 
	 * @param possessorID ID of the possessing account.
	 * 
	 * @return None.
	 */
	public void setPossessor(int possessorID)
	{
		this.possessorID = possessorID;
	}
	public Account getPossessor()
	{
		if (possessorID == -1) return null;
		else return GameInfo.getAccount(possessorID);
	}
	public abstract String getName(); // Gets object name
	public abstract void update(); // Updates regularly
	/**
	* Draws to canvas.
	* <p>
	* 
	* @param  canvas Canvas to draw to.
	*/
	public abstract void render(ScreenCanvas canvas, Point2D camera);
}
