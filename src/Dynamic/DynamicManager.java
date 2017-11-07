package Dynamic;

import Player.Player;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DynamicManager {
	public Class cls;

	public DynamicManager(String className) throws MalformedURLException {
//		ClassLoader classLoader = new URLClassLoader(new URL[] {new URL("file:/C:\\Users\\cutter.phillip\\Documents\\Zork Clone")});

		URL url = new File(System.getProperty("user.dir")).toURI().toURL();

		URL[] urls = {url};

//		System.out.println(url.toString());

		ClassLoader classLoader = new URLClassLoader(urls);

		try {
			this.cls = classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public DynamicPL method(String name, String verb, Player player) {
		DynamicPL out = null;
		try {
			Method m = this.cls.getMethod(name, DynamicPL.class);
			DynamicPL payload = new DynamicPL();
			payload.setIn(verb, player);
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
