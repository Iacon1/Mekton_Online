// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import java.awt.Color;

import com.google.gson.GsonBuilder;

import GameEngine.Sprite;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GSONModule;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Server.Account;
import Utils.GSONConfig.TransSerializables.TransSerializableAdapter;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapterFactory(new AbsFactory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Account>(Account.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Sprite>(Sprite.class));
		builder.registerTypeAdapterFactory(new TransSerializableAdapter.Factory());
		builder.registerTypeAdapter(Color.class, new ColorAdapter());
		
		GSONModule module = ModuleManager.getHighestOfType(GSONModule.class);
		if (module != null) module.addToBuilder(builder);
		
		builder.enableComplexMapKeySerialization();
		
		return builder;
	}
}
