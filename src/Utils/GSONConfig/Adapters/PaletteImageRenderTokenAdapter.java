// By Iacon1
// Created 11/23/2021
//

package Utils.GSONConfig.Adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import GameEngine.Graphics.RenderTokens.ImageRenderToken;
import GameEngine.Graphics.RenderTokens.PaletteImageRenderToken;

public class PaletteImageRenderTokenAdapter extends TypeAdapter<PaletteImageRenderToken>
{
	@Override
	public PaletteImageRenderToken read(JsonReader in) throws IOException
	{
		return PaletteImageRenderToken.fromString(in.nextString());
	}
	
	@Override
	public void write(JsonWriter out, PaletteImageRenderToken value) throws IOException
	{
		out.value(value.toString());
	}

	
}
