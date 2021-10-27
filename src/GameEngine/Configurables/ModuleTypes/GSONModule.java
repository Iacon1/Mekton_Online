// By Iacon1
// Created 09/10/2021
// Module type that interfaces with GSONConfig

package GameEngine.Configurables.ModuleTypes;

import com.google.gson.GsonBuilder;

public interface GSONModule
{
	public void addToBuilder(GsonBuilder builder); // In case you have any types that need special builders; Please use delegating feature
}
