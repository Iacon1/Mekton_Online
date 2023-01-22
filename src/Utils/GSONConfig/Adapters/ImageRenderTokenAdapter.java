// By Iacon1
// Created 11/23/2021
//

package Utils.GSONConfig.Adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import GameEngine.Graphics.RenderTokens.ImageRenderToken;

public class ImageRenderTokenAdapter extends TypeAdapter<ImageRenderToken>
{
	@Override
	public ImageRenderToken read(JsonReader in) throws IOException
	{
		return ImageRenderToken.fromString(in.nextString());
	}
	
	@Override
	public void write(JsonWriter out, ImageRenderToken value) throws IOException
	{
		out.value(value.toString());
	}

	
}
