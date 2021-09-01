// By Iacon1
// Created 08/31/2021
// A test instantiation of Client Info

package Modules.TestModule;

import GameEngine.ClientInfo;

public class TestInfo extends ClientInfo
{
	private TestInfo()
	{
		super();
	}

	public static void setup()
	{
		info_ = new TestInfo();
	}

}
