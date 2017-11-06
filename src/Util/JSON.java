package Util;

import org.json.simple.JSONArray;

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
}
