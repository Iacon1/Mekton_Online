// By Iacon1
// Created 09/16/2021
// A list of servos

package Modules.MektonCore.StatsStuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Modules.MektonCore.StatsStuff.ServoLocation.ServoSide;
import Modules.MektonCore.StatsStuff.ServoLocation.ServoType;

public class ServoList<T extends Servo>
{	
	private List<T> servos; // List of servos
	
	private Map<ServoType, List<Integer>> byType; // List of servos by category
	private Map<ServoSide, List<Integer>> bySide; // List of servos by side
	
	private ServoType getCategory(int servo)
	{
		if (servos.size() - 1 < servo || servos.get(servo) == null) return null;
		
		for (ServoType type : ServoType.values())
		{
			if (byType.get(type).contains(servo)) return type;
		}
		
		return null;
	}
	private ServoSide getSide(int servo)
	{
		if (servos.size() - 1 < servo || servos.get(servo) == null) return null;
		
		for (ServoSide side : ServoSide.values())
		{
			if (bySide.get(side).contains(servo)) return side;
		}
		
		return null;
	}
	
	private List<Integer> byTypeAndSide(ServoType category, ServoSide side)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < servos.size(); ++i)
		{
			if (byType.get(category).contains(i) && bySide.get(side).contains(i))
				list.add(i);
		}
		
		return list;
	}
	
	public void addServo(ServoType type, ServoSide side, T servo)
	{
		int index = servos.size() - 1;
		if (servos.indexOf(null) != -1)
		{
			index = servos.indexOf(null);
			servos.set(index, servo);
		}
		else servos.add(servo);
		
		byType.get(type).add(index);
		bySide.get(side).add(index);
	}
	public void removeServo(T servo)
	{
		int index = servos.indexOf(servo);
		if (index == -1) return;
		
		byType.get(getCategory(index)).remove(index);
		bySide.get(getSide(index)).remove(index);
	}
	public T getServo(ServoType category, ServoSide side, int number)
	{
		int index = byTypeAndSide(category, side).get(number);
		return servos.get(index);
	}
	
	public T getServo(ServoLocation location)
	{
		return getServo(location.type, location.side, location.number);
	}
}
