// By Iacon1
// Created 10/27/2021
//

package Modules.Security;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import GameEngine.Point2D;
import GameEngine.Server.Account;

public abstract class RoleAccount extends Account
{
	private List<Role> roles;

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
		if (params[0] == "addRole") // addRole X Y: Adds role X to user Y
		{
			RoleAccount target = (RoleAccount) server.getAccount(params[2]);
			
			boolean hasPermission = false;
			for (int i = 0; i < target.roles.size(); ++i) // We need to check if they have any roles we can give this role to
				if (hasPermission("Add role " + params[1] + " to " + target.roles.get(i).getName()))
					hasPermission = true;
			if (hasPermission)
			{
				
			}
		}
		
		return false;
	}

}
