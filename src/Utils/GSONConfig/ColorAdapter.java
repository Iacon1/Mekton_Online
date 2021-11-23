// By Iacon1
// Created 11/23/2021
//

package Utils.GSONConfig;

import java.awt.Color;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ColorAdapter extends TypeAdapter<Color>
{
	@Override
	public Color read(JsonReader in) throws IOException
	{
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		
		in.beginObject();
		while (in.hasNext()) switch (in.nextName())
		{
		case "red": r = in.nextInt(); break;
		case "green": g = in.nextInt(); break;
		case "blue": b = in.nextInt(); break;
		case "alpha": a = in.nextInt(); break;
		}
		in.endObject();
		
	    return new Color(r, g, b, a);
	}
	
	@Override
	public void write(JsonWriter out, Color value) throws IOException
	{
		out.beginObject();
		out.name("red").value(value.getRed());
		out.name("green").value(value.getGreen());
		out.name("blue").value(value.getBlue());
		out.name("alpha").value(value.getAlpha());
		out.endObject();
	}

	
}
