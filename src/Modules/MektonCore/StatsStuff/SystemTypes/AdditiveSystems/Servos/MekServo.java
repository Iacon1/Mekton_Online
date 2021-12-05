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

package Modules.MektonCore.StatsStuff.SystemTypes.AdditiveSystems.Servos;

import GameEngine.Editor.EditorPanel;
import Modules.MektonCore.Enums.ArmorType;
import Modules.MektonCore.Enums.LevelRAM;
import Modules.MektonCore.Enums.Scale;
import Modules.MektonCore.Enums.ServoClass;
import Modules.MektonCore.ExceptionTypes.ExcessValueException;
import Modules.MektonCore.ExceptionTypes.InsufficientHealthException;
import Modules.MektonCore.StatsStuff.HitLocation.ServoType;
import Modules.MektonCore.StatsStuff.ScaledUnits.ScaledCostValue;
import Modules.MektonCore.StatsStuff.ScaledUnits.ScaledHitValue;

public class MekServo extends Servo
{
	private ServoClass servoClass;
	private ServoClass armorClass;
	private ServoType servoType;
	private ArmorType armorType;
	private LevelRAM levelRAM;
	
	private ScaledHitValue sacrificedHealth;
	private ScaledHitValue armor;

	/** Copy constructor */
	public MekServo(MekServo mekServo)
	{
		super(mekServo);
		this.servoClass = mekServo.servoClass;
		this.armorClass = mekServo.armorClass;
		this.servoType = mekServo.servoType;
		this.armorType = mekServo.armorType;
		this.levelRAM = mekServo.levelRAM;
		
		this.sacrificedHealth = new ScaledHitValue(scale, 0);
		this.armor = new ScaledHitValue(scale, 0);
	}
	public MekServo()
	{
		super();
		this.scale = Scale.mekton;
		this.servoClass = null;
		this.armorClass = null;
		this.servoType = null;
		this.armorType = null;
		this.levelRAM = null;
		
		this.sacrificedHealth = new ScaledHitValue(scale, 0);
		this.armor = new ScaledHitValue(scale, 0);
	}
	public MekServo(Scale scale, ServoClass servoClass, ServoClass armorClass, ServoType servoType, ArmorType armorType, LevelRAM levelRAM)
	{
		this.scale = scale;
		this.servoClass = servoClass;
		this.armorClass = armorClass;
		this.servoType = servoType;
		this.armorType = armorType;
		this.levelRAM = levelRAM;
		
		this.sacrificedHealth = new ScaledHitValue(scale, 0);
		this.armor = new ScaledHitValue(scale, 0);
	}
	
	public Scale getScale()
	{
		return scale;
	}
	
	// Space variables
	@Override
	public ScaledHitValue getVolume()
	{
		return new ScaledHitValue(scale, 0); // Nothing actually stores a mek servo in it so there's no rule for taking space
	}
	private ScaledHitValue getMaxSpacesBase() // The servo's max spaces, accounting for servo class and type but *not* sacrificed health
	{
		switch (servoType)
		{
		case torso: case pod: return new ScaledHitValue(scale, servoClass.level() * 2);
		case arm: case leg: return new ScaledHitValue(scale, servoClass.level() + 1);
		case head: case wing: case tail: return new ScaledHitValue(scale, servoClass.level());
		default: return new ScaledHitValue(scale, 0);
		}
	}
	/** Returns the maximum spaces of the servo. 
	 * 
	 *  @return The maximum spaces of the servo.
	 */
	public ScaledHitValue getMaxSpaces() // The servo's max spaces, accounting for servo class, type, and sacrificed health
	{
		return getMaxSpacesBase().add(sacrificedHealth.multiply(2)); 
	}

