// By Iacon1
// Created 09/16/2021
// A list of servos

package Modules.MektonCore.StatsStuff;

import java.util.ArrayList;
import java.util.HashMap;

public class ServoList<T extends Servo>
{
	public enum ServoType
	{
		torso,
		arm,
		leg,
		head,
		wing,
		tail,
		pod
	}
	
	public enum Side
	{
		left,
		right,
		middle
	}
	
	private ArrayList<T> servos; // List of servos
	
	private HashMap<ServoType, ArrayList<Integer>> byCategory; // List of servos by category
	private HashMap<Side, ArrayList<Integer>> bySide; // List of servos by side
	
	private ServoType getCategory(int servo)
	{
		if (servos.size() - 1 < servo || servos.get(servo) == null) return null;
		
		for (ServoType type : ServoType.values())
		{
			if (byCategory.get(type).contains(servo)) return type;
		}
		
		return null;
	}
	private Side getSide(int servo)
	{
		if (servos.size() - 1 < servo || servos.get(servo) == null) return null;
		
		for (Side side : Side.values())
		{
			if (bySide.get(side).contains(servo)) return side;
		}
		
		return null;
	}
	
	private ArrayList<Integer> byCategoryAndSide(ServoType category, Side side)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < servos.size(); ++i)
		{
			if (byCategory.get(category).contains(i) && bySide.get(side).contains(i))
				list.add(i);
		}
		
		return list;
	}
	
	public void addServo(ServoType category, Side side, T servo)
	{
		int index = servos.size() - 1;
		if (servos.indexOf(null) != -1)
		{
			index = servos.indexOf(null);
			servos.set(index, servo);
		}
		else servos.add(servo);
		
		byCategory.get(category).add(index);
		bySide.get(side).add(index);
	}
	public void removeServo(T servo)
	{
		int index = servos.indexOf(servo);
		if (index == -1) return;
		
		byCategory.get(getCategory(index)).remove(index);
		bySide.get(getSide(index)).remove(index);
	}
	public T getServo(ServoType category, Side side, int number)
	{
		int index = byCategoryAndSide(category, side).get(number);
		return servos.get(index);
	}
}
