// By Iacon1
// Created 08/14/2021
// Like AbsAdapter, but is implicitly followed by all subclasses of a type too
// That is, AbsFactory<T> allows all subclasses of T to be deserialized *as* a T without losing their data

package Utils.GSONConfig;

import java.io.IOException;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import GameEngine.GameEntity;
import Utils.Logging;
import Utils.MiscUtils;
import Utils.GSONConfig.Delegation.AdapterDelegator;

public class AbsFactory<T> implements TypeAdapterFactory
{	
	private Class<T> superClass_;
	
	private class AbsAdapter<J> extends TypeAdapter<J>
	{
		private AdapterDelegator delegator_;
		private TypeToken<J> type_;
		
		public AbsAdapter(AdapterDelegator delegator, TypeToken<J> type)
		{
			delegator_ = delegator;
			type_ = type;
		}

		@SuppressWarnings("unchecked")
		@Override
		public J read(JsonReader in) throws IOException
		{
			String type = null;
			J data = null;
			
			in.beginObject();
			while (in.hasNext()) switch (in.nextName())
			{
			case "type":
				type = in.nextString();
				break;
			case "data":
				TypeAdapter<?> adapter = delegator_.getAdapter(type);
				data = (J) adapter.read(in); // Deserialize
				break;
			}
			in.endObject();
			
		    return data;
		}

		@Override
		public void write(JsonWriter out, J value) throws IOException
		{
			String type = MiscUtils.ClassToString(value.getClass());
			String data = delegator_.getAdapter(type_).toJson(value); // Serialize
			
			out.beginObject();
			out.name("type").value(type);
			out.name("data").jsonValue(data);
			out.endObject();
		}
	}
	
	public AbsFactory(Class<T> superClass)
	{
		superClass_ = superClass;
	}
	
	public <J> TypeAdapter<J> create(Gson gson, TypeToken<J> type)
	{
		Class<J> inClass = (Class<J>) type.getRawType();
		AdapterDelegator delegator = new AdapterDelegator(gson, this);
		
		if (!superClass_.isAssignableFrom(inClass)) // This adapter doesn't apply!
			return null;
		else
		{
			return new AbsAdapter<J>(delegator, type);
		}
	}
}