	// Armor variables
	/** Gets max armor.
	 *  @return The max armor.
	 */
	public ScaledHitValue getMaxArmor() {return new ScaledHitValue(scale, armorClass.level()).multiply(levelRAM.penalty());}
	/** Sets current armor.
	 * 
	 *  @param scale Scale of the incoming value.
	 *  @param value Value to set to.
	 *  
	 *  @throws ExcessArmorException if you try to give it more armor than its maximum.
	 */
	public void setArmor(ScaledHitValue value) throws ExcessValueException
	{
		if (value.greaterThan(getMaxArmor())) throw new ExcessValueException(value, getMaxArmor(), "armor");
		armor = new ScaledHitValue(value);
	}
	/** Gets current armor.
	 *  @return The current armor.
	 */
	public ScaledHitValue getArmor() {return new ScaledHitValue(armor);}
	/** Gets DC.
	 *  @return The DC.
	 */
	public ScaledHitValue getDC() {return new ScaledHitValue(scale, armorType.DC());}
	/** Gets the RAM reduction factor.
	 *  @return The RAM factor.
	 */
	public double getRAMReduction() {return levelRAM.reduction();}
	
	// Health variables
	private ScaledHitValue getMaxHealthBase() // Without kill sacrificing
	{
		switch (servoType)
		{
		case pod: return new ScaledHitValue(scale, 0);
		default: return getMaxSpacesBase();
		}
	}
	@Override
	public ScaledHitValue getMaxHealth()
	{
		return getMaxHealthBase().subtract(sacrificedHealth);
	}
	/** Sets the sacrificed / reinforced (if negative) health.
	 *  @param value The value to set it to.
	 */
	public void setSacrificedHealth(ScaledHitValue value) throws InsufficientHealthException
	{
		if (!getMaxHealthBase().greaterThan(value)) throw new InsufficientHealthException(value, getMaxHealthBase());
		sacrificedHealth = new ScaledHitValue(value);
	}
	
	/** Gets the cost accounting for armor and the scale of the servo. */
	public ScaledCostValue getCost()
	{
		ScaledCostValue baseCost = new ScaledCostValue(scale, 0);
		switch (servoType)
		{
		case pod: baseCost = new ScaledCostValue(scale, servoClass.level()); break; // Pods are the only type that costs less than their max space
		default:
			if (sacrificedHealth.getValue() < 0) // Reinforced kills cost, sacrificed don't
				baseCost = new ScaledCostValue(scale, getMaxSpaces().getValue(scale)); // Cost scales differently than spaces, so we'll start with spaces at native scale
			else baseCost = new ScaledCostValue(scale, getMaxSpacesBase().getValue(scale));
			break;
		}
		return baseCost.add(new ScaledCostValue(scale, armorType.costMult() * levelRAM.costMult() * armorClass.level()));
	}
	/** Gets the weight in tons, accounting for armor and the scale of the servo. 
	 *  @return The weight of the servo in tons.
	 */
	@Override
	public double getWeight()
	{
		if (sacrificedHealth.getValue() < 0) // Reinforced kills take weight, sacrificed don't
			return getMaxHealth().getValue(Scale.mekton) / 2 + getMaxArmor().getValue(Scale.mekton) / 2;
		else return getMaxHealthBase().getValue(Scale.mekton) / 2 + getMaxArmor().getValue(Scale.mekton) / 2;
	}
	@Override
	public EditorPanel editorPanel()
	{
		EditorPanel panel = super.editorPanel();
		panel.setTitle("Servo");
		panel.addInfo(() -> {return "Servo class: " + servoClass.name();});
		panel.addInfo(() -> {return "Armor class: " + armorClass.name();});
		
		panel.addInfo(() -> {return "Max health: " + getMaxHealth().getValue(Scale.mekton) + " Mek kills";});
		panel.addInfo(() -> {return "Max spaces: " + getMaxSpaces().getValue(Scale.mekton) + " Mek spaces";});
		panel.addInfo(() -> {return "Max armor: " + getMaxArmor().getValue(Scale.mekton) + " Mek SP";});
		
		panel.addOptionList("Scale: ", Scale.values(), Scale.mekton, s -> {scale = s;});
		panel.addOptionList("Servo class: ", ServoClass.values(), ServoClass.mediumWeight, c -> {servoClass = c;});
		panel.addOptionList("Armor class: ", ServoClass.values(), ServoClass.mediumWeight, c -> {armorClass = c;});
		panel.addOptionList("Servo type: ", ServoType.values(), ServoType.torso, t -> {servoType = t;});
		panel.addOptionList("Armor type: ", ArmorType.values(), ArmorType.standard, t -> {armorType = t;});
		panel.addOptionList("Level of RAM: ", LevelRAM.values(), LevelRAM.none, l -> {levelRAM = l;});
		
		return panel;
	}
}
