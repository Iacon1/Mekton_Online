// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.google.gson.GsonBuilder;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GSONModule;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Graphics.RenderQueue.RenderToken;
import GameEngine.Graphics.RenderTokens.ImageRenderToken;
import GameEngine.Graphics.RenderTokens.PaletteImageRenderToken;
import GameEngine.Server.Account;
import Utils.GSONConfig.Adapters.ColorAdapter;
import Utils.GSONConfig.Adapters.ImageRenderTokenAdapter;
import Utils.GSONConfig.Adapters.KeyEventAdapter;
import Utils.GSONConfig.Adapters.MouseEventAdapter;
import Utils.GSONConfig.Adapters.PaletteImageRenderTokenAdapter;
import Utils.GSONConfig.TransSerializables.TransSerializableAdapter;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new TransSerializableAdapter.Factory());
		builder.registerTypeAdapterFactory(new AbsFactory<GameEntity>(GameEntity.class));
		builder.registerTypeAdapterFactory(new AbsFactory<Account>(Account.class));
		
		// Render tokens
		builder.registerTypeAdapter(ImageRenderToken.class, new ImageRenderTokenAdapter());
		builder.registerTypeAdapter(PaletteImageRenderToken.class, new PaletteImageRenderTokenAdapter());
		builder.registerTypeAdapterFactory(new AbsFactory<RenderToken>(RenderToken.class));
		
		builder.registerTypeAdapter(Color.class, new ColorAdapter());
		
		// Input events
		builder.registerTypeAdapter(MouseEvent.class, new MouseEventAdapter());
		builder.registerTypeAdapter(KeyEvent.class, new KeyEventAdapter());
		builder.registerTypeAdapterFactory(new AbsFactory<InputEvent>(InputEvent.class));
		
		GSONModule module = ModuleManager.getHighestOfType(GSONModule.class);
		if (module != null) module.addToBuilder(builder);
		
		builder.enableComplexMapKeySerialization();
		
		return builder;
	}
}
