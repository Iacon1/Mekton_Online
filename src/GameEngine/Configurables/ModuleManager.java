// By Iacon1
// Created 05/31/2021
// Manages modules
// TODO mod support - Loading from jars & folders in server packs instead of from source

package GameEngine.Configurables;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleTypes.Module;
import GameEngine.Configurables.ModuleTypes.Module.ModuleConfig;
import Utils.DataManager;
import Utils.Logging;
import Utils.MiscUtils;

public final class ModuleManager
{
	private static final String modulesPathName = "Modules"; // Where the modules are
	private static final String extension = ".mom"; // Mekton Online Module
	
	private static Map<String, Module> modules; // The actual modules, by file name
	private static List<String> loadOrder; // The module names by load order

	private static ClassLoader classLoader;

	public static <T> boolean doesImplement(Module module, Class<T> moduleType) // Does module implement that module type? 
	{
		return moduleType.isAssignableFrom(module.getClass());
	}

	public static void loadModules() // Loads all modules from the server pack in GameInfo
	{		
		modules = new HashMap<String, Module>();
		loadOrder = new ArrayList<String>();
		Logging.logNotice("Loading modules.");
		
		String[] moduleFNames = MiscUtils.listFilenames(GameInfo.inServerPack(modulesPathName + "/"), extension);
		List<URL> moduleURLs = new ArrayList<URL>();
		
		Logging.logNotice("Modules: " + MiscUtils.arrayToString(moduleFNames, ", "));
		
		for (String moduleFName : moduleFNames)
		{
			try
			{
				String absFName = GameInfo.inServerPack(modulesPathName + "/" + moduleFName);
				JarFile moduleJar = new JarFile(absFName);
				JarEntry configEntry = moduleJar.getJarEntry(modulesPathName + "/" + moduleFName.substring(0, moduleFName.length() - 4) + "/ModuleConfig.json");
				ModuleConfig defaultConfig = DataManager.deserialize(MiscUtils.readZIPEntry(moduleJar, configEntry), ModuleConfig.class);
				
				// Determine place in load order: If I require someone then go after them otherwise go before them.
				for (int i = loadOrder.size() - 1; i >= -1; --i)
					if (i == -1 || Arrays.binarySearch(defaultConfig.dependencies, loadOrder.get(i)) >= 0)
					{
						loadOrder.add(i + 1, defaultConfig.moduleName);
						moduleURLs.add(i + 1, (new File(absFName)).toURI().toURL());
						break;
					}			
				moduleJar.close();
			}
			catch (Exception e)
			{
				Logging.logException(e);
				Logging.logError("Did not load module @ " + GameInfo.inServerPack(modulesPathName + "/" + moduleFName) + ".");
				continue;
			}
		}
		// Load all into class loader
		classLoader = new URLClassLoader(moduleURLs.toArray(new URL[] {}));

		for (URL url : moduleURLs)
		{
			String filePath = url.getFile();
			String moduleName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length() - 4);
			try
			{
				Module module = (Module) classLoader.loadClass(modulesPathName + "." + moduleName + "." + moduleName).getDeclaredConstructor().newInstance();
				ModuleConfig config = DataManager.deserialize(new String(MiscUtils.readResource(classLoader, modulesPathName + "/" + moduleName + "/" + "ModuleConfig.json")), ModuleConfig.class);
				module.initModule(config);
				modules.put(module.getConfig().moduleName, module);
			}
			catch (Exception e)
			{
				Logging.logException(e);
				Logging.logError("Malformed module " + filePath + ".");
				break;
			}
		}
		Logging.logNotice("Finished loading modules.");
		
		
	}
	public static ClassLoader getLoader()
	{
		return classLoader;
	}
	public static Module getModule(String fileName) // In case you need a specific module
	{
		return modules.get(fileName);
	}

	public static <T extends Module> T getHighestOfType(Class<T> moduleType, Module delegate) // Get highest implementer of a moduleType, ignoring result delegate if not null
	{
		if (loadOrder == null) return null;
		for (int i = loadOrder.size() - 1; i >= 0; --i)
		{
			Module module = modules.get(loadOrder.get(i));
			if (module == null) return null;
			if (doesImplement(module, moduleType) && (delegate == null || module != delegate)) return (T) module;
		}
		
		return null;
	}
	public static <T extends Module> T getHighestOfType(Class<T> moduleType) {return getHighestOfType(moduleType, null);}
	
	public static int size()
	{
		return modules.size();
	}
}
