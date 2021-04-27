// By Iacon1
// Created 4/20/2021
// Client test

package Net.Client;

import Utils.Logging;
import Net.ConnectionPairThread;

import java.net.Socket;

public class Client<P extends ConnectionPairThread>
{
	protected P pair_;
	
	public Client(String address, int port, P pair)
	{
		Socket socket = null;
		try {socket = new Socket(address, port);}
		catch (Exception e) {Logging.logException(e);}
		
		pair_ = pair;
		pair_.setSocket(socket);
	}
	
	public void run()
	{
		pair_.start();
	}
}
