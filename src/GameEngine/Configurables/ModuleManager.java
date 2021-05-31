// By Iacon1
// Created 05/31/2021
// Manages modules

package GameEngine.Configurables;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import GameEngine.GameWorld;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public final class ModuleManager
{
	private static HashMap<String, Module> modules_; // The actual modules, by file name
	private static HashMap<String, Module> highestImplementer_; // The highest-priority module to implement a function
	
	private static boolean checkDoesImplement(String function, Module module) 
	{
		boolean check = module.getConfig().doesImplement_.get(function);
		if (highestImplementer_.get(function) == null && check) highestImplementer_.put(function, module);
		
		return check;
	}
	// Checks if module implements function. If it does, and if nothing else has yet, then sets highestImplementer_ of function to module
	// Functions are listed in Module.java
	
	public static void init(String server) // Loads all modules from a server pack
	{
		String path = "Resources/Server Packs/" + server + "/Modules/";
		
		ArrayList<String> moduleList;
		java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>(){}.getType();
		moduleList = JSONManager.deserializeArrayJSON(MiscUtils.readText(path + "ModuleList.json"), listType); // Open up the list of files
		modules_ = new HashMap<String, Module>();
		highestImplementer_ = new HashMap<String, Module>();
		
		for (int i = 0; i < moduleList.size(); ++i) // From highest priority to lowest
		{			
			try
			{
				String moduleFolderPath = path + moduleList.get(i) + "/";
				String moduleJarPath = path + moduleList.get(i) + ".jar";
				URL folderUrl = (new File(moduleFolderPath)).toURI().toURL();
				URL jarUrl = (new File(moduleJarPath)).toURI().toURL();
				URL[] urls = new URL[2];
				urls[0] = folderUrl;
				urls[1] = jarUrl;
			
				URLClassLoader loader = new URLClassLoader(urls); // Open up [module name].jar
				Class moduleClass = loader.loadClass(moduleList.get(i)); // Open up class [module name] from [module name].jar
				Module module = (Module) moduleClass.getDeclaredConstructor().newInstance();
				
				module.getConfig().priority_ = i;
				
				checkDoesImplement("setup", module);
				checkDoesImplement("loadWorld", module);
				
				modules_.put(moduleList.get(i), module);
				Logging.logNotice("Loaded module " + moduleList.get(i));
			}
			catch (Exception e) {Logging.logException(e);}
		}
		
		Logging.logNotice("Finished loading modules.");
	}

	public static Module getModule(String fileName) // In case you need a specific module
	{
		return modules_.get(fileName);
	}
	
	// Default access to highest-priority functions
	public static GameWorld setup()
	{
		return highestImplementer_.get("setup").setup();
	}

	public static GameWorld loadWorld(String server)
	{
		return highestImplementer_.get("loadWorld").loadWorld(server);
	}
}
