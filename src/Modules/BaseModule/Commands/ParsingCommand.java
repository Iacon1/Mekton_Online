// By Iacon1
// Created 11/07/2021
//

package Modules.BaseModule.Commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParsingCommand
{
	public static interface CommandFunction
	{
		public void execute(Object caller, Map<String, String> parameters, Map<String, Boolean> flags);
	}
	
	private LinkedList<String> names;
	private String usage;
	private LinkedList<LinkedList<String>> parameters;
	private LinkedList<LinkedList<String>> flags;
	private static char tagChar = '-'; // Denotes a tag, i. e. a parameter or flag
	
	private CommandFunction commandFunction;
	
	/** Constructor.
	 * 
	 *  @param names The list of names this command can be called by.
	 *  @param usage The description of this command.
	 *  @param parameters The list of parameter names for this command, each with their own set of aliases.
	 *  @param flags The set of flag names for this command, listed with aliases like the parameters.
	 *  @param commandFunction the function that runs when this command is executed.
	 */
	public ParsingCommand(String[] names, String usage, String[][] parameters, String[][] flags, CommandFunction commandFunction)
	{
		this.names = new LinkedList<String>();
		this.usage = usage;
		this.parameters = new LinkedList<LinkedList<String>>();
		this.flags = new LinkedList<LinkedList<String>>();
		this.commandFunction = commandFunction;
		
		for (int i = 0; i < names.length; ++i) this.names.add(names[i]);
		
		for (int i = 0; i < parameters.length; ++i)
		{
			this.parameters.add(new LinkedList<String>());
			
			for (int j = 0; j < parameters[i].length; ++j) this.parameters.get(i).add(parameters[i][j]);
		}
		for (int i = 0; i < flags.length; ++i)
		{
			this.flags.add(new LinkedList<String>());
			
			for (int j = 0; j < flags[i].length; ++j) this.flags.get(i).add(flags[i][j]);
		}
	}
	
	public List<String> getNames()
	{
		return names;
	}
	public boolean hasAlias(String alias)
	{
		return names.contains(alias);
	}
	public String getUsage()
	{
		return usage;
	}
	
	private Map<String, String> parseParameters(String[] words) // Parses out all parameters
	{
		HashMap<String, String> parameters = new HashMap<String, String>();
		for (int i = 0; i < this.parameters.size(); ++i)
		{
			parameters.put(this.parameters.get(i).get(0), null);
			for (int j = 0; j < this.parameters.get(i).size(); ++j) // Search to see if any of the parameter's aliases are there
			{
				int index = Arrays.asList(words).indexOf(tagChar + this.parameters.get(i).get(j));
				
				if (index != -1) // Found an alias
				{
					parameters.put(this.parameters.get(i).get(0), words[index + 1]); // TODO what if no index + 1, or what if next value is a tag itself?
					break;
				}
				else continue;
			}
		}
		
		return parameters;
	}
	private Map<String, Boolean> parseFlags(String[] words) // Parses out all flags
	{
		HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
		
		for (int i = 0; i < this.flags.size(); ++i)
		{
			flags.put(this.flags.get(i).get(0), false);
			for (int j = 0; j < this.flags.get(i).size(); ++j) // Search to see if any of the flag's aliases are there
			{
				int index = Arrays.asList(words).indexOf(tagChar + this.flags.get(i).get(j));
				
				if (index != -1) // Found an alias
				{
					flags.put(this.flags.get(i).get(0), true);
					break;
				}
				else continue;
			}
		}
		
		return flags;
	}
	
	/** Parses and executes the command.
	 *
	 *  @param caller The object calling this command.
	 *  @param words The command, parameters, and flags that were called - In no particular order.
	 */
	public void execute(Object caller, String[] words)
	{	
		Map<String, String> parameters = parseParameters(words);
		Map<String, Boolean> flags = parseFlags(words);
		
		commandFunction.execute(caller, parameters, flags);
	}
}
