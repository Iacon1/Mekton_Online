// By Iacon1
// Created 09/10/2021
// Provides state factories

package GameEngine.Configurables.ModuleTypes;

import GameEngine.Net.StateFactory;

public interface StateGiverModule extends Module
{
	public StateFactory clientFactory(); // Client factory
	public StateFactory handlerFactory(); // Handler factory
}
