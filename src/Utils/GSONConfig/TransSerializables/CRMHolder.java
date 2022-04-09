// By Iacon1
// Created 04/09/2022
// Circular Map Holder

package Utils.GSONConfig.TransSerializables;

import java.util.Map;

public abstract class CRMHolder<H, T extends CRChild<H>> implements TransSerializable
{
	protected abstract Map<?, T> getMap();
	
	@Override public void preSerialize() {for (T t : getMap().values()) t.cutLink();}

	@Override public void postDeserialize() {for (T t : getMap().values()) t.setLink((H) this);}
}
