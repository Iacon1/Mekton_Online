// By Iacon1
// Created 10/27/2021
//

package Modules.Security;

import java.util.ArrayList;

import javax.swing.JPanel;

import GameEngine.Point2D;
import GameEngine.Server.Account;

public abstract class RoleAccount extends Account
{
	private ArrayList<Role> roles;

	public RoleAccount()
	{
		super();
		roles = new ArrayList<Role>();
	}
	
	public void addRole(Role role)
	{
		if (!hasRole(role)) roles.add(role);
	}
	public boolean hasRole(Role role)
	{
		if (!this.roles.contains(role)) return false;
		else return true;
	}
	
	public boolean hasPermission(String action)
	{
		for (int i = 0; i < roles.size(); ++i) if (roles.get(i).hasPermission(action)) return true;
		
		return false;
	}
	
	@Override
	public boolean runCommand(String... params)
	{
		if (params[0] == "addRole")
		{
//			RoleAccount target = params[2]; TODO add username search to account class.
//			if (hasPermission("Add role + " params[1] + " to " + params[i]))
		}
		
		return false;
	}

}
