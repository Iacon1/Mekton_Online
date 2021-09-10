// By Iacon1
// Created 09/03/2021
// For custom adapters that need delegation

package Utils.GSONConfig.Delegation;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public abstract class DelegatingFactory<T> implements TypeAdapterFactory
{	
	private Class<T> oClass_;

	protected abstract <J> DelegatingAdapter<J> getAdapter(AdapterDelegator delegator, TypeToken<J> type);
	
	protected DelegatingFactory(Class<T> oClass)
	{
		oClass_ = oClass;
	}

	public <J> TypeAdapter<J> create(Gson gson, TypeToken<J> type)
	{
		Class<J> inClass = (Class<J>) type.getRawType();
		AdapterDelegator delegator = new AdapterDelegator(gson, this);

		if (oClass_.isAssignableFrom(inClass))
		{
			return getAdapter(delegator, type);
		}
		else return null;
	}
}
