// By Iacon1
// Created 4/20/2021
// Constructed with a socket, runs two threads

package GameEngine.Net;

import Utils.Logging;
import Utils.SimpleTimer;
import Utils.StringEncrypter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.security.Key;

import GameEngine.Configurables.ConfigManager;

public abstract class ConnectionPairThread extends Thread
{
	protected boolean mono; // true = Mono-thread, false = tri-thread
	private Thread inThread; // Thread for getting & processing input
	private Thread outThread; // Thread for processing & sending output
	
	private DataInputStream inStream;
	private DataOutputStream outStream;
	
	protected Socket socket; // Socket for communicating
	
	protected volatile boolean running; // Main
	protected volatile boolean runningI; // Input
	protected volatile boolean runningO; // Output
	
	private static String noMsg = "NULL"; // For maintaining a connection during silence
	
	private volatile transient Key key; // Transient for security
	private volatile boolean encrypt = false;
	
	protected String getConnectedIP()
	{
		if (socket != null) return socket.getInetAddress().toString();
		else return null;
	}
	
	private String getInput() // Get the input
	{
		try
		{
			if (inStream.available() == 0) return null;
			else return inStream.readUTF();
		}
		catch (EOFException e) {close(); return null;}
		catch (IOException e) {close(); return null;}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	private void sendOutput(String output)
	{	
		try
		{
			if (output == null) outStream.writeUTF(noMsg); //return;
			outStream.writeUTF(output);
		}
		catch (IOException e) {close();}
		catch (Exception e) {Logging.logException(e);}
	}
	
	public abstract void processInput(String input) throws Exception; // Handle the input
	public abstract String processOutput() throws Exception; // Handle the output
	public abstract void onClose(); // When closed
	
	private boolean inputRun(boolean loop) // Run by inThread; Returns false if no input was gotten
	{
		while (running && runningI)
		{
			
			String input = getInput();
			
			if (input == null) return false;
			
			if (key != null && encrypt)
				try {input = StringEncrypter.decrypt(input, key);} catch (Exception e)
				{Logging.logException(e); return false;}
			
			if (input == noMsg && loop) continue;
			else if (input == noMsg) return true;
			
			try {processInput(input);}
			catch (Exception e) {Logging.logException(e);}

			if (loop && ConfigManager.getCheckCapI() != 0)
			{
				try {Thread.sleep(1000 / ConfigManager.getCheckCapI());}
				catch (Exception e) {Logging.logException(e);}
			}
			else if (!loop) return true;
		}
		return true;
	}
	private void outputRun(boolean loop) // Run by outThread
	{
		while (running && runningO)
		{			
			String output = null;
			try {output = processOutput();}
			catch (Exception e) {Logging.logException(e);}
			
			if (key != null && encrypt && output != null)
				try {output = StringEncrypter.encrypt(output, key);} catch (Exception e)
				{Logging.logException(e); return;}
			
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
			if (running) close();
			this.socket = socket;
			
			inStream = new DataInputStream(this.socket.getInputStream());
			outStream = new DataOutputStream(this.socket.getOutputStream());
		}
		catch (Exception e) {Logging.logException(e);}		
	}
	public Socket getSocket()
	{
		return socket;
	}
	public void setKey(Key key)
	{
		this.key = key;
	}
	public void clearKey()
	{
		setKey(null);
	}
	public void setEncrypt(boolean encrypt)
	{
		this.encrypt = encrypt;
	}
	public ConnectionPairThread()
	{
		mono = ConfigManager.getMonoThread();
		if (mono) setName("MtO Connection Pair Thread (" + getId() + ")");
		else setName("MtO Connection Pair Main Thread (" + getId() + ")");
	}
	public ConnectionPairThread(Socket socket)
	{
		mono = ConfigManager.getMonoThread();
		if (mono) setName("MtO Connection Pair Thread (" + getId() + ")");
		else setName("MtO Connection Pair Main Thread (" + getId() + ")");
		setSocket(socket);
	}
	
	public void runFunc()
	{
		if (socket == null || socket.isClosed()) close(); // SHUT IT DOWN!
		
		if (mono)
		{
			SimpleTimer timer = new SimpleTimer();
			timer.start();
			if (runningI) inputRun(false);
			if (runningO) outputRun(false);
			if (ConfigManager.getCheckCapM() != 0)
			{
				try
				{
					Thread.sleep(Math.max(0, 1000 / ConfigManager.getCheckCapM() - timer.stopTime())); // Accounts for lag in an effort to maintain consistent framerate.
				}
				catch (Exception e) {Logging.logException(e);}
			}
			else timer.stopTime();
		}
		else
		{
			if ((inThread == null || !inThread.isAlive()) && runningI)
			{
				inThread = new Thread(() ->  {inputRun(true);});
				inThread.setName("MtO Connection Pair Input Thread (" + getId() + ")");
				inThread.start();
			}
			if ((outThread == null || !outThread.isAlive()) && runningO)
			{
				outThread = new Thread(() ->  {outputRun(true);});
				outThread.setName("MtO Connection Pair Output Thread (" + getId() + ")");
				outThread.start();
			}
		}
	}
	public void run() // Loop
	{
		running = true;
		runningI = true;
		runningO = true;
		while (running) runFunc();
	}
	
	protected boolean isInputRunning() // Returns whether it *should* be alive *or* whether it *is* alive
	{
		if (mono) return false;
		if (inThread == null) return runningI;
		else return runningI || inThread.isAlive();
	}
	protected boolean isOutputRunning() // Returns whether it *should* be alive *or* whether it *is* alive
	{
		if (mono) return false;
		if (outThread == null) return runningO;
		else return runningO || outThread.isAlive();
	}
	public void close()
	{
		running = false;
		runningI = false;
		runningO = false;
		
		try {if (socket == null || socket.isClosed()) socket.close();}
		catch (Exception e) {Logging.logException(e);}
		
		onClose();
	}
}
