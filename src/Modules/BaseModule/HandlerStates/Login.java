package Modules.BaseModule.HandlerStates;

import GameEngine.Configurables.ModuleManager;
import GameEngine.PacketTypes.LoginFeedbackPacket;
import GameEngine.PacketTypes.LoginPacket;
import Net.StateFactory;
import Net.ThreadState;
import Server.Account;
import Server.ClientHandlerThread;
import Utils.Logging;

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
			if (parentThread.getParent().getAccount(packet.username) != null) return; // Don't overwrite an old account!
			loginFeedback_.successful = parentThread.getParent().addAccount(account);
			if (loginFeedback_.successful)
			{
				parentThread.setUsername(account.username);
				Logging.logNotice("Client " + parentThread.getSocket().getInetAddress() + " has made account \"" + parentThread.getUsername() + "\".");
				
				ModuleManager.makePlayer(parentThread.getParent(), parentThread.getAccount());
			}
		}
		else
		{
			loginFeedback_.successful = parentThread.getParent().login(packet.username, packet.password);
			if (loginFeedback_.successful)
			{
				parentThread.setUsername(account.username);
				Logging.logNotice("Client " + parentThread.getSocket().getInetAddress() + " has logged in as account \"" + parentThread.getUsername() + "\".");
				
				ModuleManager.wakePlayer(parentThread.getParent(), parentThread.getAccount());
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
			if (loginFeedback_.successful) parentThread.queueStateChange(getFactory().getState(MapScreen.class.getCanonicalName()));
			return loginFeedback_.toJSON();
		}
	else return null;
	}

	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}
}
