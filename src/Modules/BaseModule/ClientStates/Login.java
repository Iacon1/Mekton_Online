// By Iacon1
// Created 06/16/2021
// Login

package Modules.BaseModule.ClientStates;

import Client.GameClientThread;
import GameEngine.PacketTypes.LoginFeedbackPacket;
import GameEngine.PacketTypes.LoginPacket;
import Modules.BaseModule.ClientFrames.LoginDialog;
import Net.StateFactory;
import Net.ThreadState;

public class Login implements ThreadState<GameClientThread>
{
	private StateFactory factory_;
	private boolean successful_;
	
	public Login(StateFactory factory)
	{
		factory_ = factory;
	}
	
	@Override
	public void onEnter(GameClientThread parentThread)
	{
		LoginDialog.main(parentThread);
	}

	public void processInput(String input, GameClientThread parentThread, boolean mono)
	{
		LoginFeedbackPacket packet = new LoginFeedbackPacket();
		packet = (LoginFeedbackPacket) packet.fromJSON(input);
		successful_ = packet.successful;
		if (successful_)
		{
			LoginDialog dialog = (LoginDialog) parentThread.getContainer("login");
			dialog.setVisible(false);
			dialog.dispose();
			parentThread.queueStateChange(getFactory().getState(MainScreen.class.getCanonicalName()));
		}
		else
		{
			LoginDialog dialog = (LoginDialog) parentThread.getContainer("login");
			dialog.onFail("Login failed.");
		}
	}
	
	public String processOutput(GameClientThread parentThread, boolean mono)
	{
		LoginDialog dialog = (LoginDialog) parentThread.getContainer("login");
		if (dialog.isVisible() == false && !successful_)
			parentThread.close();
		LoginPacket packet = dialog.getPacket();
		if (packet != null)
			return packet.toJSON();
		else return null;
	}

	@Override
	public void processInputTrio(String input, GameClientThread parentThread) {processInput(input, parentThread, false);}
	@Override
	public String processOutputTrio(GameClientThread parentThread) {return processOutput(parentThread, false);}
	
	@Override
	public void processInputMono(String input, GameClientThread parentThread) {processInput(input, parentThread, true);}
	@Override
	public String processOutputMono(GameClientThread parentThread) {return processOutput(parentThread, true);}
	
	@Override
	public StateFactory getFactory()
	{
		return factory_;
	}

}
