// By Iacon1
// Created 09/03/2021
// Adapter that runs and then runs the adapter under it

package Utils.GSONConfig.Delegation;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

public abstract class DelegatingAdapter<T> extends TypeAdapter<T>
{
	protected AdapterDelegator delegator;
	protected TypeToken<T> type;
	
	public DelegatingAdapter(AdapterDelegator delegator, TypeToken<T> type)
	{
		this.delegator = delegator;
		this.type = type;
	}
}
