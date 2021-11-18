// By Iacon1
// Created 11/17/2021
//

package Modules.Security;

import java.util.ArrayList;
import java.util.List;

import Modules.BaseModule.Commands.ParsingCommand;

public class RoleCommand extends ParsingCommand
{
	private List<List<String>> permittedRoleSets;
	
	/** Constructor.
	 * 
	 *  @param names The list of names this command can be called by.
	 *  @param usage The description of this command.
	 *  @param parameters The list of parameter names for this command, each with their own set of aliases.
	 *  @param flags The set of flag names for this command, listed with aliases like the parameters.
	 *  @param permittedRoleSets The lists of sets of roles needed to run this command. If the runner has
	 *    any of these sets in its entirety then the command will run, else it will throw a MissingRoleException.
	 *  @param commandFunction the function that runs when this command is executed.
	 */
	public RoleCommand(String[] names, String usage, String[][] parameters, String[][] flags,
			String[][] permittedRoleSets, CommandFunction commandFunction)
	{
		super(names, usage, parameters, flags, commandFunction);
		this.permittedRoleSets = new ArrayList<List<String>>();
		
		for (int i = 0; i < permittedRoleSets.length; ++i)
		{
			this.permittedRoleSets.add(new ArrayList<String>());
			for (int j = 0; j < permittedRoleSets[i].length; ++j) this.permittedRoleSets.get(i).add(permittedRoleSets[i][j]); 
		}
	}

	private boolean hasPermission(RoleHolder holder) // Whether the holder has permission to run this command
	{
		for (int i = 0; i < permittedRoleSets.size(); ++i)
		{
			if (permittedRoleSets.get(i).size() == 0) continue;
			
			boolean match = true;
			for (int j = 0; j < permittedRoleSets.get(i).size(); ++j)
			{
				if (!holder.hasRole(permittedRoleSets.get(i).get(j)))
				{
					match = false;
					break;
				}
			}
			if (match) return true;
		}
		return false;
	}
	
	/** @throws MissingRoleException if the caller isn't a RoleHolder or simply lacks permission.
	 *  @throws Exception if the command ran but threw an exception.
	 */
	@Override
	public void execute(Object caller, String[] words) throws MissingRoleException, Exception
	{	
		if (RoleHolder.class.isAssignableFrom(caller.getClass()))
		{
			if (hasPermission((RoleHolder) caller)) super.execute(caller, words);
			else throw new MissingRoleException(this.getNames().get(0), this.permittedRoleSets);
		}
		else throw new MissingRoleException("Class " + caller.getClass() + " cannot hold roles.");
	}
}
