package GameEngine.EntityTypes.GUITypes;

import GameEngine.ScreenCanvas;
import GameEngine.Account;
import GameEngine.GameInfo;
import GameEngine.Point2D;
import GameEngine.EntityTypes.GameEntity;

public final class GUIPin extends GameEntity
{
	private Account owner_;

	public GUIPin(Account owner)
	{
		super();
		owner_ = owner;
	}
	
	/**
	* Returns the GUIPin object corresponding to a user, if any exists.
	* <p>
	* 
	* @param  username user to look for.
	*/
	public static GUIPin findPin(String username)
	{
		for (int i = 0; i < GameInfo.getWorld().getEntities().size(); ++i)
		{
			GameEntity entity = GameInfo.getWorld().getEntities().get(i);
			
			if (GUIPin.class.isAssignableFrom(entity.getClass()) && ((GUIPin) entity).owner_.username == username)
			{
				return (GUIPin) entity;
			}
		}
		
		return null;
	}
	/**
	* Returns the GUIPin object corresponding to a user, if any exists.
	* <p>
	* 
	* @param  user to look for.
	*/
	public static GUIPin findPin(Account account)
	{
		return findPin(account.username);
	}
	
	@Override
	public String getName()
	{
		return "GUI Pin: " + owner_.username;
	}
	@Override
	public void update() {}
	@Override
	public void render(ScreenCanvas canvas, Point2D camera)
	{
		for (int i = 0; i < getChildren().size(); ++i) getChildren().get(i).render(canvas, camera);
	}
}
