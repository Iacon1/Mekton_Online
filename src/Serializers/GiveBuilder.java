// By Iacon1
// Created 05/06/2021
//

package Serializers;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		return builder;
	}
}
