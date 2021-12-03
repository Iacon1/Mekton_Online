// By Iacon1
// Created 12/03/2021
//

package Modules.MektonCore.StatsStuff.SheetTypes;

import java.util.List;

import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.StatsStuff.ServoList;
import Modules.MektonCore.StatsStuff.ScaledUnits.ScaledCostValue;
import Modules.MektonCore.StatsStuff.SystemTypes.MultiplierSystem;
import Modules.MektonCore.StatsStuff.SystemTypes.AdditiveSystems.AdditiveSystem;

public class MekSheet extends ServoList
{
	private List<AdditiveSystem> additiveSystems;
	
	private List<MultiplierSystem> multiplierSystems;
	
	public ScaledCostValue getCost()
	{
		ScaledCostValue baseValue = new ScaledCostValue(Scale.mekton, 0);
		
		for (int i = 0; i < servos.size(); ++i)
			baseValue.addInPlace(servos.get(i).getCost());
		
		for (int i = 0; i < additiveSystems.size(); ++i)
			baseValue.addInPlace(additiveSystems.get(i).getCost());
		
		double modifier = 1;
		for (int i = 0; i < multiplierSystems.size(); ++i) modifier += multiplierSystems.get(i).getMultiplier();
		ScaledCostValue multValue = baseValue.multiply(modifier);
		
		// TODO WE and stuff
		
		return multValue;
	}
}
