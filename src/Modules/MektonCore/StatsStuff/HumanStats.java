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
	private void initStats()
	{
		stats.put("int", 0);
//		stats.put("edu", 0);
//		stats.put("col", 0);
		stats.put("ref", 0);
//		stats.put("att", 0);
//		stats.put("emp", 0);
		stats.put("tec", 0);
		stats.put("luk", 0);
		stats.put("mov", 0);
		stats.put("bod", 0);
		stats.put("psi", 0);
	}
	
	/*
	 * 
	 */
	
	/** Returns the value of a stat, either at base or with modifiers.
	 * 
	 * @param stat Stat to change.
	 * @param value Value to set to.
	 */
	public int getStat(String stat, boolean base)
	{
		stat = stat.toLowerCase().substring(0, 3);
		if (base) return stats.get(stat);
		else return stats.get(stat); // TODO
	}
	
	/** Sets a stat. If the stat isn't registered this does nothing.
	 * 
	 * @param stat Stat to change.
	 * @param value Value to set to.
	 */
	public void setStat(String stat, int value)
	{
		stat = stat.toLowerCase().substring(0, 3);
		if (stats.get(stat) != null) stats.put(stat, value);
	}
	
	public int getStatTotal()
	{
		int sum = 0;
		for (int i = 0; i < stats.values().size(); ++i)
		{
			sum += ((Integer[]) stats.values().toArray())[i];
		}
		return sum;
	}
}
