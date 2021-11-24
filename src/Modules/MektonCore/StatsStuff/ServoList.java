// By Iacon1
// Created 09/16/2021
// A list of servos

package Modules.MektonCore.StatsStuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Modules.MektonCore.StatsStuff.HitLocation.ServoSide;
import Modules.MektonCore.StatsStuff.HitLocation.ServoType;

public class ServoList extends SystemList<ServoList, Servo>
{	
	private Map<ServoType, List<Integer>> byType; // List of servos by category
	private Map<ServoSide, List<Integer>> bySide; // List of servos by side
	
	private ServoType getCategory(int servo)
	{
		if (heldList.size() - 1 < servo || heldList.get(servo) == null) return null;
		
		for (ServoType type : ServoType.values())
		{
			if (byType.get(type).contains(servo)) return type;
		}
		
		return null;
	}
	private ServoSide getSide(int servo)
	{
		if (heldList.size() - 1 < servo || heldList.get(servo) == null) return null;
		
		for (ServoSide side : ServoSide.values())
		{
			if (bySide.get(side).contains(servo)) return side;
		}
		
		return null;
	}
	
	private List<Integer> byTypeAndSide(ServoType category, ServoSide side)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < heldList.size(); ++i)
		{
			if (byType.get(category).contains(i) && bySide.get(side).contains(i))
				list.add(i);
		}
		
		return list;
	}
	
	public void addServo(ServoType type, ServoSide side, Servo servo)
	{
		int index = heldList.size() - 1;
		if (heldList.indexOf(null) != -1)
		{
			index = heldList.indexOf(null);
			heldList.set(index, servo);
		}
		else heldList.add(servo);
		
		byType.get(type).add(index);
		bySide.get(side).add(index);
	}
	public void removeServo(Servo servo)
	{
		int index = heldList.indexOf(servo);
		if (index == -1) return;
		
		byType.get(getCategory(index)).remove(index);
		bySide.get(getSide(index)).remove(index);
	}
	public Servo getServo(ServoType category, ServoSide side, int number)
	{
		int index = byTypeAndSide(category, side).get(number);
		return heldList.get(index);
	}
	
	public Servo getServo(HitLocation location)
	{
		return getServo(location.type, location.side, location.number);
	}
	
	/** Returns the amount of servos in the given category.
	 * 
	 *  @param category The category of servo to count.
	 *  
	 *  @return The amount of servos with that category.
	 */
	public int servoCount(ServoType category)
	{
		return byType.get(category).size();
	}
	/** Returns the amount of servos in the given side.
	 * 
	 *  @param side The side to count.
	 *  
	 *  @return The amount of servos with that side.
	 */
	public int servoCount(ServoSide side)
	{
		return bySide.get(side).size();
	}
	/** Returns the amount of servos in the given category & side.
	 * 
	 *  @param category The category of servo to count.
	 *  @param side The side to count on.
	 *  
	 *  @return The amount of servos with that combination of category & side.
	 */
	public int servoCount(ServoType category, ServoSide side)
	{
		return byTypeAndSide(category, side).size();
	}
}
