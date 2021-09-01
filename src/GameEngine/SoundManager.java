// By Iacon1
// Created 09/01/2021
// Sound Manager

package GameEngine;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.*;

import Utils.Logging;
import Utils.MiscUtils;

public final class SoundManager
{
	private static HashMap<String, Clip> sounds_;
	private static HashMap<String, Clip> music_;
	
	public static void init()
	{
		sounds_ = new HashMap<String, Clip>();
		music_ = new HashMap<String, Clip>();
	}
	
	public static void playSound(String path) // Gets an image from path
	{
		Clip sound = sounds_.get(path);
		AudioInputStream stream;
		
		if (sound == null)
		{
			Logging.logError("Have not loaded sound @ " + path + ". Loading...");
			
			try
			{
				stream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());

				if (stream == null)
				{
					Logging.logError("Could not load sound @ " + path);
					return;
				}
					
				sound = AudioSystem.getClip();
				sound.open(stream);
			
				sounds_.put(path, sound);
			}
			catch (Exception e) {Logging.logException(e); Logging.logError("Could not load sound @ " + path); return;}
		
			Logging.logError("Loading done");
		}
	
		sound.start();
	}
}
