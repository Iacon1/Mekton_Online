// By Iacon1
// Created 06/15/2021
// A connection pair thread with thread states

package Net;

import Utils.Logging;

public abstract class StatefulConnectionPairThread extends ConnectionPairThread
{

	private volatile ThreadState currentState_;
	private volatile ThreadState nextState_;
	
	public void initState(ThreadState state)
	{
		currentState_ = state;
		currentState_.onEnter(this);
	}

	public void queueStateChange(ThreadState nextState)
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
		currentState_.processInput(input, this);
	}
	
	@Override
	public String processOutput()
	{
		return currentState_.processOutput(this);
	}
}
