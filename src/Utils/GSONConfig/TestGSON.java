// By Iacon1
// Created 09/09/2021
// Quick testing suite for GSON

package Utils.GSONConfig;

import GameEngine.GameWorld;
import GameEngine.PacketTypes.GameDataPacket;
import Modules.TestModule.DummyPlayer;
import Modules.TestModule.TestHexData;
import Modules.TestModule.TestHexmap;
import Utils.JSONManager;
import Utils.Logging;

public class TestGSON
{
	private static boolean testOnce = true; // How many times to test
	private static boolean shouldTest = false; // Should test?
	
	private static boolean isNull(Object obj)
	{
		if (obj == null)
		{
			Logging.logNotice("Obj is null");
			return true;
		}
		else
		{
			Logging.logNotice("Obj not null");
			return false;
		}
	}
	private static <T> void testObj(T obj, Class<T> objClass)
	{
		String s = JSONManager.serializeJSON(obj);
		Logging.logNotice("Serialized: " + s);
		obj = JSONManager.deserializeJSON(s, objClass);
		isNull(obj);
	}
	public static void test()
	{
		if (!shouldTest) return;
		TestHexmap map = new TestHexmap("Deez", new TestHexData());
		map.setDimensions(10, 10, 10);
		testObj(map, TestHexmap.class);
		
		DummyPlayer player = new DummyPlayer();
		testObj(player, DummyPlayer.class);
		
		GameWorld world = GameWorld.getWorld();
		testObj(world, GameWorld.class);
		
		GameDataPacket packet = new GameDataPacket();
		packet.viewWorld();
		testObj(packet, GameDataPacket.class);
		
		if (testOnce) shouldTest = false;
	}
}
