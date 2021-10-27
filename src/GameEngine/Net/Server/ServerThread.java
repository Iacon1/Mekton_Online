// By Iacon1
// Created 4/20/2021
// Single-thread server
//Just instantiate and start; Use close to shut down

// http://tutorials.jenkov.com/java-multithreaded-servers/singlethreaded-server.html

package GameEngine.Net.Server;

import Utils.Logging;

import java.net.*;
import java.util.function.Supplier;

import GameEngine.Net.ConnectionPairThread;

public class ServerThread<P extends ConnectionPairThread> extends Thread
{
	protected int port; // Port
	protected ServerSocket serverSocket; // Socket for accepting new clients 
	protected boolean running = false; // Keep running?
	private Supplier<P> supplier;

	public ServerThread(int port, Supplier<P> supplier)
	{
		setName("MtO server Thread (" + getId() + ")");
		this.port = port;
		this.supplier = supplier;
	}

	public void open() // Opens the server socket
	{
		try
		{
			serverSocket = new ServerSocket(port);
			running = true;
		}
		catch (Exception e) {Logging.logException(e);}
	}
	public void close()
	{
		running = false;
		try {serverSocket.close();}
		catch (Exception e) {Logging.logException(e);}
	}

	public void run()
	{
		while (running)
		{
			try
			{;
				Socket clientSocket = serverSocket.accept();
				P pair = supplier.get();
				pair.setSocket(clientSocket);
				pair.start();
			}
			catch (Exception e) {Logging.logException(e);}
		}
	}	
	
	public void setPort(int port)
	{
		this.port = port;
	}
	public int getPort()
	{
		return port;
	}
}
