// By Iacon1
// Created 05/20/2021
// Someone who can run commands

package GameEngine.EntityTypes;

public interface CommandRunner
{
	/** Runs a command.
	 * 
	 *  @param params The command name, followed by any parameters, values, or flags.
	 *  @return The command's output, or null if no command was recognized.
	 */
	public String runCommand(String... words);
}
