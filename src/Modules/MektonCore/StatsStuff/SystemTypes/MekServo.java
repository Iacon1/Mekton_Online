// By Iacon1
// Created 09/16/2021
// Servo for meks, roadstrikers, ships, etc.
// Remember that pods always have zero health
// TODO:
/* Kill sacrificing
 * Throw range
 * Melee+
 * 
 * Redo space occupation
 */

package Modules.MektonCore.StatsStuff.SystemTypes;

import java.util.ArrayList;
import java.util.List;

import Modules.MektonCore.Enums.ArmorType;
import Modules.MektonCore.Enums.LevelRAM;
import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.Enums.ServoClass;
import Modules.MektonCore.ExceptionTypes.DoesntFitException;
import Modules.MektonCore.ExceptionTypes.ExcessArmorException;
import Modules.MektonCore.StatsStuff.HitLocation.ServoType;

public class MekServo extends Servo
{
	private List<Integer> spaceTakerIDs; // Systems that occupy space in this servo.
	
	private ServoClass servoClass;
	private ServoClass armorClass;
	private ServoType servoType;
	private ArmorType armorType;
	private LevelRAM levelRAM;
	
	private double armor = 0;
	
	/** Copy constructor */
	public MekServo(MekServo mekServo)
	{
		super(mekServo);
		this.servoClass = mekServo.servoClass;
		this.armorClass = mekServo.armorClass;
		this.servoType = mekServo.servoType;
		this.armorType = mekServo.armorType;
		this.levelRAM = mekServo.levelRAM;
		
		this.spaceTakerIDs = new ArrayList<Integer>();
		
		for (int i = 0; i < mekServo.spaceTakerIDs.size(); ++i)
		{
			this.spaceTakerIDs.add((int) mekServo.spaceTakerIDs.get(i));
		}
	}
	
	public MekServo()
	{
		super();
		this.servoClass = null;
		this.armorClass = null;
		this.servoType = null;
		this.armorType = null;
		this.levelRAM = null;
		
		this.spaceTakerIDs = new ArrayList<Integer>();
	}
	public MekServo(Scale scale, ServoClass servoClass, ServoClass armorClass, ServoType servoType, ArmorType armorType, LevelRAM levelRAM)
	{
		super(scale);
		this.servoClass = servoClass;
		this.armorClass = armorClass;
		this.servoType = servoType;
		this.armorType = armorType;
		this.levelRAM = levelRAM;
		
		this.spaceTakerIDs = new ArrayList<Integer>();
	}
	
	public int getMaxSpace() // The servo's max spaces, accounting for servo class and type
	{
		switch (servoType)
		{
		case torso: case pod: return servoClass.level() * 2;
		case arm: case leg: return servoClass.level() + 1;
		case head: case wing: case tail: return servoClass.level();
		default: return 0;
		}
	}

	@Override
	public double getMaxHealth(Scale scale)
	{
		switch (servoType)
		{
		case pod: return 0;
		default: return scale.getDamageScale() * getMaxSpace();
		}
	}
	
	/** Gets max armor.
	 *  @param scale Scale to return in.
	 */
	public double getMaxArmor(Scale scale) {return scale.getDamageScale() * armorClass.level()  * levelRAM.penalty();}
	/** Gets max armor in the servo's native scale. */
	public double getMaxArmor() {return getMaxArmor(scale);}
	/** Gets the cost in CP, accounting for armor and the scale of the servo. */
	public double getCost()
	{
		double baseCost = 0;
		switch (servoType)
		{
		case pod: baseCost = servoClass.level(); break; // Pods are the only type that costs less than their max space
		default: baseCost = getMaxSpace(); break;
		}
		return scale.getCostScale() * (baseCost + (armorType.costMult() + levelRAM.costMult()) * armorClass.level());
	}
	/** Gets the weight in tons, accounting for armor and the scale of the servo. 
	 */
	public double getWeight()
	{
		return getMaxHealth() / 2 + getMaxArmor() / 2;
	}
	
	public int getEmptySpace()
	{
		int emptySpace = getMaxSpace();
		
		for (int i = 0; i < spaceTakerIDs.size(); ++i)
		{
			emptySpace -= ((SpaceTaker) listHolder.getSystem(spaceTakerIDs.get(i))).getVolume();
		}
		
		return emptySpace;
	}
	/** Occupies a given space if possible.
	 * 
	 *  @param spaceTaker the taker to try to place.
	 * 
	 *  @throws DoesntFitException if the spaceTaker doesn't fit.
	 */
	public void addSystem(SpaceTaker spaceTaker) throws DoesntFitException
	{
		if (getEmptySpace() >= spaceTaker.getVolume()) spaceTakerIDs.add(spaceTaker.getID());
		else throw new DoesntFitException(spaceTaker.getVolume(), getEmptySpace(), getMaxSpace());
	}

	/** Sets current armor.
	 * 
	 *  @param scale Scale of the incoming value.
	 *  @param value Value to set to.
	 *  
	 *  @throws ExcessArmorException if you try to give it more armor than its maximum.
	 */
	public void setCurrentArmor(Scale scale, double value) throws ExcessArmorException
	{
		if (value > getMaxHealth(scale)) throw new ExcessArmorException(getMaxArmor(scale), value);
		armor = scale.getDamageScale() * value;
	}
	/** Sets current armor in the servo's native scale.
	 * 
	 *  @param value Value to set to.
	 *  
	 *  @throws ExcessArmorException if you try to give it more armor than its maximum.
	 */
	public void setCurrentArmor(double value) throws ExcessArmorException {setCurrentArmor(scale, value);}
	/** Gets current armor.
	 *  @param scale Scale to return in.
	 */
	public double getCurrentArmor(Scale scale) {return scale.getDamageScale() * armor;}
	/** Gets current armor in the servo's native scale. */
	public double getCurrentArmor() {return getCurrentArmor();}

	/** Gets DC.
	 * 
	 *  @param scale Scale to return in.
	 */
	public double getDC(Scale scale) {return scale.getDamageScale() * armorType.DC();}
	/** Gets DC in the servo's native scale. */
	public double getDC() {return getDC(scale);}
	/** Gets the RAM reduction factor. */
	public double getRAMReduction() {return levelRAM.reduction();}
}
