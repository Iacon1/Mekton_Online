// By Iacon1
// Created 04/09/2022
// Circular Reference Collection Holder.

package Utils.GSONConfig.TransSerializables;

import java.util.Collection;

public abstract class CRCHolder<H, T extends CRChild<H>> implements TransSerializable
{
	protected abstract Collection<T> getCollection();
	
	@Override public void preSerialize() {for (T t : getCollection()) t.cutLink();}

	@Override public void postDeserialize() {for (T t : getCollection()) t.setLink((H) this);}

	

}
