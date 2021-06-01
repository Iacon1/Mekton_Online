// By Iacon1
// Created 04/26/2021
// For handling the connection client-side

package Server;

import Utils.*;
import Client.GameClientThread;
import Client.Frames.LoginDialog;
import GameEngine.CommandRunner;
import GameEngine.GameEntity;
import GameEngine.GameWorld;
import GameEngine.PhysicalObject;
import GameEngine.Configurables.ModuleManager;
import GameEngine.PacketTypes.*;
import Net.ConnectionPairThread;

public class ClientHandlerThread extends ConnectionPairThread
{
	protected volatile String username_ = null; // Client's account name
	
	protected Account getAccount()
	{
		return parent_.getAccount(username_);
	}
	protected GameEntity getUserEntity()
	{
		return GameEntity.getEntity(parent_.gameWorld_, parent_.getAccount(username_).possessee);
	}
	protected static volatile Server parent_;
	
	private enum CurrentState
	{
		checkClient
		{
			private boolean sent_ = false; // Only send once!
			@Override
			public void onEnter(ClientHandlerThread parentThread) {}
			
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
			private LoginFeedbackPacket feedbackPacket_;
			private boolean send_;
			
			@Override
			public void onEnter(ClientHandlerThread parentThread)
			{
				feedbackPacket_ = new LoginFeedbackPacket();
			}
			
			@Override
			public void processInput(String input, ClientHandlerThread parentThread)
			{
				if (send_) return; // Don't take more packets while still giving feedback on one
			
				LoginPacket packet = new LoginPacket();
				packet = (LoginPacket) packet.fromJSON(input);
				
				Account account = new Account();
				account.username = packet.username; 
				account.setHash(packet.password);
				
				if (packet.newUser)
				{
					if (ClientHandlerThread.parent_.getAccount(packet.username) != null) return; // Don't overwrite an old account!
					feedbackPacket_.successful = ClientHandlerThread.parent_.addAccount(account);
					if (feedbackPacket_.successful)
					{
						parentThread.username_ = account.username;
						Logging.logNotice("Client " + parentThread.socket_.getInetAddress() + " has made account \"" + parentThread.username_ + "\".");
						
						ModuleManager.makePlayer(ClientHandlerThread.parent_, account);
					}
				}
				else
				{
					feedbackPacket_.successful = ClientHandlerThread.parent_.login(packet.username, packet.password);
					if (feedbackPacket_.successful)
					{
						parentThread.username_ = account.username;
						Logging.logNotice("Client " + parentThread.socket_.getInetAddress() + " has logged in as account \"" + parentThread.username_ + "\".");
					}
				}
				
				send_ = true;
			}
			
			@Override
			public String processOutput(ClientHandlerThread parentThread)
			{
				if (send_)
					{
						send_ = false;
						if (feedbackPacket_.successful) parentThread.stateChange(mapScreen);
						return feedbackPacket_.toJSON();
					}
				else return null;
			}
		},
		
		mapScreen // Game play on map
		{
			@Override
			public void onEnter(ClientHandlerThread parentThread) {}
			
			@Override
			public void processInput(String input, ClientHandlerThread parentThread)
			{
				if (input != null)
				{
					Logging.logNotice("Client " + parentThread.getConnectedIP() + " used command: \"" + input + "\"");
					ClientHandlerThread.parent_.runCommand(parentThread.username_, input);
				}
			}
			
			@Override
			public String processOutput(ClientHandlerThread parentThread)
			{
				GameDataPacket packet = new GameDataPacket();
				packet.viewWorld(parentThread.parent_.gameWorld_);
				return packet.toJSON();
			}		
		};
		
		public abstract void onEnter(ClientHandlerThread parentThread);
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
		currentState_.onEnter(this);
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
