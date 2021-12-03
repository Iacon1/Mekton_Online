// By Iacon1
// Created 11/23/2021
// Damage from AP solid weapons

package Modules.MektonCore.StatsStuff.DamageTypes;

import Modules.MektonCore.EntityTypes.Mek;
import Modules.MektonCore.StatsStuff.HitLocation;
import Modules.MektonCore.StatsStuff.ScaledUnits.ScaledHitValue;
import Modules.MektonCore.StatsStuff.SystemTypes.MekServo;

public class APDamage extends SolidDamage
{
	@Override
	public void apply(Mek recipient, HitLocation location)
	{
		MekServo servo = recipient.getServo(location);
		
		ScaledHitValue delta = damage.subtract(servo.getArmor().divide(2)); // How much damage is left after armor
		
		if (servo.getDC().getValue() == 0) {applyDirect(servo, delta, damage);} // Ablative
		else if (servo.getDC().lessThan(damage)) {applyDirect(servo, delta, new ScaledHitValue(servo.getScale(), 1));} // Not ablative, armor chipped
		else applyDirect(servo, delta, new ScaledHitValue(servo.getScale(), 0)); // Not ablative, no armor damage
	}
}
