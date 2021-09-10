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

import GameEngine.Configurables.ModuleTypes.Module;
import Utils.Logging;
import Utils.MiscUtils;

public final class ModuleManager
{
	private static HashMap<String, Module> modules_; // The actual modules, by file name
	private static ArrayList<String> modulePriorities_; // The module names by priority

	public static <T> boolean doesImplement(Module module, Class<T> moduleType) // Does module implement that module type? 
	{
		return moduleType.isAssignableFrom(module.getClass());
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

	private static void loadModule(String path, String moduleFile) throws Exception
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

		modules_.put(moduleFile, module);
		modulePriorities_.add(moduleFile);
		
		Logging.logNotice("Loaded module " + moduleFile);
		module.initModule();
		loader.close();
	}
	
	public static void init(String server) // Loads all modules from a server pack
	{
		String path = "Resources/Server Packs/" + server + "/Modules/";
		
		ArrayList<String> moduleList = getModules(path);
		
		modules_ = new HashMap<String, Module>();
		modulePriorities_ = new ArrayList<String>();
		
		for (int i = 0; i < moduleList.size(); ++i) // From highest priority to lowest
		{			
			try {loadModule(path, moduleList.get(i));}
			catch (Exception e) {Logging.logException(e);}
		}
		
		Logging.logNotice("Finished loading modules.");
	}

	public static Module getModule(String fileName) // In case you need a specific module
	{
		return modules_.get(fileName);
	}

	public static <T> T getHighestOfType(Class<T> moduleType, Module delegate) // Get highest implementer of a moduleType, ignoring result delegate if not null; Please make sure T is a module type!
	{
		for (int i = modulePriorities_.size() - 1; i >= 0; --i)
		{
			Module module = modules_.get(modulePriorities_.get(i));
			if (doesImplement(module, moduleType) && (delegate == null || module != delegate)) return (T) module;
		}
		
		return null;
	}
	
	public static <T> T getHighestOfType(Class<T> moduleType) // Get highest implementer of a moduleType; Please make sure T is a module type!
	{
		return getHighestOfType(moduleType, null);
	}
}
