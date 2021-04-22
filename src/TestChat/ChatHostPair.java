// By Iacon1
// Created 4/20/2021
// Server host test

package TestChat;

import Net.ConnectionPairThread;
import Utils.Logging;

public class ChatHostPair extends ConnectionPairThread
{
	private volatile boolean myTurn_ = false;
	
	@Override
	public void processInput(String input)
	{
		Logging.logRaw("Client: " + input);
		myTurn_ = true;
	}

	@Override
	public String processOutput()
	{
		if (myTurn_)
		{
			myTurn_ = false;
			Logging.logRaw("Server: Hello!");
			return "Hello!";
		}
		else
			return null;
	}

}
