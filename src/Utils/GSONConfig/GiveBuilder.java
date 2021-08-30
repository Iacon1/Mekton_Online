// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import Modules.MektonCore.HexData;
import Server.Account;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilderNGW() // Evil temp hack TODO change this
	{
		GsonBuilder builder = new GsonBuilder();

		//builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		//builder.registerTypeAdapter(Account.class, new AbsAdapter<Account>());
		//builder.registerTypeAdapter(HexData.class, new AbsAdapter<HexData>());

		builder.registerTypeAdapterFactory(new AbsFactory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Account>(Account.class));
		builder.registerTypeAdapterFactory(new AbsFactory<HexData>(HexData.class));
		builder.enableComplexMapKeySerialization();
		//builder.generateNonExecutableJson();

		return builder;
	}
	
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = giveBuilderNGW();
		builder = builder.registerTypeAdapter(GameWorld.class, new GameWorldDeserializer());
		return builder;
	}
}
