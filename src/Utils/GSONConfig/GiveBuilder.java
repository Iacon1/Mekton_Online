// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import java.awt.Color;

import com.google.gson.GsonBuilder;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GSONModule;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Graphics.RenderQueue.RenderToken;
import GameEngine.Graphics.RenderTokens.ImageRenderToken;
import GameEngine.Server.Account;
import Utils.GSONConfig.Adapters.ColorAdapter;
import Utils.GSONConfig.Adapters.ImageRenderTokenAdapter;
import Utils.GSONConfig.TransSerializables.TransSerializableAdapter;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new TransSerializableAdapter.Factory());
		builder.registerTypeAdapterFactory(new AbsFactory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Account>(Account.class));
		builder.registerTypeAdapter(ImageRenderToken.class, new ImageRenderTokenAdapter());
		builder.registerTypeAdapterFactory(new AbsFactory<RenderToken>(RenderToken.class));
		builder.registerTypeAdapter(Color.class, new ColorAdapter());
		
		GSONModule module = ModuleManager.getHighestOfType(GSONModule.class);
		if (module != null) module.addToBuilder(builder);
		
		builder.enableComplexMapKeySerialization();
		
		return builder;
	}
}
