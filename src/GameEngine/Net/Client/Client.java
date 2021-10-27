// By Iacon1
// Created 4/20/2021
// Client test

package GameEngine.Net.Client;

import Utils.Logging;

import java.net.Socket;

import GameEngine.Net.ConnectionPairThread;

public class Client<P extends ConnectionPairThread>
{
	protected P pair;
	
	public Client(String address, int port, P pair)
	{
		Socket socket = null;
		try {socket = new Socket(address, port);}
		catch (Exception e) {Logging.logException(e);}
		
		this.pair = pair;
		this.pair.setSocket(socket);
	}
	
	public void run()
	{
		pair.start();
	}
}
