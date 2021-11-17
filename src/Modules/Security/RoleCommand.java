// By Iacon1
// Created 11/17/2021
//

package Modules.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Modules.BaseModule.Commands.ParsingCommand;

public class RoleCommand extends ParsingCommand
{
	private List<Role> permittedRoles;
	
	public RoleCommand(String[] names, String usage, String[][] parameters, String[][] flags,
			Role[] permittedRoles, CommandFunction commandFunction)
	{
		super(names, usage, parameters, flags, commandFunction);
		this.permittedRoles = new ArrayList<Role>();
		
		for (int i = 0; i < permittedRoles.length; ++i) this.permittedRoles.add(permittedRoles[i]); 
	}

	private boolean sharesRoles(RoleHolder holder) // Whether the holder shares any roles with us
	{
		boolean match = false;
		
		for (int i = 0; i < permittedRoles.size(); ++i)
		{
			if (holder.hasRole(permittedRoles.get(i)))
			{
				match = true;
				break;
			}
		}
		
		return match;
	}
	
	@Override
	public void execute(Object caller, String[] words) throws MissingRoleException, Exception
	{	
		if (RoleHolder.class.isAssignableFrom(caller.getClass()))
		{
			if (sharesRoles((RoleHolder) caller)) super.execute(caller, words);
			else throw new MissingRoleException(this.getNames().get(0), this.permittedRoles);
		}
		else throw new MissingRoleException("Class " + caller.getClass() + " cannot hold roles.");
	}
}
