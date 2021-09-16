// By Iacon1
// Created 09/16/2021
// Human states & skills
// TODO debuffs / buffs

package Modules.MektonCore.StatsStuff;

import java.util.HashMap;

public class HumanStats
{
	// TODO some uneccessary?
	/* Stats
	 * Int - Intelligence
	 * Edu - Education
	 * Col - Cool
	 * Ref - Reflexes
	 * Att - Attractiveness
	 * Emp - Empathy
	 * Tec - Tech Ability
	 * Luk - Luck
	 * Mov - MA
	 * Bod - Body
	 * Psi - Psionics
	 */
	private HashMap<String, Integer> stats;
	
	public int getStat(String stat)
	{
		return stats.get(stat);
	}
	
	
}
