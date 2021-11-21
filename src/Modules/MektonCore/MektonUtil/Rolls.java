// By Iacon1
// Created 09/16/2021
// All sorts of rolls

package Modules.MektonCore.MektonUtil;

import java.util.Random;

import Modules.MektonCore.StatsStuff.ServoList;
import Modules.MektonCore.StatsStuff.ServoLocation;

public class Rolls
{
	private static int maxRolls = 256; // So explosions don't go on forever
	
	public static int rollXDY(int X, int Y)
	{
		Random random = new Random();
		int roll = 0;
		for (int i = 0; i < X; ++i) roll += random.nextInt(Y) + 1;
		
		return roll;
	}
	private static int rollD10Counting(int count)
	{
		Random random = new Random();
		int roll = random.nextInt(10) + 1;
		if (roll == 10 && count < maxRolls) return roll + rollD10Counting(count + 1);
		else return roll;
	}
	/** Rolls a 1D10.
	 * 
	 *  @param explode Does the die explode?
	 */
	public static int rollD10(boolean explode)
	{
		Random random = new Random();
		int roll = random.nextInt(10) + 1;
		if (roll == 10 && explode) return roll + rollD10Counting(1);
		else return roll;
	}

	public static ServoLocation hitChart(ServoList servos)
	{
		return null; // TODO
	}
}
