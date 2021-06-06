// By Iacon1
// Created 05/31/2021
// Manages modules
// TODO mod support - Loading from jars & folders in server packs instead of from source

package GameEngine.Configurables;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import GameEngine.GameEntity;
import GameEngine.GameWorld;
import Server.Account;
import Server.Server;
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
	
	private static ArrayList<String> getModules(String path) // Lists all modules
	{
		ArrayList<String> moduleList;
		/*java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>(){}.getType();
		moduleList = JSONManager.deserializeArrayJSON(MiscUtils.readText(path + "ModuleList.json"), listType); // Open up the list of files
		*/
		
		moduleList = new ArrayList<String>();
		moduleList.add("TestModule");
		
		return moduleList;
	}
	
	private static void loadModule(String path, String moduleFile, int pri) throws Exception
	{
		// TODO this doesn't actually work?
		String moduleFolderPath = path + moduleFile + "/";
		String moduleJarPath = path + moduleFile + ".jar";
		URL folderUrl = (new File(MiscUtils.getAbsolute(moduleFolderPath))).toURI().toURL();
		URL jarUrl = (new File(MiscUtils.getAbsolute(moduleJarPath))).toURI().toURL();
		URL[] urls = new URL[2];
		urls[0] = folderUrl;
		urls[1] = jarUrl;
		
		URLClassLoader loader = new URLClassLoader(urls);
		Class moduleClass = loader.loadClass(moduleFile + "." + moduleFile);
		Module module = (Module) moduleClass.getDeclaredConstructor().newInstance();
		
		module.getConfig().priority_ = pri;
		
		checkDoesImplement("setup", module);
		checkDoesImplement("loadWorld", module);
		checkDoesImplement("makePlayer", module);
		checkDoesImplement("wakePlayer", module);
		checkDoesImplement("sleepPlayer", module);
		checkDoesImplement("deletePlayer", module);
		
		modules_.put(moduleFile, module);
		Logging.logNotice("Loaded module " + moduleFile);
	}
	
	public static void init(String server) // Loads all modules from a server pack
	{
		String path = "Resources/Server Packs/" + server + "/Modules/";
		
		ArrayList<String> moduleList = getModules(path);
		
		modules_ = new HashMap<String, Module>();
		highestImplementer_ = new HashMap<String, Module>();
		
		for (int i = 0; i < moduleList.size(); ++i) // From highest priority to lowest
		{			
			try {loadModule(path, moduleList.get(i), i);}
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
	
	public static GameEntity makePlayer(Server server, Account account)
	{
		return highestImplementer_.get("makePlayer").makePlayer(server, account);
	}
	public static GameEntity wakePlayer(Server server, Account account)
	{
		return highestImplementer_.get("wakePlayer").wakePlayer(server, account);
	}
	public static GameEntity sleepPlayer(Server server, Account account)
	{
		return highestImplementer_.get("sleepPlayer").sleepPlayer(server, account);
	}
	public static GameEntity deletePlayer(Server server, Account account)
	{
		return highestImplementer_.get("deletePlayer").deletePlayer(server, account);
	}
}
