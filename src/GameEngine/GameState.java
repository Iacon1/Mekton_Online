// By Iacon1
// Created 04/22/2021
// Stores all data needed for the game

// The server-side variant will likely have more data than the client-side one

package GameEngine;

import java.util.ArrayList;

public abstract class GameState
{
	protected static ArrayList<GameInstance> instances_;
	
	public void addInstance()
	{}
}
