// By Iacon1
// Created 09/16/2021
// Anything with health

package Modules.MektonCore.StatsStuff.SystemTypes;

import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.ExceptionTypes.ExcessValueException;
import Modules.MektonCore.StatsStuff.SystemList;
import Modules.MektonCore.StatsStuff.ScaledUnits.ScaledHitValue;
import Utils.GSONConfig.TransSerializables.SerializableDLListMember;

public abstract class System implements SerializableDLListMember<SystemList>
//public abstract class System  <T extends SystemList> implements SerializableDLListMember<T>
{
	protected transient SystemList listHolder;
//	protected transient T listHolder;

	private int systemID;
	private ScaledHitValue health;

	/** Copy constructor */
	public System(SystemList listHolder, System system)
//	public System(T listHolder, System<T> system)
	{
		this.listHolder = listHolder;
		health = new ScaledHitValue();
		
		systemID = this.listHolder.addSystem(this);
	}
	/** Copy constructor */
	public System(System system)
//	public System(System<T> system)
	{
		listHolder = null;
		health = new ScaledHitValue(system.health);
	}
	public System()
	{
		listHolder = null;
		health = new ScaledHitValue();
	}
	public System(SystemList listHolder)
//	public System(T listHolder, Scale scale)
	{
		this.listHolder = listHolder;
		
		systemID = this.listHolder.addSystem(this);
		health = new ScaledHitValue();
	}

	
	public int getID()
	{
		return systemID;
	}
	
	/** Gets max health.
	 * 
	 *  @return The maximum health.
	 */
	public abstract ScaledHitValue getMaxHealth();
	
	/** Sets current health.
	 * 
	 *  @param scale Scale of the incoming value.
	 *  @param value Value to set to.
	 *  
	 *  @throws ExcessValueException if you try to give it more health than its maximum.
	 */
	public void setHealth(ScaledHitValue value) throws ExcessValueException
	{
		if (value.greaterThan(getMaxHealth())) throw new ExcessValueException(value, getMaxHealth(), "health");
		health.setValue(value);
	}
	/** Gets current health.
	 *  @return health.
	 */
	public ScaledHitValue getHealth()
	{
		return new ScaledHitValue(health);
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
