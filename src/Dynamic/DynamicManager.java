package Dynamic;

import Objects.BaseObject;
import Player.Player;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DynamicManager {
	public Class cls;

	/**
	 * A DynamicManager manages the interactions with a class that is dynamically loaded at runtime.
	 * @param className The name of the class that should be loaded and managed by this DynamicManager.
	 */

	public DynamicManager(String className) {
//		ClassLoader classLoader = new URLClassLoader(new URL[] {new URL("file:/C:\\Users\\cutter.phillip\\Documents\\Zork Clone")});

		URL url = null;
		try {
			url = new File(System.getProperty("user.dir")).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		URL[] urls = {url};

//		System.out.println(url.toString());

		ClassLoader classLoader = new URLClassLoader(urls);

		try {
			this.cls = classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method simplifies the calling of static behaviour methods. It will call a method on the loaded class with
	 * a name.
	 * @param object The object that the action is happening to.
	 * @param verb The action word exactly, used to lookup what method to call.
	 * @param player The player that can be modified by the method that will be invoked.
	 * @return The Dynamic Payload response.
	 */
	public DynamicPL method(BaseObject object, String verb, Player player) {
		DynamicPL out = null;
		try {
			Method m = this.cls.getMethod(verb, DynamicPL.class);
			DynamicPL payload = new DynamicPL();
			payload.setIn(verb, player, object);
			out = (DynamicPL) m.invoke(null, (Object) payload);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return out;
	}

}
