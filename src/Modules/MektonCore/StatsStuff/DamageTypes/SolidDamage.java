// By Iacon1
// Created 11/23/2021
// Damage from standard melee weapons, guns, and fists

package Modules.MektonCore.StatsStuff.DamageTypes;

import Modules.MektonCore.EntityTypes.Human;
import Modules.MektonCore.EntityTypes.Mek;
import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.ExceptionTypes.ExcessArmorException;
import Modules.MektonCore.ExceptionTypes.ExcessHealthException;
import Modules.MektonCore.StatsStuff.HitLocation;
import Modules.MektonCore.StatsStuff.SystemTypes.MekServo;
import Utils.Logging;

public class SolidDamage implements Damage
{
	private double damage; // 1x scale
	protected Scale scale;
	
	public double getDamage(Scale scale)
	{
		return scale.getDamageScale() * damage;
	}

	/** Simply applies damage to the servo.
	 * 
	 *  @param healthDamage Damage to health at native scale.
	 *  @param armorDamage Damage to armor at native scale.
	 */
	protected void applyDirect(MekServo servo, double healthDamage, double armorDamage)
	{
		double newHealth = Math.max(0, servo.getHealth(scale) - healthDamage);
		double newArmor = Math.max(0, servo.getCurrentArmor(scale) - armorDamage);
		
		try {servo.setHealth(scale, newHealth);}
		catch (ExcessHealthException e) {Logging.logException(e);}
		
		try {servo.setCurrentArmor(scale, newArmor);}
		catch (ExcessArmorException e) {Logging.logException(e);}
	}
	
	@Override
	public void apply(Mek recipient, HitLocation location)
	{
		MekServo servo = recipient.getServo(location);
		
		double delta = getDamage(scale) - servo.getCurrentArmor(scale); // How much damage is left after armor
		
		if (servo.getDC(scale) == 0) {applyDirect(servo, delta, getDamage(scale));} // Ablative
		else if (servo.getDC(scale) <= getDamage(scale)) {applyDirect(servo, delta, -1);} // Not ablative, armor chipped
		else applyDirect(servo, delta, 0); // Not ablative, no armor damage
	}

	@Override
	public void apply(Human recipient, HitLocation location)
	{
		// TODO Auto-generated method stub

	}

}
