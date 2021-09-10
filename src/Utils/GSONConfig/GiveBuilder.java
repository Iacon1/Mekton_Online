// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;
import Modules.MektonCore.HexData;
import Server.Account;
import GameEngine.PacketTypes.Packet;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapterFactory(new TransSerializableAdapter.Factory());
		
		builder.registerTypeAdapterFactory(new AbsAdapter.Factory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsAdapter.Factory<Account>(Account.class));
		builder.registerTypeAdapterFactory(new AbsAdapter.Factory<HexData>(HexData.class));
		//builder.registerTypeAdapterFactory(new AbsAdapter.Factory<Packet>(Packet.class));
		
		builder.enableComplexMapKeySerialization();
		builder.serializeNulls();
		
		return builder;
	}
}
