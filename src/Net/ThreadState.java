// By Iacon1
// Created 6/13/2021
// Thread state

package Net;

public interface ThreadState<T extends ConnectionPairThread>
{
	public abstract void onEnter(T parentThread);
	public abstract void processInput(String input, T parentThread);
	public abstract String processOutput(T parentThread);
	
	public abstract StateFactory getFactory();
}
