// By Iacon1
// Created 06/15/2021
// A connection pair thread with thread states

package GameEngine.Net;

public abstract class StatefulConnectionPairThread extends ConnectionPairThread
{

	private volatile ThreadState currentState; // The state we're currently on
	private volatile ThreadState nextState; // The state we are switching to; Null if we're not switching right now

	public void initState(ThreadState state)
	{
		currentState = state;
		currentState.onEnter(this);
	}

	public void queueStateChange(ThreadState nextState) // Queues a state change. We'll get to that when we can!
	{
		this.nextState = nextState;
	}
	public void changeState() // Changes the state
	{
		currentState = nextState;
		currentState.onEnter(this);
	}

	public ThreadState getCurrentState()
	{
		return currentState;
	}
	
	@Override
	public void runFunc()
	{
		super.runFunc();
		if (nextState != null)
		{
			changeState();
			nextState = null;
		}
	}

	@Override
	public void processInput(String input) {currentState.processInput(input, this);}

	@Override
	public String processOutput() {return currentState.processOutput(this);}
}
