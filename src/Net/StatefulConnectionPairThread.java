// By Iacon1
// Created 06/15/2021
// A connection pair thread with thread states

package Net;

public abstract class StatefulConnectionPairThread extends ConnectionPairThread
{

	private volatile ThreadState currentState_; // The state we're currently on
	private volatile ThreadState nextState_; // The state we are switching to; Null if we're not switching right now

	public void initState(ThreadState state)
	{
		currentState_ = state;
		currentState_.onEnter(this);
	}

	public void queueStateChange(ThreadState nextState) // Queues a state change. We'll get to that when we can!
	{
		nextState_ = nextState;
	}
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

	@Override
	public void processInput(String input)
	{
		if (mono_) currentState_.processInputMono(input, this);
		else currentState_.processInputTrio(input, this);
	}

	@Override
	public String processOutput()
	{
		if (mono_) return currentState_.processOutputMono(this);
		else return currentState_.processOutputTrio(this);
	}
}
