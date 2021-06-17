// By Iacon1
// Created 06/16/2021
//

package Modules.BaseModule.ClientStates;

import Net.ThreadState;
import Client.GameClientThread;
import Net.StateFactory;

public class ClientStateFactory implements StateFactory
{

	@Override
	public ThreadState<GameClientThread> getState(int id)
	{
		switch (id)
		{
		case 0: return new CheckServer(this);
		case 1: return new BadServer(this);
		case 2: return new Login(this);
		case 3: return new MapScreen(this);
		default: return new CheckServer(this);
		}
	}

	@Override
	public ThreadState<GameClientThread> getState(String canonicalName)
	{
		if (canonicalName.equals(CheckServer.class.getCanonicalName())) return new CheckServer(this);
		else if (canonicalName.equals(BadServer.class.getCanonicalName())) return new BadServer(this);
		else if (canonicalName.equals(Login.class.getCanonicalName())) return new Login(this);
		else if (canonicalName.equals(MapScreen.class.getCanonicalName())) return new MapScreen(this);
		else return new CheckServer(this);
	}

}
