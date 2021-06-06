// By Iacon1
// Created 05/06/2021
//

package Utils.Serializers;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import GameEngine.PacketTypes.Packet;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		//builder.registerTypeAdapter(Packet.class, new AbsAdapter<Packet>());
		builder.registerTypeAdapter(GameWorld.class, new GameWorldDeserializer());
		return builder;
	}
	
	public static GsonBuilder giveBuilderNGW() // Evil temp hack
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		//builder.registerTypeAdapter(Packet.class, new AbsAdapter<Packet>());
		//builder.registerTypeAdapter(GameWorld.class, new GameWorldDeserializer());
		return builder;
	}
}
