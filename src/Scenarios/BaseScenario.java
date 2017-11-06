package Scenarios;

import Objects.BaseJSONObject;
import Objects.BaseObject;
import Util.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.function.BiConsumer;

abstract public class BaseScenario extends BaseObject {

	public ArrayList<BaseObject> objs;

	public BaseScenario(ArrayList<BaseObject> objs) {
		super("scenario", "a", false, true, null);
		if (objs != null) {
			this.objs = objs;
		}
		this.setGameObjects(this.getBaseObjs());
	}

	public static BaseScenario fromJson(JSONObject json) {
		JSONObject objsJson = (JSONObject) json.get("scenario");
		objsJson = (JSONObject) objsJson.get("objs");
		ArrayList<BaseObject> objs = new ArrayList<>();

		for (Object o : objsJson.keySet()) {
			JSONObject objJson = (JSONObject) objsJson.get(o);
			BaseObject object = objFromJson(o.toString(), objJson);
			objs.add(object);
		}

		JSONScenario scenario = new JSONScenario(objs);

		return scenario;
	}

	private static BaseObject objFromJson(String object, JSONObject infoJson) {
		String determiner = (String) infoJson.getOrDefault("determiner", "a");
		Boolean animate = (Boolean) infoJson.getOrDefault("animate", false);
		Boolean canContain = infoJson.getOrDefault("objs", null) != null;
		String preDesc = (String) infoJson.getOrDefault("preDesc", "");
		String postDesc = (String) infoJson.getOrDefault("postDesc", "");
		String name = object;

		ArrayList<BaseObject> objs = new ArrayList<>();
		if (infoJson.getOrDefault("objs", null) != null) {
			BiConsumer<String, JSONObject> objsBC = (String objName, JSONObject objInfoJson) -> {
				objs.add(objFromJson(objName, objInfoJson));
			};
			JSONObject objObjs = (JSONObject) infoJson.get("objs");
			objObjs.forEach(objsBC);
		}

		BaseJSONObject jsonObj = new BaseJSONObject(name, determiner, animate, canContain, objs, preDesc, postDesc);

		ArrayList<BaseObject> objArr = new ArrayList<>();

//			System.out.println(key + " : " + value);
		BiConsumer<String, JSONObject> actionsBC = (String action, JSONObject actionJson) -> {
			JSONArray responses = (JSONArray) actionJson.getOrDefault("responses", new JSONArray());
			jsonObj.addBehaviour(action, JSON.toStringArray(responses));
		};
		JSONObject actionsJson = (JSONObject) infoJson.get("methods");
		actionsJson.forEach(actionsBC);

		return jsonObj;
	}

	abstract protected ArrayList<BaseObject> getBaseObjs();

	public static BaseScenario getRandomScenario() {
		return new FarmScenario();
	}
}
