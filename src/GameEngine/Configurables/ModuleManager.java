// By Iacon1
// Created 05/31/2021
// Manages modules
// TODO mod support - Loading from jars & folders in server packs instead of from source

package GameEngine.Configurables;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import GameEngine.GameInfo;
import GameEngine.Configurables.ModuleTypes.Module;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public final class ModuleManager
{
	private static Map<String, Module> modules; // The actual modules, by file name
	private static List<String> modulePriorities; // The module names by priority
	private static URLClassLoader classLoader;
	
	public static <T> boolean doesImplement(Module module, Class<T> moduleType) // Does module implement that module type? 
	{
		return moduleType.isAssignableFrom(module.getClass());
	}

	private static List<String> getModuleNames(String path)
	{
		if (!(new File(GameInfo.getServerPackResource(path + "/Modules/")).exists())) return new ArrayList<String>(); // If this path has no modules folder then return nothing.
		
		String moduleListText = MiscUtils.readText(GameInfo.getServerPackResource(path + "/Modules/ModuleList.json")); // Otherwise, load the module list.
		List<String> currentList = (List<String>) JSONManager.deserializeCollectionJSONList(moduleListText, ArrayList.class, String.class);
		List<String> newList = new ArrayList<String>();
		for (int i = 0; i < currentList.size(); ++i)
		{
			String moduleName = currentList.get(i);
			File moduleFileJ = new File(GameInfo.getServerPackResource(path + "/Modules/" + moduleName + ".jar"));
			if (moduleFileJ.exists()) newList.add(moduleName);
		}
		
		return newList;
	}
	private static List<URL> getModuleURLs(String path) // Lists all modules
	{
		if (!(new File(GameInfo.getServerPackResource(path + "/Modules/")).exists())) return new ArrayList<URL>(); // If this path has no modules folder then return nothing.
		
		String moduleListText = MiscUtils.readText(GameInfo.getServerPackResource(path + "/Modules/ModuleList.json"));
		List<String> currentList = (List<String>) JSONManager.deserializeCollectionJSONList(moduleListText, ArrayList.class, String.class);
		List<URL> newList = new ArrayList<URL>();
		for (int i = 0; i < currentList.size(); ++i)
		{
			String moduleName = currentList.get(i);
			File moduleFileJ = new File(GameInfo.getServerPackResource(path + "/Modules/" + moduleName + ".jar"));
			if (moduleFileJ.exists()) try {newList.add(moduleFileJ.toURI().toURL());}
			catch (Exception e) {Logging.logException(e);}
		}

		return newList;
	}
 // TODO metamodule support
	
	private static void loadModule(String moduleName) throws Exception
	{
		Logging.logNotice("Loading module " + moduleName + ".");
		String path = GameInfo.getServerPackResource("Modules/" + moduleName + ".jar");
		URL url = new File(path).toURI().toURL();
	
		JarFile moduleFile = new JarFile(path);
		Enumeration<JarEntry> entries = moduleFile.entries();
		
		Class moduleClass = null;
		
		// https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
		while (entries.hasMoreElements())
		{
			JarEntry entry = entries.nextElement();
			if (entry.isDirectory() || !entry.getName().endsWith(".class"))
				continue;
			String className = entry.getName().substring(0, entry.getName().length() - 6); // Removes ".class"
			className = className.replace('/', '.'); // From file notation to class notation
			
			if (className.endsWith(moduleName)) moduleClass = classLoader.loadClass(className);
			else classLoader.loadClass(className);
			Logging.logNotice("Loaded class " + className);
		}
		Module module = (Module) moduleClass.getDeclaredConstructor().newInstance();

		modules.put(moduleName, module);
		modulePriorities.add(moduleName);
		
		Logging.logNotice("Loaded module " + moduleName + ".");
		module.initModule();
	}
	
	public static final ClassLoader getLoader()
	{
		return classLoader;
	}
	public static void init() // Loads all modules from the server pack in GameInfo
	{
		URL packURL = null;
		try {packURL = (new File(GameInfo.getServerPackResource("/"))).toURI().toURL();}
		catch (Exception e) {Logging.logException(e); return;}
		
		modules = new HashMap<String, Module>();
		modulePriorities = new ArrayList<String>();

		List<String> moduleNames = getModuleNames("");
		List<URL> moduleURLs = getModuleURLs("");
		
		classLoader = URLClassLoader.newInstance(moduleURLs.toArray(new URL[moduleURLs.size()]));
		
		for (int i = 0; i < moduleNames.size(); ++i) // From highest priority to lowest
		{			
			try {loadModule(moduleNames.get(i));}
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
		if (modulePriorities == null) return null;
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
	
	public static int size()
	{
		return size();
	}
	
	/** Gets the module with that priority.
	 *  
	 */
	public static Module getModule(int priority)
	{
		return modules.get(modulePriorities.get(priority));
	}
}
