// By Iacon1
// Created 09/16/2021
// A list of servos

package Modules.MektonCore.StatsStuff;

import Modules.MektonCore.StatsStuff.HitLocation.ServoSide;
import Modules.MektonCore.StatsStuff.HitLocation.ServoType;
import Modules.MektonCore.StatsStuff.SystemTypes.AdditiveSystems.Servos.Servo;
import Utils.IndexTable;
import Utils.IndexTypesException;
import Utils.Logging;

public abstract class ServoList
{	
	protected IndexTable<Servo> servos;

	/** If the servo is already in the list:
	 *    Adds the servo to the given type & side.
	 *  If the servo isn't already in the list:
	 *    Adds it to the system list then proceeds with above.
	 *  
	 *  @param type The type to list the servo as.
	 *  @param side The side to list the servo as.
	 *  @param servo The servo to add or to list.
	 */
	public void addServo(ServoType type, ServoSide side, Servo servo) {servos.add(servo, type, side, servo);}
	
	/** Removes the given servo from the table.
	 *  
	 *  @param servo  The servo to remove.
	 */
	public void removeServo(Servo servo) {servos.remove(servo);}
	public Servo getServo(ServoType category, ServoSide side, int index)
	{
		try {return servos.get(category, side).get(index);}
		catch (IndexTypesException e)
		{
			Logging.logException(e);
			return null;
		}
	}
	
	public Servo getServo(HitLocation location) {return getServo(location.type, location.side, location.index);}
	
	/** Returns the amount of servos of the given type.
	 * 
	 *  @param category The type of servo to count.
	 *  
	 *  @return The amount of servos of that type.
	 */
	public int servoCount(ServoType type)
	{
		try {return servos.get(type, null).size();}
		catch (IndexTypesException e)
		{
			Logging.logException(e);
			return 0;
		}
	}
	/** Returns the amount of servos in the given side.
	 * 
	 *  @param side The side to count.
	 *  
	 *  @return The amount of servos with that side.
	 */
	public int servoCount(ServoSide side)
	{
		try {return servos.get(null, side).size();}
		catch (IndexTypesException e)
		{
			Logging.logException(e);
			return 0;
		}
	}
	/** Returns the amount of servos in the given category & side.
	 * 
	 *  @param Type the type of servo to count.
	 *  @param side The side to count on.
	 *  
	 *  @return The amount of servos with that combination of category & side.
	 */
	public int servoCount(ServoType type, ServoSide side)
	{
		try {return servos.get(type, side).size();}
		catch (IndexTypesException e)
		{
			Logging.logException(e);
			return 0;
		}
	}
}
