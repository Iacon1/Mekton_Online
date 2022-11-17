// By Iacon1
// Created 04/22/2021
// Objects in the game state (not character sheets!)

package GameEngine.EntityTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GameEngine.Graphics.Camera;
import GameEngine.Graphics.ScreenCanvas;
import GameEngine.Server.Account;
import GameEngine.GameInfo;
import GameEngine.EntityTypes.Behaviors.Behavior;

public abstract class GameEntity //extends CRMHolder<GameEntity, Behavior>
{
	private int parentId; // Parent object index; -1 means none
	private int ourId;
	protected List<Integer> childrenIds; // Children object indices
	protected Map<Class<? extends Behavior>, Behavior> behaviors; // Behaviors.
	
	private int possessorID;
	
	public static GameEntity getEntity(int id)
	{
		return GameInfo.getWorld().getEntity(id);
	}

	public GameEntity()
	{
		if (GameInfo.getWorld() != null) ourId = GameInfo.getWorld().addEntity(this);
		// This seems dumb, but note if it's ever null then it will likely be replaced by a new world that already contains us
		parentId = -1;
		possessorID = -1;
		childrenIds = new ArrayList<Integer>();
		behaviors = new HashMap<Class<? extends Behavior>, Behavior>();
	}
	
	public String getName() {return this.getClass().getName() + " (ID " + ourId + ")";}

	
	public boolean isPossessee() {return ourId == GameInfo.getPossessee();}
	public int getId() {return ourId;}		

//	protected Map<?, Behavior> getMap() {return behaviors;}
	
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
	public int getPossessorId()
	{
		return possessorID;
	}


	public void addBehavior(Behavior behavior, boolean overrideSuperclass)
	{
		behaviors.put(behavior.getClass(), behavior);
		if (overrideSuperclass)
		{
			Class<?> c = behavior.getClass().getSuperclass();
			while (c != Behavior.class)
			{
				behaviors.put((Class<? extends Behavior>) c, behavior);
				c = c.getSuperclass();
			}
		}
		behavior.setParent(this);
	}
	public void addBehavior(Behavior behavior)
	{
		addBehavior(behavior, true);
	}
	public <B extends Behavior> B getBehavior(Class<B> behaviorClass)
	{
		Behavior b = behaviors.get(behaviorClass);
		if (b != null && behaviorClass.isAssignableFrom(b.getClass())) return (B) b;
		else return null;
	}

	/** Updates the object.
	 * 
	 */
	public void update() {for (Behavior behavior : behaviors.values()) behavior.update();}
	/** Draws to canvas.
	 * 
	 * @param  canvas Canvas to draw to.
	 */
	public void render(ScreenCanvas canvas, Camera camera) {for (Behavior behavior : behaviors.values()) behavior.render(canvas, camera);}
}
