// By Iacon1
// Created 09/06/2021
// Abstract Adapter

package Utils.GSONConfig;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import Utils.GSONConfig.Delegation.AdapterDelegator;
import Utils.GSONConfig.Delegation.DelegatingAdapter;
import Utils.GSONConfig.Delegation.DelegatingFactory;

public class AbsAdapter<T> extends DelegatingAdapter<T>
{

	public AbsAdapter(AdapterDelegator delegator, TypeToken<T> type)
	{
		super(delegator, type);
	}

	@Override
	public T read(JsonReader in) throws IOException
	{
		String type = null;
		T data = null;
		
		in.beginObject();
		while (in.hasNext()) switch (in.nextName())
		{
		case "type":
			type = in.nextString();
			break;
		case "data":
			TypeAdapter<?> adapter = delegator_.getAdapter(type);
			data = (T) adapter.read(in); // Deserialize
			break;
		}
		in.endObject();
		
	    return data;
	}

	@Override
	public void write(JsonWriter out, T value) throws IOException
	{
		String type = value.getClass().getCanonicalName();
		String data = delegator_.getAdapter(type_).toJson(value); // Serialize
		
		out.beginObject();
		out.name("type").value(type);
		out.name("data").jsonValue(data);
		out.endObject();
	}

	public static class Factory<C> extends DelegatingFactory<C>
	{
		protected Factory(Class<C> oClass)
		{
			super(oClass);
		}

		@Override
		protected <J> DelegatingAdapter<J> getAdapter(AdapterDelegator delegator, TypeToken<J> type)
		{
			return new AbsAdapter<J>(delegator, type);
		}
		
		
	}
}
