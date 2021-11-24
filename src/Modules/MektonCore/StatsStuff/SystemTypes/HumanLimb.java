// By Iacon1
// Created 09/16/2021
// An organic servo, i. e. a limb

package Modules.MektonCore.StatsStuff.SystemTypes;

import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.StatsStuff.HitLocation.ServoType;

public class HumanLimb extends System
{
	private ServoType limbType;
	private int body;
	
	public HumanLimb(ServoType limbType, int body)
	{
		this.limbType = limbType;
		this.body = body;
	}
	@Override
	public double getMaxHealth(Scale scale)
	{
		double baseValue = 0;
		switch (body)
		{
		case 2: baseValue = 4; break;
		case 3: case 4: baseValue = 5; break;
		case 5: case 6: case 7: baseValue = 6; break;
		case 8: case 9: baseValue = 7; break;
		case 10: baseValue = 8; break;
		default: baseValue = 0;
		}
		
		switch (limbType)
		{
		case head: return baseValue;
		case torso: return 2 * baseValue;
		case arm: case leg: return baseValue + Math.floor(baseValue / 2);
		default: return 0;
		}
	}

}
