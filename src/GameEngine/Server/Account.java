// By Iacon1
// Created 05/10/2021
// Account data

package GameEngine.Server;

import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.Queue;

import javax.swing.JPanel;

import GameEngine.GameInfo;
import GameEngine.IntPoint2D;
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
	
	public abstract IntPoint2D getCamera();

	public int getID()
	{
		return id;
	}
	
	@Override
	public String runCommand(String... words)
	{
		if (words[0].charAt(0) == '@') // Targeted
		{
			int targetID = Integer.valueOf(words[0].substring(1));
			GameEntity target = GameInfo.getWorld().getEntity(targetID);
			if (CommandRunner.class.isAssignableFrom(target.getClass())) // Can run commands
			{
				CommandRunner targetRunner = (CommandRunner) target;
				if (target.getId() == getPossessee().getId()) // Our possessee
					return targetRunner.runCommand(Arrays.copyOfRange(words, 1, words.length));
				if (GUISpriteEntity.class.isAssignableFrom(target.getClass()))
				{
					GUISpriteEntity targetGUI = (GUISpriteEntity) target;
					if (targetGUI.getOwnerID() == getID()) // An owned GUI element
						return targetRunner.runCommand(Arrays.copyOfRange(words, 1, words.length));
				}
				
			}
				
		}
		
		return null;
	}
	
	// Inputs that are not a command
	public void processOtherInputs(Queue<InputEvent> inputs)
	{
		while (!inputs.isEmpty())
		{
			InputEvent input = inputs.poll();
			for (int i = 0; i < GameInfo.getWorld().getEntities().size(); ++i)
			{
				GameInfo.getWorld().getEntity(i).handleInput(id, input);
			}
		}
	}
}
