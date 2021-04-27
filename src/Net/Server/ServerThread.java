// By Iacon1
// Created 4/20/2021
// Single-thread server
//Just instantiate and start; Use close to shut down

// http://tutorials.jenkov.com/java-multithreaded-servers/singlethreaded-server.html

package Net.Server;

import Utils.Logging;
import Net.ConnectionPairThread;

import java.net.*;

public class ServerThread<P extends ConnectionPairThread> extends Thread
{
	protected int port_; // Port
	protected ServerSocket serverSocket_; // Socket for accepting new clients 
	protected boolean running_ = false; // Keep running?
	private P pairTemplate_;

	public ServerThread(int port, P pairTemplate)
	{
		port_ = port;
		pairTemplate_ = pairTemplate;
	}
	
	public void open() // Opens the server socket
	{
		try
		{
			serverSocket_ = new ServerSocket(port_);
			running_ = true;
		}
		catch (Exception e) {Logging.logException(e);}
	}
	public void close()
	{
		running_ = false;
		try {serverSocket_.close();}
		catch (Exception e) {Logging.logException(e);}
	}

	public void run()
	{
		open();
		while (running_)
		{
			Socket clientSocket;
			try
			{
				clientSocket = serverSocket_.accept();
				
				P pair = (P) pairTemplate_.getClass().getConstructor().newInstance();
				pair.setSocket(clientSocket);
				pair.start();
			}
			catch (Exception e) {Logging.logException(e);}
		}
	}
	
}
