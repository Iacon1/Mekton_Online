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
import java.util.List;
import java.util.Map;

import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleTypes.Module;
import Utils.Logging;
import Utils.MiscUtils;

public final class ModuleManager
{
	private static Map<String, Module> modules; // The actual modules, by file name
	private static List<String> modulePriorities; // The module names by priority

	public static <T> boolean doesImplement(Module module, Class<T> moduleType) // Does module implement that module type? 
	{
		return moduleType.isAssignableFrom(module.getClass());
	}
	
	private static List<String> getModules(String path) // Lists all modules
	{
		List<String> moduleList;
		/*java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>(){}.getType();
		moduleList = JSONManager.deserializeArrayJSON(MiscUtils.readText(path + "ModuleList.json"), listType); // Open up the list of files
		*/
		
		moduleList = new ArrayList<String>();
		moduleList.add("BaseModule");
		moduleList.add("HexUtilities");
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

		modules.put(moduleFile, module);
		modulePriorities.add(moduleFile);
		
		Logging.logNotice("Loaded module " + moduleFile);
		module.initModule();
		loader.close();
	}
	
	public static void init() // Loads all modules from the server pack in GameInfo
	{
		String path = GameInfo.getServerPackResource("Modules/");
		
		List<String> moduleList = getModules(path);
		
		modules = new HashMap<String, Module>();
		modulePriorities = new ArrayList<String>();
		
		for (int i = 0; i < moduleList.size(); ++i) // From highest priority to lowest
		{			
			try {loadModule(path, moduleList.get(i));}
			catch (Exception e) {Logging.logException(e);}
		}
		
		Logging.logNotice("Finished loading modules.");
	}

	public static Module getModule(String fileName) // In case you need a specific module
	{
		return modules.get(fileName);
	}

	public static <T extends Module> T getHighestOfType(Class<T> moduleType, Module delegate) // Get highest implementer of a moduleType, ignoring result delegate if not null
	{
		for (int i = modulePriorities.size() - 1; i >= 0; --i)
		{
			Module module = modules.get(modulePriorities.get(i));
			if (doesImplement(module, moduleType) && (delegate == null || module != delegate)) return (T) module;
		}
		
		return null;
	}
	
	public static <T extends Module> T getHighestOfType(Class<T> moduleType) // Get highest implementer of a moduleType
	{
		return getHighestOfType(moduleType, null);
	}
}
