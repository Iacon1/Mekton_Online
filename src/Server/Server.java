// By Iacon1
// Created 04/26/2021
//

package Server;

import GameEngine.GameWorld;
import GameEngine.Hexmap;
import Net.Server.ServerThread;
import Server.Frames.ServerWindow;
import Utils.Logging;
import Utils.MiscUtils;

// TODO these are all dummy values
public class Server
{
	private ServerThread<ClientHandlerThread> serverThread_;
	private static ClientHandlerThread template_;
	public Server()
	{
		GameWorld.init();
		Hexmap map = new Hexmap();
		map.setDimensions(18, 9, 1);
		template_ = new ClientHandlerThread();
		template_.setParent(this);
	}
	
	public void start(int port)
	{
		serverThread_ = new ServerThread<ClientHandlerThread>(port, template_);
		serverThread_.open();
		serverThread_.start();
		if (serverThread_.isAlive()) Logging.logNotice("Server started on IP " + MiscUtils.getExIp() + "<br> & port " + port);
	}
	public String getName() // Get server name
	{
		return null;
	}
	
	public String getResourceFolder() // Get path for resources
	{
		return "Resources/Server Packs/Default";
	}
	
	public int currentPlayers()
	{
		return 0;
	}
	public int maxPlayers()
	{
		return 10;
	}
}
