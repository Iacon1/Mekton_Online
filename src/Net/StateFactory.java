// By Iacon1
// Created 06/16/2021
// Generates one of many states

package Net;

public interface StateFactory
{
	public abstract ThreadState getState(int id); // Returns a state based off some arbitrary list; 0 should be the initial state
	
	public abstract ThreadState getState(String className); // Returns the state with specified name, should it be part of this factory
}
