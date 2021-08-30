// By Iacon1
// Created 06/15/2021
// A connection pair thread with thread states

package Net;

public abstract class StatefulConnectionPairThread extends ConnectionPairThread
{

	@SuppressWarnings("rawtypes")
	private volatile ThreadState currentState_; // The state we're currently on
	@SuppressWarnings("rawtypes")
	private volatile ThreadState nextState_; // The state we are switching to; Null if we're not switching right now
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initState(ThreadState state)
	{
		currentState_ = state;
		currentState_.onEnter(this);
	}

	@SuppressWarnings("rawtypes")
	public void queueStateChange(ThreadState nextState) // Queues a state change. We'll get to that when we can!
	{
		nextState_ = nextState;
	}
	@SuppressWarnings("unchecked")
	public void changeState() // Changes the state
	{
		runningI_ = false;
		runningO_ = false;
		while (isInputRunning());
		while (isOutputRunning());
		currentState_ = nextState_;
		currentState_.onEnter(this);
		runningI_ = true;
		runningO_ = true;
	}
	
	@SuppressWarnings("rawtypes")
	public ThreadState getCurrentState()
	{
		return currentState_;
	}
	
	@Override
	public void runFunc()
	{
		super.runFunc();
		if (nextState_ != null)
		{
			changeState();
			nextState_ = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void processInput(String input)
	{
		currentState_.processInput(input, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String processOutput()
	{
		return currentState_.processOutput(this);
	}
}
