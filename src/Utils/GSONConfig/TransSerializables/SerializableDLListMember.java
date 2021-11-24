// By Iacon1
// Created 11/23/2021
// Member of a serializable list

package Utils.GSONConfig.TransSerializables;

public interface SerializableDLListMember<T>
{
	/** Cuts the link with the list holder*/
	public void cutLink();
	/** Establishes the link with the list holder */
	public void establishLink(T listHolder);
}
