// By Iacon1
// Created 09/03/2021
//

package Utils.GSONConfig.Delegation;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

public abstract class DelegatingAdapter<T> extends TypeAdapter<T>
{
	protected AdapterDelegator delegator_;
	protected TypeToken<T> type_;
	
	public DelegatingAdapter(AdapterDelegator delegator, TypeToken<T> type)
	{
		delegator_ = delegator;
		type_ = type;
	}
}
