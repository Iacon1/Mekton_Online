// By Iacon1
// Created 09/16/2021
// Anything with health

package Modules.MektonCore.StatsStuff.SystemTypes;

import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.ExceptionTypes.ExcessHealthException;
import Modules.MektonCore.StatsStuff.SystemList;
import Utils.GSONConfig.TransSerializables.SerializableDLListMember;

public abstract class System implements SerializableDLListMember<SystemList>
//public abstract class System  <T extends SystemList> implements SerializableDLListMember<T>
{
	protected transient SystemList listHolder;
//	protected transient T listHolder;
	
	protected Scale scale;
	private int systemID;
	private double health; // 1x scale

	/** Copy constructor */
	public System(SystemList listHolder, System system)
//	public System(T listHolder, System<T> system)
	{
		this.listHolder = listHolder;
		scale = system.scale;
		health = system.health;
		
		systemID = this.listHolder.addSystem(this);
		health = 0;
	}
	/** Copy constructor */
	public System(System system)
//	public System(System<T> system)
	{
		listHolder = null;
		scale = system.scale;
		health = system.health;
	}
	public System()
	{
		listHolder = null;
		scale = Scale.mekton; // 1x
		health = -1;
	}
	public System(SystemList listHolder, Scale scale)
//	public System(T listHolder, Scale scale)
	{
		this.listHolder = listHolder;
		this.scale = scale;
		
		systemID = this.listHolder.addSystem(this);
		health = 0;
	}
	public System(Scale scale)
	{
		this.listHolder = null;
		this.scale = scale;
		health = 0;
	}
	
	public int getID()
	{
		return systemID;
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

	/** Sets the list that this system belongs to. */
	public void setListHolder(SystemList listHolder) {this.listHolder = listHolder;}
//	public void setListHolder(T listHolder) {this.listHolder = listHolder;}
	@Override
	public void cutLink() {listHolder = null;}
	@Override
	public void establishLink(SystemList listHolder) {setListHolder(listHolder);}
//	public void establishLink(T listHolder) {setListHolder(listHolder);}
}
