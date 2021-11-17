// By Iacon1
// Created 11/16/2021
//

package Modules.BaseModule.Commands;

import GameEngine.EntityTypes.CommandRunner;
import GameEngine.Server.Account;
import java.util.Map;

public abstract class ParsingCommandAccount extends Account
{
	private transient ParsingCommandBank commandBank;
	
	public ParsingCommandAccount()
	{
		super();
		commandBank = new ParsingCommandBank();
		
		registerCommands();
	}

	/** Registers the list of commands the account has available.
	 * 
	 */
	protected void registerCommands()
	{
		ParsingCommand helpCommand = new ParsingCommand(
				new String[]{"Help", "help"},
				"Gives info on a command or lists all commands",
				new String[][]{
					new String[]{"command", "c"}
				},
				new String[][] {},
				(Object caller, Map<String, String> params, Map<String, Boolean> flags) -> {});
		commandBank.registerCommand(helpCommand);
	}
	
	@Override
	public boolean runCommand(String... words)
	{
		if (commandBank.recognizes(words[0]))
		{
			commandBank.execute(this, words);
			return true;
			
		}
		else if (CommandRunner.class.isAssignableFrom(getPossessee().getClass())) return ((CommandRunner) getPossessee()).runCommand(words);
		else return false;
	}
}
