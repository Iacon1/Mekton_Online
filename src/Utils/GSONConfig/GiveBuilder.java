// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;
import GameEngine.GameInfo;
import GameEngine.TransSerializable;
import Modules.MektonCore.HexData;
import Server.Account;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapterFactory(new AbsFactory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Account>(Account.class));
		builder.registerTypeAdapterFactory(new AbsFactory<HexData>(HexData.class));
		
		builder.registerTypeAdapterFactory(new TransSerializableAdapter.Factory()); // Issue is that instances
		builder.registerTypeAdapterFactory(new GameWorldAdapter.Factory());
		builder.enableComplexMapKeySerialization();
		
		return builder;
	}
}
