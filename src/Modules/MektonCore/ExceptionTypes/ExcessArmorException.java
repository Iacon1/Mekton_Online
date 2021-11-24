// By Iacon1
// Created 11/23/2021
// Tried to give too much health

package Modules.MektonCore.ExceptionTypes;

@SuppressWarnings("serial")
public class ExcessArmorException extends Exception
{
	public ExcessArmorException(double attemptedArmor, double maxArmor)
	{
		super("Tried to give " + attemptedArmor + " to a servo with " + maxArmor + " maximum armor.");
	}
}
