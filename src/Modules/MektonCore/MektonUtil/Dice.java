// By Iacon1
// Created 09/16/2021
//

package Modules.MektonCore.MektonUtil;

import java.util.Random;

public class Dice
{
	/** Rolls a 1D10.
	 * 
	 *  @param explode Does the die explode?
	 */
	public static int rollDie(boolean explode)
	{
		Random random = new Random();
		int roll = random.nextInt(10) + 1;
		if (roll == 10 && explode) return roll + rollDie(true);
		else return roll;
	}
}
