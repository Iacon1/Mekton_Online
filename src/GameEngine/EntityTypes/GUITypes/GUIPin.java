package GameEngine.EntityTypes.GUITypes;

import GameEngine.GameCanvas;
import GameEngine.GameInfo;
import GameEngine.EntityTypes.GameEntity;
import Server.Account;

public final class GUIPin extends GameEntity
{
	private Account owner_;
	
	@Override
	public String getName()
	{
		return "GUI Pin: " + owner_.username;
	}

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
	public void render(GameCanvas canvas)
	{
		for (int i = 0; i < getChildren().size(); ++i) getChildren().get(i).render(canvas);
	}
}
