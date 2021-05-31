// By Iacon1
// Created 4/21/2021
// Updates the game state and receives input to send to the server

package Client;

import java.awt.Container;
import java.net.Socket;

import Client.Frames.ClientGameFrame;
import Client.Frames.ClientGameWindow;
import Client.Frames.ConnectFailDialog;
import Client.Frames.LoginDialog;
import GameEngine.GameWorld;
import GameEngine.PacketTypes.ClientInfoPacket;
import GameEngine.PacketTypes.GameDataPacket;
import GameEngine.PacketTypes.LoginFeedbackPacket;
import GameEngine.PacketTypes.LoginPacket;
import GameEngine.PacketTypes.ServerInfoPacket;
import Net.ConnectionPairThread;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public class GameClientThread extends ConnectionPairThread
{
	private enum CurrentState
	{
		checkServer // Check the server; Make sure it's a compatible MtO server
		{
			@Override
			public void onEnter(GameClientThread parentThread) {}
			
			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				ServerInfoPacket packet = new ServerInfoPacket();
				packet = (ServerInfoPacket) packet.fromJSON(input);
				parentThread.serverInfo_ = packet;
				if (parentThread.socket_ == null || packet == null || !packet.version.equals(MiscUtils.getVersion()))
					parentThread.stateChange(badServer);
				else
					parentThread.stateChange(login);
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
			public void onEnter(GameClientThread parentThread) {}
			
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
			public void onEnter(GameClientThread parentThread)
			{
				LoginDialog.main(parentThread);
			}

			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				LoginFeedbackPacket packet = new LoginFeedbackPacket();
				packet = (LoginFeedbackPacket) packet.fromJSON(input);
				
				if (packet.successful)
				{
					LoginDialog dialog = (LoginDialog) parentThread.container_;
					dialog.setVisible(false);
					dialog.dispose();
					parentThread.stateChange(mapScreen);
				}
				else
				{
					LoginDialog dialog = (LoginDialog) parentThread.container_;
					dialog.onFail("Login failed.");
				}
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				LoginDialog dialog = (LoginDialog) parentThread.container_;
				LoginPacket packet = dialog.getPacket();
				if (packet != null)
					return packet.toJSON();
				else return null;
			}
		},
		
		mapScreen // Game play on map
		{
			@Override
			public void onEnter(GameClientThread parentThread)
			{
				ClientGameWindow.main(parentThread);
			}
			
			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				GameDataPacket packet = new GameDataPacket();
				packet = (GameDataPacket) packet.fromJSON(input);
				parentThread.gameWorld_ = packet.ourView;
				ClientGameFrame frame = (ClientGameFrame) parentThread.container_;
				frame.updateUIStuff(packet);
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				ClientGameFrame frame = (ClientGameFrame) parentThread.container_;
				String input = frame.getCommand();
				if (input != null) // We got input
					return input;
				return null;
			}		
		};
	
		public abstract void onEnter(GameClientThread parentThread); // When entering this state
		public abstract void processInput(String input, GameClientThread parentThread);
		public abstract String processOutput(GameClientThread parentThread);
	}
	
	private volatile CurrentState currentState_; // Change this to change the state of the client
	private ServerInfoPacket serverInfo_; // The server info we received
	private Container container_; // The currently-open GUI
	
	public GameWorld gameWorld_;
	
	public GameClientThread()
	{
		super();
		currentState_ = CurrentState.checkServer;
	}
	
	public void stateChange(CurrentState newState) // Resets both input & output threads to respect change
	{
		currentState_ = newState;
		runningI_ = false;
		runningO_ = false;
		try {Thread.sleep(10);}
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
		if (this.container_ != null)
		{
			this.container_.setVisible(false);
			//this.container_.dispose();
		}
	}
	
	public void setContainer(Container container)
	{
		this.container_ = container;
	}

}
