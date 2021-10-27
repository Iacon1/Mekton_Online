// By Iacon1
// Created 09/14/2021
//

package GameEngine;

public class Animation
{
	public static enum EndAction
	{
		stickFrame, // Stop at the last frame, forever.
		callHandle, // Call any code that you want to run after an animation *stops*. If no such code is provided then see stickFrame
		loop // Restart
	}
	
	public int frames;
	public int duration; // In milliseconds
	public int offset; // In height multiples
	public EndAction action;
	
	public int GetFrameDuration() // In milliseconds
	{
		return duration / frames;
	}
}
