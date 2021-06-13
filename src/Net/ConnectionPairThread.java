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
	
	protected volatile boolean running_; // Main
	protected volatile boolean runningI_; // Input
	protected volatile boolean runningO_; // Output
	
	protected String getConnectedIP()
	{
		if (socket_ != null) return socket_.getInetAddress().toString();
		else return null;
	}
	
	private String getInput() // Get the input
	{
		try {return inStream_.readUTF();}
		catch (EOFException e) {close(); return null;}
		catch (IOException e) {close(); return null;}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	private void sendOutput(String output)
	{
		try
		{
			if (output == null) return;
			outStream_.writeUTF(output);
		}
		catch (IOException e) {close();}
		catch (Exception e) {Logging.logException(e);}
	}
	
	public abstract void processInput(String input) throws Exception; // Handle the input
	public abstract String processOutput() throws Exception; // Handle the output
	public abstract void onClose(); // When closed
	
	private void inputRun() // Run by inThread_
	{
		while (running_ && runningI_)
		{
			String input = getInput();
			try {processInput(input);}
			catch (Exception e) {Logging.logException(e);}
		}
	}
	private void outputRun() // Run by outThread_
	{
		while (running_ && runningO_)
		{
			String output = null;
			try {output = processOutput();}
			catch (Exception e) {Logging.logException(e);}
			
			sendOutput(output);
		}
	}
	
	public void setSocket(Socket socket)
	{
		try
		{
			if (running_) close();
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
		runningI_ = true;
		runningO_ = true;
		while (running_)
		{
			if (socket_ == null || socket_.isClosed()) close(); // SHUT IT DOWN!
			
			if ((inThread_ == null || !inThread_.isAlive()) && runningI_)
			{
				inThread_ = new Thread(() ->  {inputRun();});
				inThread_.setName("MtO Connection Pair Input Thread (" + getId() + ")");
				inThread_.start();
			}
			if ((outThread_ == null || !outThread_.isAlive()) && runningO_)
			{
				outThread_ = new Thread(() ->  {outputRun();});
				outThread_.setName("MtO Connection Pair Output Thread (" + getId() + ")");
				outThread_.start();
			}
		}
	}
	
	public void close()
	{
		running_ = false;
		runningI_ = false;
		runningO_ = false;
		
		try {if (socket_ == null || socket_.isClosed()) socket_.close();}
		catch (Exception e) {Logging.logException(e);}
		
		onClose();
	}
}
