// By Iacon1
// Created 06/16/2021
// Checking client state

package Modules.BaseModule.HandlerStates;

import GameEngine.PacketTypes.ClientInfoPacket;
import GameEngine.PacketTypes.ServerInfoPacket;
import Modules.BaseModule.ClientHandlerThread;
import Net.StateFactory;
import Net.ThreadState;
import Utils.Logging;
import Utils.MiscUtils;

public class CheckClient implements ThreadState<ClientHandlerThread>
{
	boolean sent_;
	private StateFactory factory_;
	
	public CheckClient(StateFactory factory)
	{
		factory_ = factory;
		
		sent_ = false;
	}
	
	@Override
	public void onEnter(ClientHandlerThread parentThread) {}

	public void processInput(String input, ClientHandlerThread parentThread, boolean mono)
	{
		if (sent_)
		{
			ClientInfoPacket packet = new ClientInfoPacket();
			packet = (ClientInfoPacket) packet.fromJSON(input);
			if (packet == null || !packet.version.equals(MiscUtils.getVersion())) // We don't actually care about this client anymore
			{
				if (packet == null) Logging.logError("Client " + parentThread.getSocket().getInetAddress() + " tried to connect<br> but failed to send a info packet.");
				else Logging.logError("Client " + parentThread.getSocket().getInetAddress() + " tried to connect<br> but sent a bad packet.");
				parentThread.close();
			}
			else
			{
				Logging.logNotice("Client " + parentThread.getSocket().getInetAddress() + " has connected.");
				parentThread.queueStateChange(getFactory().getState(Login.class.getCanonicalName())); // They're good, let's login	
			}
		}
	}

	public String processOutput(ClientHandlerThread parentThread, boolean mono)
	{
		if (!sent_)
		{
			ServerInfoPacket packet = new ServerInfoPacket();
			packet.serverName = parentThread.getParent().getName();
			packet.version = MiscUtils.getVersion();
			packet.resourceFolder = parentThread.getParent().getResourceFolder();
			
			if (parentThread.getParent().currentPlayers() >= parentThread.getParent().maxPlayers()) // We're full
			{
				packet.note = ServerInfoPacket.Note.full;
				parentThread.close(); // We don't need this connection any more
			}
			else packet.note = ServerInfoPacket.Note.good;
			sent_ = true;
			
			Logging.logNotice("Sent info to " + parentThread.getSocket().getInetAddress());
			return packet.toJSON();
		}
		else return null; // We have nothing to say to them
	}

	@Override
	public void processInputTrio(String input, ClientHandlerThread parentThread) {processInput(input, parentThread, false);}
	@Override
	public String processOutputTrio(ClientHandlerThread parentThread) {return processOutput(parentThread, false);}
	
	@Override
	public void processInputMono(String input, ClientHandlerThread parentThread) {processInput(input, parentThread, true);}
	@Override
	public String processOutputMono(ClientHandlerThread parentThread) {return processOutput(parentThread, true);}
	
	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}
}
