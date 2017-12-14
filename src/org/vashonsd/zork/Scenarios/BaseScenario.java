package org.vashonsd.zork.Scenarios;

import org.vashonsd.zork.Dynamic.DynamicManager;
import org.vashonsd.zork.Objects.BaseJSONObject;
import org.vashonsd.zork.Objects.BaseObject;
import org.vashonsd.zork.Util.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.function.BiConsumer;

abstract public class BaseScenario extends BaseObject {

	public ArrayList<BaseObject> objs;

	/**
	 * A scenario encompasses an entire game and all of the objects in the game.
	 * @param objs What object this scenario should have.
	 */
	public BaseScenario(ArrayList<BaseObject> objs) {
		super("scenario", "a", false, true, null);
		if (objs != null) {
			this.objs = objs;
		}
		this.setGameObjects(this.getBaseObjs());
	}

	/**
	 * Returns a JSONScenario from a JSONObject in the form of a BaseScenario. It also initializes child object of the
	 * scenario using objFromJson.
	 * @param json A JSONObject detailing the scenario.
	 * @return A BaseScenario in the form of a BaseScenario.
	 */
	public static BaseScenario fromJson(JSONObject json) {
		JSONObject objsJson = (JSONObject) json.get("scenario");
		objsJson = (JSONObject) objsJson.get("objs");
		ArrayList<BaseObject> objs = new ArrayList<>();

		for (Object o : objsJson.keySet()) {
			JSONObject objJson = (JSONObject) objsJson.get(o);
			BaseObject object = objFromJson(o.toString(), objJson, json);
			objs.add(object);
		}

		JSONScenario scenario = new JSONScenario(objs);

		return scenario;
	}

	/**
	 * Given JSON it will return a BaseObject in the form of a JSONObject.
	 * @param object The name of the object.
	 * @param infoJson The JSON of the individual object.
	 * @param baseJson The JSON of the whole scenario.
	 * @return
	 */
	private static BaseObject objFromJson(String object, JSONObject infoJson, JSONObject baseJson) {
		String determiner = (String) infoJson.getOrDefault("determiner", "a");
		Boolean animate = (Boolean) infoJson.getOrDefault("animate", false);
		Boolean canContain = infoJson.getOrDefault("objs", null) != null;
		String preDesc = (String) infoJson.getOrDefault("preDesc", "");
		String postDesc = (String) infoJson.getOrDefault("postDesc", "");
		String name = object;

		ArrayList<BaseObject> objs = new ArrayList<>();
		if (infoJson.getOrDefault("objs", null) != null) {
			BiConsumer<String, JSONObject> objsBC = (String objName, JSONObject objInfoJson) -> {
				objs.add(objFromJson(objName, objInfoJson, baseJson));
			};
			JSONObject objObjs = (JSONObject) infoJson.get("objs");
			objObjs.forEach(objsBC);
		}

		String customBehaviour = (String) baseJson.getOrDefault("customBehaviour", null);
		DynamicManager dm = null;

		if (customBehaviour != null) {
			dm = new DynamicManager(customBehaviour);
		}

		BaseJSONObject jsonObj = new BaseJSONObject(name, determiner, animate, canContain, objs, preDesc, postDesc);

		ArrayList<BaseObject> objArr = new ArrayList<>();

//			System.out.println(key + " : " + value);
		BiConsumer<String, JSONObject> actionsBC = (String action, JSONObject actionJson) -> {
			JSONArray responses = (JSONArray) actionJson.getOrDefault("responses", new JSONArray());
			String classStr = (String) actionJson.getOrDefault("class", null);
			if (classStr != null) {
				jsonObj.addClassBehaviour(action, classStr);
			}
			else{
				jsonObj.addBehaviour(action, JSON.toStringArray(responses));
			}
		};
		JSONObject actionsJson = (JSONObject) infoJson.get("methods");
		actionsJson.forEach(actionsBC);

		return jsonObj;
	}

	/**
	 * An abstract method that returns the objects that will be used.
	 * @return An ArrayList of BaseObjects to be used.
	 */
	abstract protected ArrayList<BaseObject> getBaseObjs();

	/**
	 * Returns a hardcoded random scenario from its pool of implemented scenarios. Currently only FarmScenario.
	 * @return BaseScenario that is chosen at random from a pool of implemented scenarios.
	 */
	public static BaseScenario getRandomScenario() {
		return new FarmScenario(); // 100% random guaranteed
	}
}
