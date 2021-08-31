// By Iacon1
// Created 4/20/2021
// Constructed with a socket, runs two threads

package Net;

import Utils.Logging;

import java.net.*;

import GameEngine.Configurables.ConfigManager;

import java.io.*;

public abstract class ConnectionPairThread extends Thread
{
	protected boolean mono_; // true = Mono-thread, false = tri-thread
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
		try
		{
			if (inStream_.available() == 0) return null;
			else return inStream_.readUTF();
		}
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
	
	private void inputRun(boolean loop) // Run by inThread_
	{
		//Logging.logNotice("Thread started: " + Thread.currentThread().getName());
		while (running_ && runningI_)
		{
			
			String input = getInput();
			if (input == null && loop) continue; // TODO Maybe allow handling this?
			else if (input == null) return;
			
			try {processInput(input);}
			catch (Exception e) {Logging.logException(e);}

			if (loop && ConfigManager.getCheckCapI() != 0)
			{
				try {Thread.sleep(1000 / ConfigManager.getCheckCapI());}
				catch (Exception e) {Logging.logException(e);}
			}
			else if (!loop) return;
		}
	}
	private void outputRun(boolean loop) // Run by outThread_
	{
		long checkO, cap;
		//Logging.logNotice("Thread started: " + Thread.currentThread().getName());
		while (running_ && runningO_)
		{			
			String output = null;
			try {output = processOutput();}
			catch (Exception e) {Logging.logException(e);}
			
			if (output != null) sendOutput(output);
			
			if (loop && ConfigManager.getCheckCapO() != 0) 
			{
				try {Thread.sleep(1000 / ConfigManager.getCheckCapO());}
				catch (Exception e) {Logging.logException(e);}
			}
			else if (!loop) return;
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
	public Socket getSocket()
	{
		return socket_;
	}
	
	public ConnectionPairThread()
	{
		mono_ = ConfigManager.getMonoThread();
		if (mono_) setName("MtO Connection Pair Thread (" + getId() + ")");
		else setName("MtO Connection Pair Main Thread (" + getId() + ")");
	}
	public ConnectionPairThread(Socket socket)
	{
		mono_ = ConfigManager.getMonoThread();
		if (mono_) setName("MtO Connection Pair Thread (" + getId() + ")");
		else setName("MtO Connection Pair Main Thread (" + getId() + ")");
		setSocket(socket);
	}
	
	public void runFunc()
	{
		if (socket_ == null || socket_.isClosed()) close(); // SHUT IT DOWN!
		
		if (mono_)
		{
			if (runningI_) inputRun(false);
			if (runningO_) outputRun(false);
			if (ConfigManager.getCheckCapM() != 0)
			{
				try {Thread.sleep(1000 / ConfigManager.getCheckCapM());}
				catch (Exception e) {Logging.logException(e);}
			}
		}
		else
		{
			if ((inThread_ == null || !inThread_.isAlive()) && runningI_)
			{
				inThread_ = new Thread(() ->  {inputRun(true);});
				inThread_.setName("MtO Connection Pair Input Thread (" + getId() + ")");
				inThread_.start();
			}
			if ((outThread_ == null || !outThread_.isAlive()) && runningO_)
			{
				outThread_ = new Thread(() ->  {outputRun(true);});
				outThread_.setName("MtO Connection Pair Output Thread (" + getId() + ")");
				outThread_.start();
			}
		}
	}
	public void run() // Loop
	{
		running_ = true;
		runningI_ = true;
		runningO_ = true;
		while (running_) runFunc();
	}
	
	protected boolean isInputRunning() // Returns whether it *should* be alive *or* whether it *is* alive
	{
		if (mono_) return false;
		if (inThread_ == null) return runningI_;
		else return runningI_ || inThread_.isAlive();
	}
	protected boolean isOutputRunning() // Returns whether it *should* be alive *or* whether it *is* alive
	{
		if (mono_) return false;
		if (outThread_ == null) return runningO_;
		else return runningO_ || outThread_.isAlive();
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
