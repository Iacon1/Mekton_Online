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
	private enum Result
	{
		waiting,
		login,
		bad,
		done
	}
	private StateFactory factory;
	private volatile Result result; // 0 - Waiting for result; 1 - Login; 2 - Bad Server; 3 - Done
	private DiffieHellman diffieHellman;
	
	public CheckServer(StateFactory factory)
	{
		this.factory = factory;
		diffieHellman = new DiffieHellman();
		diffieHellman.start();
		
		result = Result.waiting;
	}
	
	@Override
	public void onEnter(GameClientThread parentThread) {parentThread.setEncrypt(false);}
	
	@Override
	public void processInputTrio(String input, GameClientThread parentThread) // TODO implement encryption
	{
		ServerInfoPacket packet = JSONManager.deserializeJSON(input, ServerInfoPacket.class);
		
		if (packet == null)
		{
			result = Result.login;
			while (result != Result.done);
			parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
		}
		else
		{
			parentThread.setInfo(packet.getInfo());
			
			if (parentThread.getSocket() == null || !packet.version.equals(MiscUtils.getVersion()))
			{
				result = Result.bad;
				while (result != Result.done);
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
			}
			else
			{
				result = Result.login;
				while (result != Result.done);
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(Login.class)));
			}
		}		
	}	
	@Override
	public String processOutputTrio(GameClientThread parentThread) // TODO implement encryption
	{
		while (result == Result.waiting);
		
		if (result == Result.login) // We decided to go forward!
		{
			result = Result.done;
			ClientInfoPacket packet = new ClientInfoPacket();
			packet.version = MiscUtils.getVersion();
			packet.mix = diffieHellman.getPublicComponent();
			return JSONManager.serializeJSON(packet);
		}
		else if (result == Result.bad)
		{
			result = Result.done;
			return null; // We have nothing to say to them
		}
		else if (result == Result.done) return null;
		else return null; // ???
	}
	
	@Override
	public void processInputMono(String input, GameClientThread parentThread)
	{
		ServerInfoPacket packet = JSONManager.deserializeJSON(input, ServerInfoPacket.class);
		
		if (packet == null)
		{
			result = Result.bad;
			parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
		}
		else
		{
			parentThread.setInfo(packet.getInfo());
			
			if (parentThread.getSocket() == null || !packet.version.equals(MiscUtils.getVersion()))
			{
				result = Result.bad;
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
			}
			else
			{
				result = Result.login;
				diffieHellman.start();
				diffieHellman.end(packet.mix, parentThread);
				
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(Login.class)));
			}
		}
	}
	@Override
	public String processOutputMono(GameClientThread parentThread)
	{
		if (result == Result.waiting) return null;
		
		if (result == Result.login) // We decided to go forward!
		{
			result = Result.done;
			ClientInfoPacket packet = new ClientInfoPacket();
			packet.version = MiscUtils.getVersion();
			packet.mix = diffieHellman.getPublicComponent();
			
			return JSONManager.serializeJSON(packet);
		}
		else if (result == Result.bad)
		{
			result = Result.done;
			return null; // We have nothing to say to them
		}
		else if (result == Result.done) return null;
		else return null; // ???
	}
	
	@Override
	public StateFactory getFactory()
	{
		return factory;
	}
}
