// By Iacon1
// Created 04/26/2021
// For handling the connection client-side

package Server;

import Utils.*;
import GameEngine.DummyPlayer;
import GameEngine.GameInstance;
import GameEngine.GameWorld;
import GameEngine.PhysicalObject;
import GameEngine.PacketTypes.*;
import Net.ConnectionPairThread;

public class ClientHandlerThread extends ConnectionPairThread
{
	protected volatile GameInstance userEntity_; // Object the user is "possessing"
	protected static volatile Server parent_;
	
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
					if (packet == null || !packet.version.equals(MiscUtils.getVersion())) // We don't actually care about this client anymore
					{
						if (packet == null) Logging.logError("Client " + parentThread.socket_.getInetAddress() + " tried to connect<br> but failed to send a info packet.");
						else Logging.logError("Client " + parentThread.socket_.getInetAddress() + " tried to connect<br> but sent a bad packet.");
						parentThread.close();
					}
					else
					{
						Logging.logNotice("Client " + parentThread.socket_.getInetAddress() + " has connected.");
						parentThread.stateChange(login); // They're good, let's login
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
					
					Logging.logNotice("Sent info to " + parentThread.socket_.getInetAddress());
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
			}
			
			@Override
			public String processOutput(ClientHandlerThread parentThread)
			{
				parentThread.stateChange(mapScreen);
				parentThread.userEntity_ = new DummyPlayer();
				GameWorld.getWorld().getRootInstances().get(0).addChild(parentThread.userEntity_); // Adds a guy to the map
				((PhysicalObject) parentThread.userEntity_).setPos(2, 2, 0);
				return null; // TODO implement login
			}
		},
		
		mapScreen // Game play on map
		{
			@Override
			public void processInput(String input, ClientHandlerThread parentThread)
			{
				if (input != null)
				{
					Logging.logNotice("Client " + parentThread.socket_.getInetAddress() + " used command: \"" + input + "\"");
					GameWorld.getWorld().processCommand(parentThread.userEntity_, input);
				}
			}
			
			@Override
			public String processOutput(ClientHandlerThread parentThread)
			{
				GameDataPacket packet = new GameDataPacket();
				packet.viewWorld();
				return packet.toJSON();
			}		
		};
		
		public abstract void processInput(String input, ClientHandlerThread parentThread);
		public abstract String processOutput(ClientHandlerThread parentThread);
	}
	
	private volatile CurrentState currentState_; // Change this to change the state of the client
	
	public ClientHandlerThread()
	{
		currentState_ = CurrentState.checkClient;
	}
	public void setParent(Server parent)
	{
		parent_ = parent;
	}
	
	public void stateChange(CurrentState newState) // Resets both input & output threads to respect change
	{
		currentState_ = newState;
		runningI_ = false;
		runningO_ = false;
		try {Thread.sleep(100);}
		catch (Exception e) {Logging.logException(e);}
		runningI_ = true;
		runningO_ = true;
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
	@Override
	public void onClose()
	{
		Logging.logNotice("Client " + socket_.getInetAddress() + " has disconnected.");
	}

}
