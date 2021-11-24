// By Iacon1
// Created 09/02/2021
// preSerialize runs before serialization
// postSerialize runs after deserialization

package Utils.GSONConfig.TransSerializables;

public interface TransSerializable
{
	/** Runs before serialization. */
	public void preSerialize();
	/** Runs after serialization. */
	public void postDeserialize();
}
