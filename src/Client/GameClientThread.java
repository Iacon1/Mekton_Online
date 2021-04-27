// By Iacon1
// Created 4/21/2021
// Updates the game state and receives input to send to the server

package Client;

import java.net.Socket;

import Client.Frames.ConnectFailDialog;
import GameEngine.PacketTypes.ClientInfoPacket;
import GameEngine.PacketTypes.ServerInfoPacket;
import Net.ConnectionPairThread;

import Utils.MiscUtils;

public class GameClientThread extends ConnectionPairThread
{
	private enum CurrentState
	{
		checkServer // Check the server; Make sure it's a compatible MtO server
		{
			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				ServerInfoPacket packet = new ServerInfoPacket();
				packet = (ServerInfoPacket) packet.fromJSON(input);
				parentThread.serverInfo_ = packet;
				if (parentThread.socket_ == null || packet == null || !packet.version.equals(MiscUtils.getVersion()))
					parentThread.currentState_ = badServer;
				else
					parentThread.currentState_ = login;
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				while (parentThread.currentState_ == checkServer);
				
				if (parentThread.currentState_ == login) // We decided to go forward!
				{
					ClientInfoPacket packet = new ClientInfoPacket();
					packet.version = MiscUtils.getVersion();
					return packet.toJSON();
				}
				else if (parentThread.currentState_ == badServer) return null; // We have nothing to say to them
				else return null; // ???
			}
		},
		
		badServer // Server wasn't online, wasn't an MtO server, or was the wrong version
		{
			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				if (parentThread.socket_ == null)
					ConnectFailDialog.main("Server wasn't found.");
				else if (parentThread.serverInfo_ == null)
					ConnectFailDialog.main("Server wasn't recognized.");
				else if (!parentThread.serverInfo_.version.equals(MiscUtils.getVersion()))
					ConnectFailDialog.main("Server was version " + parentThread.serverInfo_.version + ";<br> Should be " + MiscUtils.getVersion());
				parentThread.close();
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				return null; // We have nothing to say to them
			}
		},
		
		login // Logging in
		{
			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				parentThread.currentState_ = mapScreen;
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				return null; // TODO implement login
			}
		},
		
		mapScreen // Game play on map
		{
			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				parentThread.currentState_ = null;
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				return null; // TODO implement login
			}		
		};
		
		
		public abstract void processInput(String input, GameClientThread parentThread);
		public abstract String processOutput(GameClientThread parentThread);
	}
	
	private CurrentState currentState_; // Change this to change the state of the client
	private ServerInfoPacket serverInfo_; // The server info we received
	
	
	public GameClientThread()
	{
		super();
		currentState_ = CurrentState.checkServer;
	}
	
	@Override
	public void processInput(String input)
	{
		currentState_.processInput(input, this);
	}
	@Override
	public String processOutput()
	{
		return currentState_.processOutput(this);
	}

}
