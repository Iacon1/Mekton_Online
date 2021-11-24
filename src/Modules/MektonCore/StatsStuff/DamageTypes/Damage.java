// By Iacon1
// Created 09/26/2021
// Mekton has many kinds of damage, so this is very broad

package Modules.MektonCore.StatsStuff.DamageTypes;

import Modules.MektonCore.StatsStuff.MekServo;

public interface Damage
{
	/** Applies the damage to a mek's servo.
	 * 
	 *  @param recipient The servo to damage.
	 */
	public void apply(MekServo recipient);
}
