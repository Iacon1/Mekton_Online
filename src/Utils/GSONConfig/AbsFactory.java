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

import Utils.Logging;

public class AbsFactory<T> implements TypeAdapterFactory
{	
	private Class<T> superClass_;
	
	private class AdapterDelegator // Simply cleans up some of the code in CustomJAdapter by handling delegation
	{
		private Gson gson_;
		private TypeAdapterFactory factory_;
		
		public AdapterDelegator(Gson gson, TypeAdapterFactory factory)
		{
			gson_ = gson;
			factory_ = factory;
		}
		
		public <J> TypeAdapter<J> getAdapter(TypeToken<J> type)
		{
			return gson_.getDelegateAdapter(factory_, type);
		}
		
		@SuppressWarnings("unchecked")
		public <J> TypeAdapter<J> getAdapter(String typeName)
		{
			try
			{
				TypeToken<J> type = (TypeToken<J>) TypeToken.get(Class.forName(typeName));
				return gson_.getDelegateAdapter(factory_, type);
			}
			catch (Exception e) {Logging.logException(e); return null;}
		}
	}
	
	private class CustomJAdapter<J> extends TypeAdapter<J>
	{
		private AdapterDelegator delegator_;
		private TypeToken<J> type_;
		
		public CustomJAdapter(AdapterDelegator delegator, TypeToken<J> type)
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
				data = (J) adapter.read(in);
				break;
			}
			in.endObject();
			
		    return data;
		}

		@Override
		public void write(JsonWriter out, J value) throws IOException
		{
			String type = value.getClass().getCanonicalName();
			String data = delegator_.getAdapter(type_).toJson(value);
			
			out.beginObject();
			out.name("type").value(type);
			out.name("data").jsonValue(data);
			out.endObject();
		}
	}
	
	AbsFactory(Class<T> superClass)
	{
		superClass_ = superClass;
	}
	
	@SuppressWarnings("unchecked")
	public <J> TypeAdapter<J> create(Gson gson, TypeToken<J> type)
	{
		Class<J> inClass = (Class<J>) type.getRawType();
		AdapterDelegator delegator = new AdapterDelegator(gson, this);
		
		if (!superClass_.isAssignableFrom(inClass)) // This adapter doesn't apply!
			return null;
		else if (superClass_.getCanonicalName().equals(inClass.getCanonicalName())) // Use base adapter
		{
			return new CustomJAdapter<J>(delegator, type);
		}
		else // Child class
		{
			return new CustomJAdapter<J>(delegator, type);
		}
	}
}
