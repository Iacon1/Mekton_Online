// By Iacon1
// Created 05/10/2021
// Account data

package GameEngine;

import javax.swing.JPanel;

import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.GameEntity;

public abstract class Account implements CommandRunner
{
	public String username;
	public int possessee; // Entity our user is possessing
	
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
	public GameEntity getPossessee()
	{
		return GameInfo.getWorld().getEntity(possessee);
	}
	
	public void setLogged(boolean logged)
	{
		this.loggedIn = loggedIn;
	}
	
	public abstract JPanel serverPanel();
	
	public abstract Point2D getCamera();
	
	@Override
	public abstract void runCommand(String[] params); // Commands that a player can type
}
