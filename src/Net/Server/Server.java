// By Iacon1
// Created 04/26/2021
//

package Net.Server;

import Net.ConnectionPairThread;
import Utils.Logging;
import Utils.MiscUtils;

// TODO these are all dummy values
public abstract class Server<T extends ConnectionPairThread>
{
	private ServerThread<T> serverThread_;
	
	public Server(T template)
	{	
		serverThread_ = new ServerThread<T>(-1, template);
	}
	
	public void start(int port)
	{
		serverThread_.setPort(port);
		serverThread_.open();
		serverThread_.start();
		if (serverThread_.isAlive()) Logging.logNotice("Server started on IP " + MiscUtils.getExIp() + "<br> & port " + port);
	}
	
	public abstract String getName();
	public abstract String getResourceFolder();
	public abstract int currentPlayers();
	public abstract int maxPlayers();

}
