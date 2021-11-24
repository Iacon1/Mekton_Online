// By Iacon1
// Created 11/23/2021
// Damage from standard melee weapons, guns, and fists

package Modules.MektonCore.StatsStuff.DamageTypes;

import Modules.MektonCore.EntityTypes.Mek;
import Modules.MektonCore.StatsStuff.HitLocation;
import Modules.MektonCore.StatsStuff.SystemTypes.MekServo;

public class EnergyDamage extends SolidDamage
{
	@Override
	public void apply(Mek recipient, HitLocation location)
	{
		MekServo servo = recipient.getServo(location);
		
		double delta = getDamage(scale) - servo.getCurrentArmor(scale) / 2; // How much damage is left after armor
		
		if (servo.getDC(scale) == 0) {applyDirect(servo, delta, getDamage(scale));} // Ablative
		else if (servo.getDC(scale) <= getDamage(scale)) {applyDirect(servo, delta, -1);} // Not ablative, armor chipped
		else applyDirect(servo, delta, 0); // Not ablative, no armor damage
	}
}
