// By Iacon1
// Created 04/26/2021
// For handling the connection client-side

package Server;

import Utils.*;

import GameEngine.PacketTypes.*;
import Net.ConnectionPairThread;

public class ClientHandlerThread extends ConnectionPairThread
{
	private enum CurrentState
	{
		checkClient
		{
			private boolean sent_ = false; // Only send once!
			@Override
			public void processInput(String input, ClientHandlerThread parentThread)
			{
				if (sent_)
				{
					ClientInfoPacket packet = new ClientInfoPacket();
					packet = (ClientInfoPacket) packet.fromJSON(input);
					if (packet == null || packet.version != MiscUtils.getVersion()) // We don't actually care about this client anymore
						parentThread.close();
					else
					{
						Logging.logNotice("Client " + parentThread.socket_.getInetAddress() + " has connected.");
						parentThread.currentState_ = login; // They're good, let's login
					}
				}
			}
			
			@Override
			public String processOutput(ClientHandlerThread parentThread)
			{
				if (!sent_)
				{
					ServerInfoPacket packet = new ServerInfoPacket();
					packet.serverName = parentThread.parent_.getName();
					packet.version = MiscUtils.getVersion();
					packet.resourceFolder = parentThread.parent_.getResourceFolder();
					
					if (parentThread.parent_.currentPlayers() >= parentThread.parent_.maxPlayers()) // We're full
					{
						packet.note = ServerInfoPacket.Note.full;
						parentThread.close(); // We don't need this connection any more
					}
					else packet.note = ServerInfoPacket.Note.good;
					sent_ = true;
					return packet.toJSON();
				}
				else return null; // We have nothing to say to them
			}
		},
		
		login // Logging in
		{
			@Override
			public void processInput(String input, ClientHandlerThread parentThread)
			{
				parentThread.currentState_ = mapScreen;
			}
			
			@Override
			public String processOutput(ClientHandlerThread parentThread)
			{
				return null; // TODO implement login
			}
		},
		
		mapScreen // Game play on map
		{
			@Override
			public void processInput(String input, ClientHandlerThread parentThread)
			{
				parentThread.currentState_ = null;
			}
			
			@Override
			public String processOutput(ClientHandlerThread parentThread)
			{
				return null; // TODO implement login
			}		
		};
		
		public abstract void processInput(String input, ClientHandlerThread parentThread);
		public abstract String processOutput(ClientHandlerThread parentThread);
	}
	
	private CurrentState currentState_; // Change this to change the state of the client
	
	protected static volatile Server parent_;
	
	public void setParent(Server parent)
	{
		parent_ = parent;
	}
	
	@Override
	public void processInput(String input)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String processOutput()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
