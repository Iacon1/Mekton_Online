// By Iacon1
// Created 10/27/2021
//

package Modules.Security;

import java.util.ArrayList;
import java.util.List;

import Modules.BaseModule.Commands.ParsingCommandAccount;

public abstract class RoleAccount extends ParsingCommandAccount implements RoleHolder
{
	private List<Role> roles;

	public RoleAccount()
	{
		super();
		roles = new ArrayList<Role>();
	}
	
	@Override
	public void addRole(Role role)
	{
		if (!hasRole(role)) roles.add(role);
	}
	@Override
	public boolean hasRole(Role role)
	{
		if (!this.roles.contains(role)) return false;
		else return true;
	}
	@Override
	public List<Role> getRoles()
	{
		return roles;
	}
	
}
