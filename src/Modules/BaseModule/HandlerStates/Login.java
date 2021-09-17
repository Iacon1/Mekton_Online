package Modules.BaseModule.HandlerStates;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.PlayerHandlerModule;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import GameEngine.Server.Account;
import Modules.BaseModule.ClientHandlerThread;
import Modules.BaseModule.PacketTypes.LoginFeedbackPacket;
import Modules.BaseModule.PacketTypes.LoginPacket;
import Modules.TestModule.TestAccount;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public class Login implements ThreadState<ClientHandlerThread>
{
	boolean send_;
	private StateFactory factory_;
	LoginFeedbackPacket loginFeedback_;
	public Login(StateFactory factory)
	{
		factory_ = factory;
		
		loginFeedback_ = new LoginFeedbackPacket();
		send_ = false;
	}
	
	@Override
	public void onEnter(ClientHandlerThread parentThread) {}

	public void processInput(String input, ClientHandlerThread parentThread, boolean mono)
	{
		if (send_) return; // Don't take more packets while still giving feedback on one
		
		LoginPacket packet = JSONManager.deserializeJSON(input, LoginPacket.class);
		
		Account account = new TestAccount(); // TODO Modularize
		account.username = packet.username; 
		account.setHash(packet.myPassword);
		
		if (packet.newUser)
		{
			if (parentThread.getParent().getAccount(packet.username) != null) return; // Don't overwrite an old account!
			loginFeedback_.successful = parentThread.getParent().addAccount(account);
			if (loginFeedback_.successful)
			{
				parentThread.setUsername(account.username);
				Logging.logNotice("Client " + parentThread.getSocket().getInetAddress() + " has made account \"" + parentThread.getUsername() + "\".");
				
				ModuleManager.getHighestOfType(PlayerHandlerModule.class).makePlayer(parentThread.getAccount());
			}
		}
		else
		{
			loginFeedback_.successful = parentThread.getParent().login(packet.username, packet.myPassword);
			if (loginFeedback_.successful)
			{
				parentThread.setUsername(account.username);
				Logging.logNotice("Client " + parentThread.getSocket().getInetAddress() + " has logged in as account \"" + parentThread.getUsername() + "\".");
				
				ModuleManager.getHighestOfType(PlayerHandlerModule.class).makePlayer(parentThread.getAccount());
			}
		}
		
		send_ = true;
	}
	
	public String processOutput(ClientHandlerThread parentThread, boolean mono)
	{
		if (send_)
		{
			send_ = false;
			if (loginFeedback_.successful) parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(MainScreen.class)));
			return JSONManager.serializeJSON(loginFeedback_);
		}
		else return null;
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
