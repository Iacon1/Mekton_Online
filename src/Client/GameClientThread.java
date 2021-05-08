// By Iacon1
// Created 4/21/2021
// Updates the game state and receives input to send to the server

package Client;

import java.net.Socket;

import Client.Frames.ClientGameFrame;
import Client.Frames.ClientGameWindow;
import Client.Frames.ConnectFailDialog;
import GameEngine.GameWorld;
import GameEngine.PacketTypes.ClientInfoPacket;
import GameEngine.PacketTypes.GameDataPacket;
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
				while (parentThread.window_ == null); // Wait for screen allocation
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				parentThread.stateChange(mapScreen);
				ClientGameWindow.main(parentThread);
				return null; // TODO implement login
			}
		},
		
		mapScreen // Game play on map
		{
			@Override
			public void processInput(String input, GameClientThread parentThread)
			{
				if (parentThread.window_ == null) return; // Give it a second
				GameDataPacket packet = new GameDataPacket();
				packet = (GameDataPacket) packet.fromJSON(input);
				GameWorld.setWorld(packet.ourView);
				ClientGameFrame frame = (ClientGameFrame) parentThread.window_.getFrame();
				frame.updateUIStuff(packet);
			}
			
			@Override
			public String processOutput(GameClientThread parentThread)
			{
				if (parentThread.window_ == null) return null; // Give it a second
				ClientGameFrame frame = (ClientGameFrame) parentThread.window_.getFrame();
				String input = frame.getCommand();
				if (input != null) // We got input
				{
					return input;
				}
				return null;
			}		
		};
		
		
		public abstract void processInput(String input, GameClientThread parentThread);
		public abstract String processOutput(GameClientThread parentThread);
	}
	
	private volatile CurrentState currentState_; // Change this to change the state of the client
	private ServerInfoPacket serverInfo_; // The server info we received
	private ClientGameWindow window_; // The game window we update
	
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
		if (this.window_ != null)
		{
			this.window_.getFrame().setVisible(false);
			this.window_.getFrame().dispose();
		}
	}
	
	public void setWindow(ClientGameWindow window)
	{
		this.window_ = window;
	}

}
