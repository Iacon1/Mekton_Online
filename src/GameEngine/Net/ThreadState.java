// By Iacon1
// Created 6/13/2021
// Thread state
// Keep in mind that mono-thread assures processOutput will always run exactly once after the last processInput
// Whereas in trio-thread they will run simultaneously (ish)

package GameEngine.Net;

public interface ThreadState<T extends ConnectionPairThread>
{
	public abstract void onEnter(T parentThread);
	
	public abstract void processInput(String input, T parentThread); // Can have totally different functions per threading type
	public abstract String processOutput(T parentThread);
	
	public abstract StateFactory getFactory();
}
