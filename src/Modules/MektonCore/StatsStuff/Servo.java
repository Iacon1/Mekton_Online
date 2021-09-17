// By Iacon1
// Created 09/16/2021
// A servo or limb

package Modules.MektonCore.StatsStuff;

public class Servo
{
	private int health = 0;
	private int maxHealth = 0;
	
	/** Sets max health in points.
	 * 
	 *  @param value Value in points.
	 */
	public void setMaxHealth(int value)
	{
		maxHealth = value;
	}
	/** Gets max health in points.
	 */
	public int getMaxHealth()
	{
		return maxHealth;
	}
	/** Sets current health in points.
	 * 
	 * @param value Value in points.
	 */
	public void setCurrentHealth(int value)
	{
		health = value;
	}
	/** Gets current health in points.
	 */
	public int getCurrentHealth()
	{
		return health;
	}
	
	/** Sets max health in kills.
	 * 
	 * @param value in kills.
	 */
	public void setMaxKills(int value)
	{
		setMaxHealth(25 * value);
	}
	/** Gets max health in kills.
	 */
	public int getMaxKills()
	{
		return getMaxHealth() / 25;
	}
	/** Sets current health in kills.
	 * 
	 * @param value in kills.
	 */
	public void setCurrentKills(int value)
	{
		setCurrentHealth(25 * value);
	}
	/** Gets current health in kills.
	 */
	public int getCurrentKills()
	{
		return getCurrentHealth() / 25;
	}
}
