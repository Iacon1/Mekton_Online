// By Iacon1
// Created 11/23/2021
//

package Utils.GSONConfig.Adapters;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class KeyEventAdapter extends TypeAdapter<KeyEvent>
{
	private Component component; // Dummy
	
	public KeyEventAdapter()
	{
		super();
		component = new Container();
	}
	@Override
	public KeyEvent read(JsonReader in) throws IOException
	{
		String[] s = in.nextString().split("\\|");
		return new KeyEvent(component,
				Integer.parseInt(s[0]),
				Long.parseLong(s[1]),
				Integer.parseInt(s[2]),
				Integer.parseInt(s[3]),
				s[4].charAt(0));
	}
	
	@Override
	public void write(JsonWriter out, KeyEvent value) throws IOException
	{
		String output = Integer.toString(value.getID());
		output += "|" + Long.toString(value.getWhen());
		output += "|" + Integer.toString(value.getModifiersEx());
		output += "|" + Integer.toString(value.getKeyCode());
		output += "|" + value.getKeyChar();
		out.value(output);
	}

	
}
