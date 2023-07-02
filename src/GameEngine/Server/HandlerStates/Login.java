package GameEngine.Server.HandlerStates;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.PlayerHandlerModule;
import GameEngine.Net.StateFactory;
import GameEngine.Net.ThreadState;
import GameEngine.PacketTypes.LoginFeedbackPacket;
import GameEngine.PacketTypes.LoginPacket;
import GameEngine.Server.Account;
import GameEngine.Server.ClientHandlerThread;
import GameEngine.Configurables.ModuleTypes.ServerMakingModule;

import Utils.DataManager;
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

	@Override
	public void processInput(String input, ClientHandlerThread parentThread)
	{
		if (send) return; // Don't take more packets while still giving feedback on one
		
		LoginPacket packet = DataManager.deserialize(input, LoginPacket.class);
		
		Account account = ModuleManager.getHighestOfType(ServerMakingModule.class).newAccount();
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
	
	@Override
	public String processOutput(ClientHandlerThread parentThread)
	{
		if (send)
		{
			send = false;
			if (loginFeedback.successful) parentThread.queueStateChange(getFactory().getState(MiscUtils.ClassToString(MainScreen.class)));
			return DataManager.serialize(loginFeedback);
		}
		else return null;
	}
	
	@Override
	public StateFactory getFactory()
	{
		return factory;
	}
}
