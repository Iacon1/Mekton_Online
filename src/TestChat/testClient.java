// By Iacon1
// Created 4/20/2021
// Test Client

package TestChat;

import Net.Client.Client;

public class testClient
{
	public static void main(String[] args)
	{
		Client<ChatClientPair> client = new Client<ChatClientPair>("localhost", 6666, new ChatClientPair());		
		client.run();
	}
}
