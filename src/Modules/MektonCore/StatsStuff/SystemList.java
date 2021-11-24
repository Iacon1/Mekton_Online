// By Iacon1
// Created 11/23/2021
// A list of components (i. e. things with health)

package Modules.MektonCore.StatsStuff;

import GameEngine.EntityTypes.GameEntity;
import Modules.MektonCore.StatsStuff.SystemTypes.System;
import Utils.GappyArrayList;
import Utils.GSONConfig.TransSerializables.SerializableDLListHolder;

public abstract class SystemList extends SerializableDLListHolder<SystemList, System, GappyArrayList<System>>
// public abstract class SystemList<T extends SystemList, C extends System<T>> extends SerializableDLListHolder<T, C, GappyArrayList<C>> {}
{

	public int addSystem(System system)
	{
		int index = heldList.getFirstGap();
		heldList.add(system);
		return index;
	}
	public int findSystem(System system)
	{
		return heldList.indexOf(system);
	}
	public System getSystem(int id)
	{
		return heldList.get(id);
	}
	public void removeSystem(int id)
	{
		heldList.remove(id);
	}
	public void removeEntity(GameEntity entity)
	{
		removeSystem(entity.getId());
	}
}