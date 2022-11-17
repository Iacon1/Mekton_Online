// By Iacon1
// Created 04/27/2021
// Data given to a player on update

package GameEngine.PacketTypes;

import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.GraphicsHandlerModule;
import GameEngine.Graphics.RenderQueue;
import GameEngine.Server.Account;

public class GameDataPacket extends Packet
{
	public RenderQueue renderQueue;

	public GameDataPacket(RenderQueue renderQueue)
	{	
		this.renderQueue = renderQueue;
	}
}
