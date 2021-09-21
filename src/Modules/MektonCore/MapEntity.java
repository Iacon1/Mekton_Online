// By Iacon1
// Created 09/21/2021
//

package Modules.MektonCore;

import GameEngine.EntityToken;
import Modules.HexUtilities.HexEntity;
import Modules.HexUtilities.HexStructures.Axial.AxialHexCoord3D;

public abstract class MapEntity extends HexEntity<AxialHexCoord3D>
{
	protected EntityToken<MektonMap> mapToken;
	
	public MapEntity()
	{
		super();
		this.mapToken = null;
	}
	
	public MapEntity(String owner, MektonMap map)
	{
		super(owner);
		this.mapToken = new EntityToken<MektonMap>(map);
	}
	
	
}
