// By Iacon1
// Created 11/17/2021
//

package Modules.Security;

import java.util.List;

import Utils.MiscUtils;

@SuppressWarnings("serial")
public class MissingRoleException extends Exception
{
	public MissingRoleException(String message)
	{
		super(message);
	}
	
	public MissingRoleException(String commandName, List<Role> role)
	{
		super("Needs one of the following roles to execute " + commandName + ": "  + MiscUtils.arrayToString(role, ", ") + ".");
	}
}
