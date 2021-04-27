// By Iacon1
// Created 4/20/2021
// Constructed with a socket, runs two threads

package Net;

import Utils.Logging;

import java.net.*;

import java.io.*;

public abstract class ConnectionPairThread extends Thread
{
	private Thread inThread_; // Thread for getting & processing input
	private Thread outThread_; // Thread for processing & sending output
	
	private DataInputStream inStream_;
	private DataOutputStream outStream_;
	
	protected Socket socket_; // Socket for communicating
	
	protected volatile boolean running_;

	private String getInput() // Get the input
	{
		try {return inStream_.readUTF();}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	private void sendOutput(String output)
	{
		try
		{
			if (output == null) return;
			outStream_.writeUTF(output);
		}
		catch (Exception e) {Logging.logException(e);}
	}
	
	public abstract void processInput(String input); // Handle the input
	public abstract String processOutput(); // Handle the output
	
	private void inputRun() // Run by inThread_
	{
		while (running_)
		{String input = getInput();
			processInput(input);
		}
	}
	private void outputRun() // Run by outThread_
	{
		while (running_)
		{
			String output = processOutput();
			sendOutput(output);
		}
	}
	
	public void setSocket(Socket socket)
	{
		try
		{
			close();
			socket_ = socket;
			
			inStream_ = new DataInputStream(socket_.getInputStream());
			outStream_ = new DataOutputStream(socket_.getOutputStream());
		}
		catch (Exception e) {Logging.logException(e);}		
	}
	
	public ConnectionPairThread()
	{
		setName("MtO Connection Pair Main Thread (" + getId() + ")");
	}
	public ConnectionPairThread(Socket socket)
	{
		setName("MtO Connection Pair Main Thread (" + getId() + ")");
		setSocket(socket);
	}
	
	public void run() // Opens two threads temporarily
	{
		running_ = true;
		inThread_ = new Thread(() ->  {inputRun();});
		inThread_.setName("MtO Connection Pair Input Thread (" + getId() + ")");
		inThread_.start();
		
		outThread_ = new Thread(() ->  {outputRun();});
		outThread_.setName("MtO Connection Pair Output Thread (" + getId() + ")");
		outThread_.start();
	}
	public void close()
	{
		running_ = false;
	}
}
