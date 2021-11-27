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
	boolean send;
	private StateFactory factory;
	LoginFeedbackPacket loginFeedback;
	public Login(StateFactory factory)
	{
		this.factory = factory;
		
		loginFeedback = new LoginFeedbackPacket();
		send = false;
	}
	
	@Override
	public void onEnter(ClientHandlerThread parentThread) {parentThread.setEncrypt(true);}

	public void processInput(String input, ClientHandlerThread parentThread, boolean mono)
	{
		if (send) return; // Don't take more packets while still giving feedback on one
		
		LoginPacket packet = JSONManager.deserializeJSON(input, LoginPacket.class);
		
		Account account = new TestAccount(); // TODO Modularize
		account.username = packet.username; 
		account.setHash(packet.myPassword);
		
		if (packet.newUser)
		{
			if (parentThread.getParent().getAccount(packet.username) != null) return; // Don't overwrite an old account!
			loginFeedback.successful = (parentThread.getParent().addAccount(account) != -1); // TODO exception instead
			if (loginFeedback.successful)
			{
				parentThread.setUsername(account.username);
				Logging.logNotice("Client " + parentThread.getSocket().getInetAddress() + " has made account \"" + parentThread.getUsername() + "\".");
				
				ModuleManager.getHighestOfType(PlayerHandlerModule.class).makePlayer(parentThread.getAccount());
			}
		}
		else
		{
			loginFeedback.successful = parentThread.getParent().login(packet.username, packet.myPassword);
			if (loginFeedback.successful)
			{
				parentThread.setUsername(account.username);
				Logging.logNotice("Client " + parentThread.getSocket().getInetAddress() + " has logged in as account \"" + parentThread.getUsername() + "\".");
				
				ModuleManager.getHighestOfType(PlayerHandlerModule.class).makePlayer(parentThread.getAccount());
			}
		}
		
		send = true;
	}
	
	public String processOutput(ClientHandlerThread parentThread, boolean mono)
	{
		if (send)
		{
			send = false;
			if (loginFeedback.successful) parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(MainScreen.class)));
			return JSONManager.serializeJSON(loginFeedback);
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
		return factory;
	}
}
