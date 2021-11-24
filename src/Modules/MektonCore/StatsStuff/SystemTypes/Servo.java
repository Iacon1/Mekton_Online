// By Iacon1
// Created 11/23/2021
// A limb or such thing

package Modules.MektonCore.StatsStuff.SystemTypes;

import Modules.MektonCore.Enums.Scale;

public abstract class Servo extends System
{
	/** Copy constructor. */
	public Servo(Servo servo) {super(servo);}

	public Servo() {super();}

	public Servo(Scale scale) {super(scale);}
}
