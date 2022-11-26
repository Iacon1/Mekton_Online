// By Iacon1
// Created 11/23/2021
//

package Utils.GSONConfig.Adapters;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class MouseEventAdapter extends TypeAdapter<MouseEvent>
{
	private Component component; // Dummy
	
	public MouseEventAdapter()
	{
		super();
		component = new Container();
	}
	
	@Override
	public MouseEvent read(JsonReader in) throws IOException
	{
		String[] s = in.nextString().split("\\|");
		return new MouseEvent(component,
				Integer.parseInt(s[0]),
				Long.parseLong(s[1]),
				Integer.parseInt(s[2]),
				Integer.parseInt(s[3]),
				Integer.parseInt(s[4]), 
				Integer.parseInt(s[5]),
				false,
				Integer.parseInt(s[6]));
	}
	
	@Override
	public void write(JsonWriter out, MouseEvent value) throws IOException
	{
		String output = Integer.toString(value.getID());
		output += "|" + Long.toString(value.getWhen());
		output += "|" + Integer.toString(value.getModifiersEx());
		output += "|" + Integer.toString(value.getX());
		output += "|" + Integer.toString(value.getY());
		output += "|" + Integer.toString(value.getClickCount());
		output += "|" + Integer.toString(value.getButton());
		out.value(output);
	}

	
}
