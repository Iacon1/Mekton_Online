// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import GameEngine.HexData;
import GameEngine.PacketTypes.Packet;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();
<<<<<<< Updated upstream:src/Utils/Serializers/GiveBuilder.java
		builder = builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		//builder.registerTypeAdapter(Packet.class, new AbsAdapter<Packet>());
		builder = builder.registerTypeAdapter(HexData.class, new AbsAdapter<HexData>());
		builder = builder.registerTypeAdapter(GameWorld.class, new GameWorldDeserializer());
=======
		//builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		//builder.registerTypeAdapter(Account.class, new AbsAdapter<Account>());
		//builder.registerTypeAdapter(HexData.class, new AbsAdapter<HexData>());

		builder.registerTypeAdapterFactory(new AbsFactory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Account>(Account.class));
		builder.registerTypeAdapterFactory(new AbsFactory<HexData>(HexData.class));
		builder.enableComplexMapKeySerialization();
		//builder.generateNonExecutableJson();
		
>>>>>>> Stashed changes:src/Utils/GSONConfig/GiveBuilder.java
		return builder;
	}
	
	public static GsonBuilder giveBuilderNGW() // Evil temp hack TODO change this
	{
		GsonBuilder builder = new GsonBuilder();
		builder = builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		//builder = builder.registerTypeAdapter(Packet.class, new AbsAdapter<Packet>());
		builder = builder.registerTypeAdapter(HexData.class, new AbsAdapter<HexData>());
		//builder = builder.registerTypeAdapter(GameWorld.class, new GameWorldDeserializer());
		return builder;
	}
}
