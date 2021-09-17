package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import GameEngine.PacketTypes.ClientInfoPacket;
import GameEngine.PacketTypes.ServerInfoPacket;
import Utils.JSONManager;
import Utils.MiscUtils;

public class CheckServer implements ThreadState<GameClientThread>
{
	private StateFactory factory_;
	private volatile int result_; // 0 - Waiting for result; 1 - Login; 2 - Bad Server; 3 - Done
	
	public CheckServer(StateFactory factory)
	{
		factory_ = factory;
		
		result_ = 0;
	}
	
	@Override
	public void onEnter(GameClientThread parentThread) {}
	
	@Override
	public void processInputTrio(String input, GameClientThread parentThread)
	{
		ServerInfoPacket packet = JSONManager.deserializeJSON(input, ServerInfoPacket.class);
		
		
		if (packet == null)
		{
			result_ = 2;
			while (result_ != 3);
			parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
		}
		else
		{
			parentThread.setInfo(packet);
			
			if (parentThread.getSocket() == null || !packet.version.equals(MiscUtils.getVersion()))
			{
				result_ = 2;
				while (result_ != 3);
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
			}
			else
			{
				result_ = 1;
				while (result_ != 3);
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(Login.class)));
			}
		}		
	}	
	@Override
	public String processOutputTrio(GameClientThread parentThread)
	{
		while (result_ == 0);
		
		if (result_ == 1) // We decided to go forward!
		{
			result_ = 3;
			ClientInfoPacket packet = new ClientInfoPacket();
			packet.version = MiscUtils.getVersion();
			return JSONManager.serializeJSON(packet);
		}
		else if (result_ == 2)
		{
			result_ = 3;
			return null; // We have nothing to say to them
		}
		else if (result_ == 3) return null;
		else return null; // ???
	}
	
	@Override
	public void processInputMono(String input, GameClientThread parentThread)
	{
		ServerInfoPacket packet = JSONManager.deserializeJSON(input, ServerInfoPacket.class);
		
		if (packet == null)
		{
			result_ = 2;
			parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
		}
		else
		{
			parentThread.setInfo(packet);
			
			if (parentThread.getSocket() == null || !packet.version.equals(MiscUtils.getVersion()))
			{
				result_ = 2;
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(BadServer.class)));
			}
			else
			{
				result_ = 1;
				parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(Login.class)));
			}
		}
	}
	@Override
	public String processOutputMono(GameClientThread parentThread)
	{
		if (result_ == 0) return null;
		
		if (result_ == 1) // We decided to go forward!
		{
			result_ = 3;
			ClientInfoPacket packet = new ClientInfoPacket();
			packet.version = MiscUtils.getVersion();
			return JSONManager.serializeJSON(packet);
		}
		else if (result_ == 2)
		{
			result_ = 3;
			return null; // We have nothing to say to them
		}
		else if (result_ == 3) return null;
		else return null; // ???
	}
	
	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}
}
