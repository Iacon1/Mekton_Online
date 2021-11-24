// By Iacon1
// Created 11/23/2021
// Tried to give too much health

package Modules.MektonCore.ExceptionTypes;

@SuppressWarnings("serial")
public class ExcessHealthException extends Exception
{
	public ExcessHealthException(double attemptedHealth, double maxHealth)
	{
		super("Tried to give " + attemptedHealth + " to a servo with " + maxHealth + " maximum health.");
	}
}
