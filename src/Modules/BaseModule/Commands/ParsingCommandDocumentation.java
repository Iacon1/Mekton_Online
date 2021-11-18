// By Iacon1
// Created 11/18/2021
// Documentation for a command

package Modules.BaseModule.Commands;

import java.util.List;

import Utils.MiscUtils;

public class ParsingCommandDocumentation
{
	private String usage;
	private List<String> parameterDescriptions;
	private List<String> flagDescriptions;
	
	
	public String getDocumentation(List<String> names, List<List<String>> parameters, List<List<String>> flags)
	{
		String documentation =
				MiscUtils.arrayToString(names, ", ") + ":\n\n"
				+ "Parameters: \n"; // TODO string manager
			
		for (int i = 0; i < parameters.size(); ++i) documentation = documentation
				+ "  " + MiscUtils.arrayToString(parameters.get(i), ", ") + ": " + parameterDescriptions.get(i) + "\n";
		
		documentation = documentation
				+ "\n"
				+ "\n"
				+ "Flags: \n";
			
		for (int i = 0; i < flags.size(); ++i) documentation = documentation
				+ "  " + MiscUtils.arrayToString(flags.get(i), ", ") + ": " + flagDescriptions.get(i) + "\n";
		
		documentation = documentation
				+ "\n"
				+ "\n"
				+ "Usage: \n"
				+ usage + "\n";
		
		return documentation;
	}
}
