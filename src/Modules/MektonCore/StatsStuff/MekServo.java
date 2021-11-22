// By Iacon1
// Created 09/16/2021
// Servo for meks
// TODO:
/* Kill sacrificing
 * Throw range
 * Melee+
 */

package Modules.MektonCore.StatsStuff;

import Modules.MektonCore.StatsStuff.HitLocation.ServoType;
import Utils.GappyArrayList;

public class MekServo extends Servo
{
	
	public enum ServoClass
	{
		superLight,
		lightWeight,
		striker,
		mediumStriker,
		heavyStriker,
		mediumWeight,
		lightHeavy,
		mediumHeavy,
		armoredHeavy,
		superHeavy,
		megaHeavy
	}
	
	private int maxSpace = 0;
	private GappyArrayList<Integer> spaceTakers; // Important to note because it has functions a normal ArrayList doesn't
	private int cost = 0;
	private float weight = 0;
	
	private void setValues(int cost, int maxSpace, int kills, float weight)
	{
		setMaxKills(kills);
		setCurrentKills(kills);
		this.cost = cost;
		this.weight = weight;
		this.maxSpace = maxSpace;
	}
	
	public void setClass(ServoClass servoClass, ServoType servoType)
	{
		switch (servoType)
		{
		case torso:
			switch (servoClass)
			{
			case superLight: setValues(2, 2, 2, 1.0f); break;
			case lightWeight: setValues(4, 4, 4, 2.0f); break;
			case striker: setValues(6, 6, 6, 3.0f); break;
			case mediumStriker: setValues(8, 8, 8, 4.0f); break;
			case heavyStriker: setValues(10, 10, 10, 5.0f); break;
			case mediumWeight: setValues(12, 12, 12, 6.0f); break;
			case lightHeavy: setValues(14, 14, 14, 7.0f); break;
			case mediumHeavy: setValues(16, 16, 16, 8.0f); break;
			case armoredHeavy: setValues(18, 18, 18, 9.0f); break;
			case superHeavy: setValues(20, 20, 20, 10.0f); break;
			case megaHeavy: setValues(22, 22, 22, 11.0f); break;
			} break;
		case arm: case leg:
			switch (servoClass)
			{
			case superLight: setValues(2, 2, 2, 0.5f); break;
			case lightWeight: setValues(3, 3, 3, 1.0f); break;
			case striker: setValues(4, 4, 4, 1.5f); break;
			case mediumStriker: setValues(5, 5, 5, 2.0f); break;
			case heavyStriker: setValues(6, 6, 6, 2.5f); break;
			case mediumWeight: setValues(7, 7, 7, 3.0f); break;
			case lightHeavy: setValues(8, 8, 8, 3.5f); break;
			case mediumHeavy: setValues(9, 9, 9, 4.0f); break;
			case armoredHeavy: setValues(10, 10, 10, 4.5f); break;
			case superHeavy: setValues(11, 11, 11, 5.0f); break;
			case megaHeavy: setValues(12, 12, 12, 5.5f); break;
			} break;
		case head: case wing: case tail:
			switch (servoClass)
			{
			case superLight: setValues(1, 1, 1, 0.5f); break;
			case lightWeight: setValues(2, 2, 2, 1.0f); break;
			case striker: setValues(3, 3, 3, 1.5f); break;
			case mediumStriker: setValues(4, 4, 4, 2.0f); break;
			case heavyStriker: setValues(5, 5, 5, 2.5f); break;
			case mediumWeight: setValues(6, 6, 6, 3.0f); break;
			case lightHeavy: setValues(7, 7, 7, 3.5f); break;
			case mediumHeavy: setValues(8, 8, 8, 4.0f); break;
			case armoredHeavy: setValues(9, 9, 9, 4.5f); break;
			case superHeavy: setValues(10, 10, 10, 5.0f); break;
			case megaHeavy: setValues(11, 11, 11, 5.5f); break;
			} break;
		case pod:
			switch (servoClass)
			{
			case superLight: setValues(1, 2, 0, 0.0f); break;
			case lightWeight: setValues(2, 4, 0, 0.0f); break;
			case striker: setValues(3, 6, 0, 0.0f); break;
			case mediumStriker: setValues(4, 8, 0, 0.0f); break;
			case heavyStriker: setValues(5, 10, 0, 0.0f); break;
			case mediumWeight: setValues(6, 12, 0, 0.0f); break;
			case lightHeavy: setValues(7, 14, 0, 0.0f); break;
			case mediumHeavy: setValues(8, 16, 0, 0.0f); break;
			case armoredHeavy: setValues(9, 18, 0, 0.0f); break;
			case superHeavy: setValues(10, 20, 0, 0.0f); break;
			case megaHeavy: setValues(11, 22, 0, 0.0f); break;
			} break;
		}
	}
	public MekServo(ServoClass servoClass, ServoType servoType)
	{
		this.spaceTakers = new GappyArrayList<Integer>();
		setClass(servoClass, servoType);
	}
	
	public int getMaxSpace()
	{
		return maxSpace;
	}
	public int getEmptySpace()
	{
		int emptySpace = maxSpace;
		
		for (int i = 0; i < spaceTakers.size(); ++i)
		{
			emptySpace -= spaceTakers.get(i);
		}
		
		return emptySpace;
	}
	
	/** Occupies a given space if possible, returning the spacetaker id
	 * if it fit or -1 if it didn't.
	 * 
	 * @param space Amount of space to occupy.
	 * 
	 * @return Spacetaker id or -1.
	 */
	public int takeSpace(int space)
	{
		if (getEmptySpace() >= space)
		{
			int index = spaceTakers.getFirstGap();
			spaceTakers.add(space);
			return index;
		}
		else return -1; // Won't fit
	}
	public int getCost()
	{
		return cost;
	}
}
