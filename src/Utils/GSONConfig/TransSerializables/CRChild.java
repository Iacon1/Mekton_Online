// By Iacon1
// Created 04/09/2022
// Circular Reference Child

package Utils.GSONConfig.TransSerializables;

public interface CRChild<H>
{
	public void setLink(H holder);
	public void cutLink();
}
