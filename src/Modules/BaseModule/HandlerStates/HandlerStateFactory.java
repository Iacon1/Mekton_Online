// By Iacon1
// Created 06/16/2021
//

package Modules.BaseModule.HandlerStates;

import Net.ThreadState;
import Modules.BaseModule.ClientHandlerThread;
import Net.StateFactory;

public class HandlerStateFactory implements StateFactory
{

	@Override
	public ThreadState<ClientHandlerThread> getState(int id)
	{
		switch (id)
		{
		case 0: return new CheckClient(this);
		case 2: return new Login(this);
		case 3: return new MainScreen(this);
		default: return new CheckClient(this);
		}
	}

	@Override
	public ThreadState<ClientHandlerThread> getState(String canonicalName)
	{
		if (canonicalName.equals(CheckClient.class.getCanonicalName())) return new CheckClient(this);
		else if (canonicalName.equals(Login.class.getCanonicalName())) return new Login(this);
		else if (canonicalName.equals(MainScreen.class.getCanonicalName())) return new MainScreen(this);
		else return new CheckClient(this);
	}

}
