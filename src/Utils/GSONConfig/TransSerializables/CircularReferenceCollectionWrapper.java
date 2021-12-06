// By Iacon1
// Created 11/23/2021
/* Contains a serializable double-linked (i. e. members contain a reference to this list) list.
   Normally something like this would cause a stackoverflow but this fixes it by cutting the
   reverse link (presumably; Up to the implementation of SerializableDLListMember) and rebuilding
   it after serialization.
   
*/

package Utils.GSONConfig.TransSerializables;

import java.util.Collection;

public class CircularReferenceCollectionWrapper<T extends CircularReferenceChild<T, C>, C extends Collection<T>> implements TransSerializable
{
	C collection;
	public CircularReferenceCollectionWrapper(C collection)
	{
		this.collection = collection;
	}
	
	@Override
	public void preSerialize()
	{
		collection.forEach(child -> {child.cutLink();});
	}

	@Override
	public void postDeserialize()
	{
		collection.forEach(child -> {child.establishLink(collection);});
	}

	public C get()
	{
		return collection;
	}
}
