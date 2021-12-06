// By Iacon1
// Created 11/23/2021
// Member of a serializable list

package Utils.GSONConfig.TransSerializables;

import java.util.Collection;

public interface CircularReferenceChild<T extends CircularReferenceChild<T, C>, C extends Collection<T>>
{
	/** Cuts the link with the list holder*/
	public void cutLink();
	/** Establishes the link with the list holder */
	public void establishLink(C collection);
}
