// By Iacon1
// Created 4/20/2021
// Chat client

package TestChat;

import java.util.*;

import Net.ConnectionPairThread;
import Utils.Logging;

public class ChatClientPair extends ConnectionPairThread
{
	private Scanner scanner_;
	
	public ChatClientPair()
	{
		scanner_ = new Scanner(System.in);
	}
	@Override
	public void processInput(String input)
	{
		Logging.logRaw("Server: " + input);
	}

	@Override
	public String processOutput()
	{
		try
		{
			String line = scanner_.nextLine();
			Logging.logRaw("Client: " + line);
			return line;
		}
		catch (NoSuchElementException e) {return null;}
		catch (Exception e) {Logging.logException(e); return null;}
	}

}
