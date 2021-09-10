// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import com.google.gson.GsonBuilder;

import GameEngine.GameEntity;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GSONModule;
import Server.Account;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapterFactory(new AbsFactory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Account>(Account.class));
		
		builder.registerTypeAdapterFactory(new TransSerializableAdapter.Factory());
		
		GSONModule module = ModuleManager.getHighestOfType(GSONModule.class);
		if (module != null) module.addToBuilder(builder);
		
		builder.enableComplexMapKeySerialization();
		
		return builder;
	}
}
