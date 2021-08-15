// By Iacon1
// Created 4/20/2021
// Single-thread server
//Just instantiate and start; Use close to shut down

// http://tutorials.jenkov.com/java-multithreaded-servers/singlethreaded-server.html

package Net.Server;

import Utils.Instancer;
import Utils.Logging;
import Net.ConnectionPairThread;

import java.net.*;

public class ServerThread<P extends ConnectionPairThread> extends Thread
{
	protected int port_; // Port
	protected ServerSocket serverSocket_; // Socket for accepting new clients 
	protected boolean running_ = false; // Keep running?
	private Instancer<P> instancer_;

	public ServerThread(int port, P pairTemplate)
	{
		setName("MtO server Thread (" + getId() + ")");
		port_ = port;
		instancer_ = new Instancer<P>(pairTemplate);
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
		while (running_)
		{
			try
			{;
				Socket clientSocket = serverSocket_.accept();
				P pair = instancer_.getInstance();
				pair.setSocket(clientSocket);
				pair.start();
			}
			catch (Exception e) {Logging.logException(e);}
		}
	}	
	
	public void setPort(int port)
	{
		port_ = port;
	}
	public int getPort()
	{
		return port_;
	}
}
