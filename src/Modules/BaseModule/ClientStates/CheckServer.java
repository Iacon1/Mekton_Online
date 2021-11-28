package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import Modules.BaseModule.DiffieHellman;
import Modules.BaseModule.PacketTypes.ClientInfoPacket;
import Modules.BaseModule.PacketTypes.ServerInfoPacket;
import Utils.JSONManager;
import Utils.MiscUtils;

public class CheckServer implements ThreadState<GameClientThread>
{
	private StateFactory factory;
	private volatile int result; // 0 - Waiting for result; 1 - Login; 2 - Bad Server; 3 - Done
	private DiffieHellman diffieHellman;
	
	public CheckServer(StateFactory factory)
	{
		this.factory = factory;
		diffieHellman = new DiffieHellman();
		diffieHellman.generateSecret();
		
		result = 0;
	}
	
	@Override
	public void onEnter(GameClientThread parentThread) {parentThread.setEncrypt(false);}
	
	@Override
	public void processInputTrio(String input, GameClientThread parentThread) // TODO implement encryption
	{
		ServerInfoPacket packet = JSONManager.deserializeJSON(input, ServerInfoPacket.class);
		
		if (packet == null)
		{
			result = 2;
			while (result != 3);
			parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
		}
		else
		{
			parentThread.setInfo(packet.getInfo());
			
			if (parentThread.getSocket() == null || !packet.version.equals(MiscUtils.getVersion()))
			{
				result = 2;
				while (result != 3);
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
			}
			else
			{
				result = 1;
				while (result != 3);
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(Login.class)));
			}
		}		
	}	
	@Override
	public String processOutputTrio(GameClientThread parentThread) // TODO implement encryption
	{
		while (result == 0);
		
		if (result == 1) // We decided to go forward!
		{
			result = 3;
			ClientInfoPacket packet = new ClientInfoPacket();
			packet.version = MiscUtils.getVersion();
			packet.mix = diffieHellman.initialMix();
			return JSONManager.serializeJSON(packet);
		}
		else if (result == 2)
		{
			result = 3;
			return null; // We have nothing to say to them
		}
		else if (result == 3) return null;
		else return null; // ???
	}
	
	@Override
	public void processInputMono(String input, GameClientThread parentThread)
	{
		ServerInfoPacket packet = JSONManager.deserializeJSON(input, ServerInfoPacket.class);
		
		if (packet == null)
		{
			result = 2;
			parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
		}
		else
		{
			parentThread.setInfo(packet.getInfo());
			
			if (parentThread.getSocket() == null || !packet.version.equals(MiscUtils.getVersion()))
			{
				result = 2;
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
			}
			else
			{
				result = 1;
				diffieHellman.finalMix(packet.mix);
				diffieHellman.giveKey(parentThread);
				
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(Login.class)));
			}
		}
	}
	@Override
	public String processOutputMono(GameClientThread parentThread)
	{
		if (result == 0) return null;
		
		if (result == 1) // We decided to go forward!
		{
			result = 3;
			ClientInfoPacket packet = new ClientInfoPacket();
			packet.version = MiscUtils.getVersion();
			packet.mix = diffieHellman.initialMix();
			
			return JSONManager.serializeJSON(packet);
		}
		else if (result == 2)
		{
			result = 3;
			return null; // We have nothing to say to them
		}
		else if (result == 3) return null;
		else return null; // ???
	}
	
	@Override
	public StateFactory getFactory()
	{
		return factory;
	}
}
