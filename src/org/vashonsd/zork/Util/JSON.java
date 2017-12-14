package org.vashonsd.zork.Util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.vashonsd.zork.Scenarios.BaseScenario;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JSON {
	public static String[] toStringArray(JSONArray array) {
		if(array==null)
			return null;

		String[] arr=new String[array.size()];
		for(int i=0; i<arr.length; i++) {
			arr[i]=(String) array.get(i);
		}
		return arr;
	}

	public static BaseScenario scenarioFromJsonName(String jsonName) throws IOException {
		if (jsonName != null) {
			JSONParser jsonParser = new JSONParser();
			URL url = new File(System.getProperty("user.dir") + "/" + jsonName).toURI().toURL();
			InputStream jsonIS = url.openStream();

			Object obj = null;
			try {
				obj = jsonParser.parse(convertStreamToString(jsonIS));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			JSONObject jsonObj = (JSONObject) obj;

			return BaseScenario.fromJson(jsonObj);
		}
		else {
			return BaseScenario.getRandomScenario();
		}
	}

	/**
	 * Converts a InputStream object to a string.
	 * @param is The input stream to be converted.
	 * @return The String version of the InputStream.
	 */
	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
