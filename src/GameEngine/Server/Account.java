// By Iacon1
// Created 05/10/2021
// Account data

package GameEngine.Server;

import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import GameEngine.GameInfo;
import GameEngine.Point2D;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.EntityTypes.GUITypes.GUISpriteEntity;

public abstract class Account implements CommandRunner
{
	public int id;
	public String username;
	private int possessee; // Entity our user is possessing
	
	protected boolean loggedIn; // Am I logged in?
	private int hash;

	public Account()
	{
		username = null;
		possessee = -1;
		hash = -1;
	}
	
	public void setHash(String password) // Sets the hash
	{
		hash = password.hashCode(); // More secure probably possible
	}
	public boolean eqHash(String password) // Is the given password equal to our hash?
	{
		return (password.hashCode() == hash);
	}
	public void possess(int id)
	{
//		entity.setOwner(username);
		possessee = id;
	}
	public void possess(GameEntity entity)
	{
		possess(entity.getId());
		entity.setPossessor(getID());
	}
	public boolean hasPossessee()
	{
		return possessee != -1;
	}
	public GameEntity getPossessee()
	{
		return GameInfo.getWorld().getEntity(possessee);
	}
	
	public void setLogged(boolean logged)
	{
		this.loggedIn = logged;
	}
	
	public abstract JPanel serverPanel();
	
	public abstract Point2D getCamera();

	public int getID()
	{
		return id;
	}
	
	@Override
	public boolean runCommand(String... words)
	{
		if (GUISpriteEntity.GUICommandHeader.equals(words[0]))
		{
			List<GUISpriteEntity> GUIEntities = GameInfo.getWorld().getEntitiesOfType(GUISpriteEntity.class);
			
			for (int i = 0; i < GUIEntities.size(); ++i)
				if (GUIEntities.get(i).getOwnerID() == this.id) GUIEntities.get(i).runCommand(words);
			return true;
		}
		
		return false;
	}
}
