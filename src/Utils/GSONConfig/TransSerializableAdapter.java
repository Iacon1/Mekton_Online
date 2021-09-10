// By Iacon1
// Created 05/09/2021
// For abstract classes that may be in lists
// https://stackoverflow.com/questions/16872492/gson-and-abstract-superclasses-deserialization-issue

package Utils.GSONConfig;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import GameEngine.TransSerializable;
import Utils.Logging;
import Utils.GSONConfig.Delegation.AdapterDelegator;
import Utils.GSONConfig.Delegation.DelegatingAdapter;
import Utils.GSONConfig.Delegation.DelegatingFactory;

public class TransSerializableAdapter<T> extends DelegatingAdapter<T>
{
	public TransSerializableAdapter(AdapterDelegator delegator, TypeToken<T> type)
	{
		super(delegator, type);
	}

	@Override
	public void write(JsonWriter out, T value) throws IOException
	{
		((TransSerializable) value).preSerialize(); // We can assume T is extended from TransSerializable
		delegator_.getAdapter(type_).write(out, value); // Serialize
	}

	@Override
	public T read(JsonReader in) throws IOException
	{
		TypeAdapter<?> adapter = delegator_.getAdapter(type_);
		TransSerializable data = (TransSerializable) adapter.read(in); // Deserialize
		data.postDeserialize();
		
	    return (T) data;
	}
	
	public static class Factory extends DelegatingFactory<TransSerializable>
	{

		Factory()
		{
			super(TransSerializable.class);
		}

		@Override
		protected <J> DelegatingAdapter<J> getAdapter(AdapterDelegator delegator, TypeToken<J> type)
		{
			return new TransSerializableAdapter<J>(delegator, type);
		}
	}
}
