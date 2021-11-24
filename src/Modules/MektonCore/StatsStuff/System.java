// By Iacon1
// Created 09/16/2021
// Anything with health

package Modules.MektonCore.StatsStuff;

import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.ExceptionTypes.ExcessHealthException;

public abstract class System
{
	protected Scale scale;
	private double health; // 1x scale

	/** Copy constructor */
	public System(System system)
	{
		scale = system.scale;
		health = system.health;
	}
	public System()
	{
		scale = Scale.mekton;
		health = -1;
	}
	public System(Scale scale)
	{
		this.scale = scale; // 1x
		health = -1;
	}
	
	/** Gets max health.
	 * 
	 *  @param scale Scale to return in.
	 */
	public abstract double getMaxHealth(Scale scale);
	/** Gets max health in the servo's native scale.
	 */
	public double getMaxHealth()
	{
		return scale.getDamageScale() * getMaxHealth();
	}
	
	/** Sets current health.
	 * 
	 *  @param scale Scale of the incoming value.
	 *  @param value Value to set to.
	 *  
	 *  @throws ExcessHealthException if you try to give it more health than its maximum.
	 */
	public void setHealth(Scale scale, double value) throws ExcessHealthException
	{
		if (value > getMaxHealth(scale)) throw new ExcessHealthException(getMaxHealth(scale), value);
		health = scale.getDamageScale() * value;
	}
	/** Sets current health in the servo's native scale.
	 * 
	 *  @param value Value to set to.
	 *  
	 *  @throws ExcessHealthException if you try to give it more health than its maximum.
	 */
	public void setHealth(double value) throws ExcessHealthException
	{
		setHealth(scale, value);
	}

	/** Gets current health.
	 *  @param scale Scale to return in.
	 */
	public double getHealth(Scale scale)
	{
		return scale.getDamageScale() * health;
	}
	/** Gets current health in the servo's native scale.
	 */
	public double getHealth()
	{
		return scale.getDamageScale() * health;
	}
	
	/** Destroys the system */
	public abstract void destroy();
}
