// By Iacon1
// Created 09/02/2021
// Contains key bindings

package GameEngine.Managers;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import Utils.Logging;

public final class KeyBindingManager
{
	private static HashMap<String, Integer> bindings;
	
	public static void init()
	{
		bindings = new HashMap<String, Integer>();
		bindings.put("MOVE_NORTH", KeyEvent.VK_W);
	}
	
	public static int getBinding(String name) // Gets a binding from a name
	{
		int binding = -1;
		binding = bindings.get(name);
		if (binding == -1) Logging.logError("Binding " + name + " not found.");
	
		return binding;
	}
}
