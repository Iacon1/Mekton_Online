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

import GameEngine.GameCanvas;
import GameEngine.GameEntity;
import GameEngine.GameInfo;
import Net.StateFactory;
import Server.Account;
import Server.GameServer;
import Utils.Logging;
import Utils.MiscUtils;

public final class ModuleManager
{
	private static HashMap<String, Module> modules_; // The actual modules, by file name
	private static HashMap<String, Module> highestImplementer_; // The highest-priority module to implement a function
	
	public static void attemptRegister(String function, Module module)
	{
		if (highestImplementer_.get(function) == null) highestImplementer_.put(function, module);
	}
	public static boolean checkDoesImplement(String function, Module module) 
	{
		boolean check = module.getConfig().doesImplement_.get(function);
		if (check) attemptRegister(function, module);
		
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
		moduleList.add("BaseModule");
		moduleList.add("MektonCore");
		moduleList.add("TestModule");
		
		return moduleList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		//Class moduleClass = loader.loadClass(moduleFile + "." + moduleFile);
		Class moduleClass = loader.loadClass("Modules." + moduleFile + "." + moduleFile); // TODO
		Module module = (Module) moduleClass.getDeclaredConstructor().newInstance();
		
		module.getConfig().priority_ = pri;
		
		checkDoesImplement("makeServer", module);
		checkDoesImplement("setup", module);
		checkDoesImplement("loadWorld", module);
		
		checkDoesImplement("drawWorld", module);
		
		checkDoesImplement("makePlayer", module);
		checkDoesImplement("wakePlayer", module);
		checkDoesImplement("sleepPlayer", module);
		checkDoesImplement("deletePlayer", module);
		
		checkDoesImplement("clientFactory", module);
		checkDoesImplement("handlerFactory", module);
		
		modules_.put(moduleFile, module);
		Logging.logNotice("Loaded module " + moduleFile);
		module.init();
		loader.close();
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
	public static Module getHighestImplementer(String function) // Default access to highest-priority functions
	{
		return highestImplementer_.get(function);
	}
	@SuppressWarnings("rawtypes")
	public static GameServer makeServer() // Sets up the server (not what's in the server!)
	{
		return getHighestImplementer("makeServer").makeServer();
	}

	public static void setup()
	{
		getHighestImplementer("setup").setup();
	}
	public static GameInfo loadWorld(String server)
	{
		return getHighestImplementer("loadWorld").loadWorld(server);
	}
	
	public static void drawWorld(GameCanvas canvas)
	{
		getHighestImplementer("drawWorld").drawWorld(canvas);
	}

	public static GameEntity makePlayer(Account account)
	{
		return getHighestImplementer("makePlayer").makePlayer(account);
	}
	@SuppressWarnings("rawtypes")
	public static GameEntity wakePlayer(Account account)
	{
		return getHighestImplementer("wakePlayer").wakePlayer(account);
	}
	@SuppressWarnings("rawtypes")
	public static GameEntity sleepPlayer(Account account)
	{
		return getHighestImplementer("sleepPlayer").sleepPlayer(account);
	}
	@SuppressWarnings("rawtypes")
	public static GameEntity deletePlayer(Account account)
	{
		return getHighestImplementer("deletePlayer").deletePlayer(account);
	}
	
	public static StateFactory clientFactory() // Client factory
	{
		return getHighestImplementer("clientFactory").clientFactory();
	}
	public static StateFactory handlerFactory() // Handler factory
	{
		return getHighestImplementer("handlerFactory").handlerFactory();
	}
}
