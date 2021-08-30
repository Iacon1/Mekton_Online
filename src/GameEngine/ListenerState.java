// By Iacon1
// Created 06/14/2021
// A state that determines the packet type and reacts accordingly
// Should add listeners as lambdas in the implementer's constructor

package GameEngine;

import java.util.HashMap;
import java.util.function.BiFunction;

import GameEngine.PacketTypes.Packet;
import Net.ConnectionPairThread;
import Net.ThreadState;
import Utils.Logging;

public abstract class ListenerState<T extends ConnectionPairThread> implements ThreadState<T>
{
	private HashMap<String, BiFunction<T, String, Void>> listeners_;
	
	public void addListener(String name, BiFunction<T, String, Void> listener)
	{
		listeners_.put(name, listener);
	}
	
	public ListenerState()
	{
		listeners_ = new HashMap<String, BiFunction<T, String, Void>>();
	}

	public void processInput(String input, T parentThread, boolean mono)
	{
		String type = Packet.getType(input);
		
		if (listeners_.get(type) == null) Logging.logError("Unrecognized packet type " + type);
		else listeners_.get(type).apply(parentThread, input);
	}
	
	@Override
	public void processInputTrio(String input, T parentThread) {processInput(input, parentThread, false);}
	
	@Override
	public void processInputMono(String input, T parentThread) {processInput(input, parentThread, true);}
}
