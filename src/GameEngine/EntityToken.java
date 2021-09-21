// By Iacon1
// Created 09/21/2021
// Used to convienently get a connected object

package GameEngine;

import java.util.function.Supplier;

import GameEngine.EntityTypes.GameEntity;

public class EntityToken<T extends GameEntity> implements Supplier<T>
{
	private int id;
	
	public EntityToken()
	{
		id = -1;
	}
	public EntityToken(int id)
	{
		this.id = id;
	}
	public EntityToken(T entity)
	{
		id = entity.getId();
	}
	
	@Override
	public T get()
	{
		return (T) GameInfo.getWorld().getEntity(id);
	}
}
