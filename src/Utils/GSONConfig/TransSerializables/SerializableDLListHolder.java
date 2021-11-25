// By Iacon1
// Created 11/23/2021
/* Contains a serializable double-linked (i. e. members contain a reference to this list) list.
   Normally something like this would cause a stackoverflow but this fixes it by cutting the
   reverse link (presumably; Up to the implementation of SerializableDLListMember) and rebuilding
   it after serialization.
   
*/

package Utils.GSONConfig.TransSerializables;

import java.util.List;
import java.util.function.Supplier;

public class SerializableDLListHolder<T, M extends SerializableDLListMember<T>, L extends List<M>> implements TransSerializable
{
	protected L heldList;
	
	public SerializableDLListHolder(Supplier<L> constructor)
	{
		heldList = constructor.get();
	}
	@Override
	public void preSerialize()
	{
		for (int i = 0; i < heldList.size(); ++i)
		{
			if (heldList.get(i) != null) heldList.get(i).cutLink();
		}
	}

	@Override
	public void postDeserialize()
	{
		for (int i = 0; i < heldList.size(); ++i)
		{
			if (heldList.get(i) != null) heldList.get(i).establishLink((T) this);
		}
	}

}
