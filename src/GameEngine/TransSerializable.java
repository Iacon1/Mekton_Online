// By Iacon1
// Created 09/02/2021
// preSerialize runs before serialization
// postSerialize runs after deserialization

package GameEngine;

public interface TransSerializable
{
	public void preSerialize();
	public void postDeserialize();
}
