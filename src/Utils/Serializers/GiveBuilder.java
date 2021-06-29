// By Iacon1
// Created 05/06/2021
//

package Utils.Serializers;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import GameEngine.PacketTypes.Packet;
import Modules.MektonCore.HexData;
import Server.Account;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilderNGW() // Evil temp hack TODO change this
	{
		GsonBuilder builder = new GsonBuilder();
		builder = builder.registerTypeAdapter(GameEntity.class, new AbsAdapter<GameEntity>());
		builder = builder.registerTypeAdapter(Account.class, new AbsAdapter<Account>());
		builder = builder.registerTypeAdapter(HexData.class, new AbsAdapter<HexData>());
		return builder;
	}
	
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = giveBuilderNGW();
		builder = builder.registerTypeAdapter(GameWorld.class, new GameWorldDeserializer());
		return builder;
	}
}
