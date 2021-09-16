// By Iacon1
// Created 09/16/2021
// Consumes special header params

package Modules.BaseModule;

import java.util.Arrays;

public class CommandParser
{
	public static class ParsedCommand
	{
		public int target = -1;
		public String[] params;
	}
	
	public static ParsedCommand parseCommand(String[] params)
	{
		ParsedCommand command = new ParsedCommand();
		int offset = 0;
		if (params[0].substring(0, 1).equals("@")) // @ used, target a specific entity
		{
			command.target = Integer.valueOf(params[0].substring(1));
			offset = 1;
		}
		
		command.params = Arrays.copyOfRange(params, offset, params.length);
		
		return command;
	}
}
