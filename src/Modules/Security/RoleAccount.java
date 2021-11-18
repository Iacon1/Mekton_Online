// By Iacon1
// Created 10/27/2021
//

package Modules.Security;

import java.util.ArrayList;
import java.util.List;

import Modules.BaseModule.Commands.ParsingCommandAccount;

public abstract class RoleAccount extends ParsingCommandAccount implements RoleHolder
{
	private List<String> roles;

	public RoleAccount()
	{
		super();
		roles = new ArrayList<String>();
	}
	
	@Override
	public void addRole(String role)
	{
		if (!hasRole(role)) roles.add(role);
	}
	@Override
	public boolean hasRole(String role)
	{
		if (!this.roles.contains(role)) return false;
		else return true;
	}
	@Override
	public List<String> getRoles()
	{
		return roles;
	}
	
}
