// By Iacon1
// Created 09/26/2021
// A human

package Modules.MektonCore.EntityTypes;

import Modules.MektonCore.StatsStuff.HumanStats;
import Modules.MektonCore.StatsStuff.DamageTypes.Damage;
import Modules.MektonCore.StatsStuff.HumanStats.Stat;

public class Human extends MektonActor
{
	private HumanStats stats;
	
	private String name;
	
	public Human()
	{
		super();
	}
	public Human(String owner, MektonMap map)
	{
		super(owner, map);
		this.stats = new HumanStats();
		this.stats.initStats(5);
	}

	public String statSummary()
	{
		String summary =
				"Intelligence:     " + stats.getStat(HumanStats.Stat.intelligence, true) + "\n" +
				"Education:        " + stats.getStat(HumanStats.Stat.education, true) + "\n" +
				"Cool:             " + stats.getStat(HumanStats.Stat.cool, true) + "\n" +
				"Reflexes:         " + stats.getStat(HumanStats.Stat.reflexes, true) + "\n" +
				"Attractiveness:   " + stats.getStat(HumanStats.Stat.attractiveness, true) + "\n" +
				"Empathy:          " + stats.getStat(HumanStats.Stat.empathy, true) + "\n" +
				"Tech Ability:     " + stats.getStat(HumanStats.Stat.techAbility, true) + "\n" +
				"luck:             " + stats.getStat(HumanStats.Stat.luck, true) + "\n" +
				"Movement Ability: " + stats.getStat(HumanStats.Stat.movementAbility, true) + "\n" +
				"Body:             " + stats.getStat(HumanStats.Stat.body, true) + "\n" +
				"Psionics:         " + stats.getStat(HumanStats.Stat.psionics, true);
		
		return summary;
	}
	@Override
	protected int getMA()
	{
		return stats.getStat(Stat.movementAbility, false);
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void onPause() {}

	@Override
	public void onStart() {}

	@Override
	public void onAnimStop() {}

	@Override
	public void takeDamage(Damage damage) {}

	@Override
	public void defend(MektonActor aggressor) {}

	@Override
	public void attack(MektonActor defender) {}
}
