// By Iacon1
// Created 09/12/2021
// An actor is an entity with standard Mekton actions like shooting and moving and stuff

package Modules.MektonCore.EntityTypes;

import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.MektonCore.MektonMap;
import Utils.SimpleTimer;

public abstract class MektonActor extends MapEntity
{
	public enum Scale
	{
		
	}
	
	private int MA = 5; // TODO
	
	private float actionPoints;
	private transient SimpleTimer actionTimer;
	
	public MektonActor()
	{
		super();
		actionPoints = 0f;
	}

	public MektonActor(String owner, MektonMap map)
	{
		super(owner, map);
		actionTimer = new SimpleTimer();
		
		resetActionPoints();
	}

	public void resetActionPoints()
	{
		actionPoints = 2f;
		actionTimer.start();
		resume();
	}
	
	public float remainingActions()
	{
		return actionPoints;
	}
	public boolean takeAction(float cost)
	{
		if (actionPoints >= cost)
		{
			actionPoints -= cost;
			return true;
		}
		else return false;
	}

	@Override
	public void moveTargetHex(AxialHexCoord3D target, int speed)
	{
		// Should cost 1 action to move full MA walking or half MA flying
		float moveCost = ((float) hexPos.distance(target)) / (float) MA; // TODO assumes walking
		if (takeAction(moveCost)) super.moveTargetHex(target, speed);
		else pause();
	}
	
	@Override
	public void onResume()
	{
		updatePath();
	}
	
	@Override
	public void update()
	{
		super.update();
		if (actionTimer.checkTime(10000))
		{
			resetActionPoints();
		}
	}
}
