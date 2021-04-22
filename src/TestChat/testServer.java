// By Iacon1
// Created 4/20/2021
// Server test

package TestChat;

import Net.Server.*;

public class testServer
{
	public static void main(String[] args)
	{
		ServerThread<ChatHostPair> server = new ServerThread<ChatHostPair>(6666, new ChatHostPair());
		server.start();
	}
}
