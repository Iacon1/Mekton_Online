// By Iacon1
// Created 09/26/2021
// Mekton has many kinds of damage, so this is very broad

package Modules.MektonCore.StatsStuff.DamageTypes;

import Modules.MektonCore.EntityTypes.MektonActor;

public interface Damage
{
	public void apply(MektonActor recipient);
}
