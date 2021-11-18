// By Iacon1
// Created 09/01/2021
// Sound Manager

package GameEngine.Managers;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import GameEngine.GameInfo;
import Utils.Logging;

public final class SoundManager
{
	private static Map<String, Clip> sounds;
	private static Map<String, Clip> music;
	
	public static void init()
	{
		sounds = new HashMap<String, Clip>();
		music = new HashMap<String, Clip>();
	}
	
	public static void playSoundPath(String path) // Gets an image from path
	{
		Clip sound = sounds.get(path);
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
			
				sounds.put(path, sound);
			}
			catch (Exception e) {Logging.logException(e); Logging.logError("Could not load sound @ " + path); return;}
		
			Logging.logError("Loading done");
		}
	
		sound.start();
	}
	
	public static void playSound(String name)
	{
		playSound(GameInfo.getServerPackResource("/Sounds/SFX/" + name + ".WAV"));
	}
}
