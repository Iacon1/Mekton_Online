// By Iacon1
// Created 11/16/2021
// List of commands that can be executed

package Modules.BaseModule.Commands;

import java.util.ArrayList;
import java.util.List;

public class ParsingCommandBank
{
	private List<ParsingCommand> commands;
	
	public ParsingCommandBank()
	{
		commands = new ArrayList<ParsingCommand>();
	}
	
	public void registerCommand(ParsingCommand command)
	{
		commands.add(command);
	}
	
	/** Returns whether the command is recognized or not.
	 *  
	 *  @param text The command to check.
	 *  @return Whether a command with the same name is in this bank.
	 */
	public boolean recognizes(String command)
	{
		String[] words = command.split(" ");
		for (int i = 0; i < commands.size(); ++i)
			if (commands.get(i).hasAlias(words[0])) return true;
		
		return false;
	}
	
	/** Executes the command, if it exists.
	 * 
	 *  @param caller The object calling this.
	 *  @param words The command, parameters, values, and flags to run.
	 */
	public void execute(Object caller, String[] words) throws Exception
	{
		for (int i = 0; i < commands.size(); ++i)
		{
			if (commands.get(i).hasAlias(words[0]))
			{
				commands.get(i).execute(caller, words);
				return;
			}
		}
	}
	
	/** Executes the command, if it exists.
	 * 
	 *  @param caller The object calling this.
	 *  @param command The command to run.
	 */
	public void execute(Object caller, String command) throws Exception
	{
		String[] words = command.split(" ");
		
		execute(caller, words);
	}
}
