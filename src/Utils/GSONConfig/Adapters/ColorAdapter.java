// By Iacon1
// Created 11/23/2021
//

package Utils.GSONConfig.Adapters;

import java.awt.Color;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import Utils.MiscUtils;

public class ColorAdapter extends TypeAdapter<Color>
{
	private static final int NPC = 2; // Nibbles per channel
	
	@Override
	public Color read(JsonReader in) throws IOException
	{
	    return Color.getColor(in.nextString());
	}
	
	@Override
	public void write(JsonWriter out, Color value) throws IOException
	{
		out.value(Integer.toHexString(value.getRGB()));
	}

	
}
