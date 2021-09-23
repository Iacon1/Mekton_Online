// By Iacon1
// Created 09/12/2021
// An actor is an entity with standard Mekton actions like shooting and moving and stuff

package Modules.MektonCore.EntityTypes;

import java.util.LinkedList;

import Modules.HexUtilities.HexDirection;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;
import Modules.MektonCore.MektonMap;
import Utils.MiscUtils;
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
		if (actionPoints >= cost - MiscUtils.floatTolerance) // Float accuracy seems to get weird here
		{
			actionPoints = Math.max(actionPoints - cost, 0);
			return true;
		}
		else return false;
	}

	public void moveTargetHexAct(AxialHexCoord3D target, int speed)
	{
		float moveCost = ((float) hexPos.distance(target)) / (float) MA; // TODO assumes walking
		if (takeAction(moveCost)) super.moveTargetHex(target, speed);
		else pause();
	}

	public void moveDeltaHexAct(AxialHexCoord3D delta, int speed)
	{
		moveTargetHexAct(hexPos.rAdd(delta), speed);
	}
	
	public void moveDirectionalAct(HexDirection dir, int distance, int speed)
	{
		AxialHexCoord3D delta = hexPos.getUnitVector(dir).rMultiply(distance);
		setDirection(dir);
		
		moveDeltaHexAct(delta, speed);
	}
	
	@Override
	public void movePath(LinkedList<AxialHexCoord3D> path, int speed)
	{
		this.path = path;
		moveTargetHexAct(path.getFirst(), speed);
	}
	@Override
	public void updatePath()
	{
		if (this.path != null && this.hexPos == this.path.getFirst()) // Ready for next step of path
		{
			this.path.remove();
			if (this.path.isEmpty()) this.path = null;
			else moveTargetHexAct(path.getFirst(), baseSpeed);
		}
		else if (this.path != null)
		{
			moveTargetHexAct(path.getFirst(), baseSpeed);
		}
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
