package Scenarios;

import Objects.BaseObject;
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

	public static BaseObject fromJson(JSONObject json) {
		JSONObject objsJson = (JSONObject) json.get("scenario");
		objsJson = (JSONObject) objsJson.get("objs");
		BiConsumer<String, JSONObject> bc = (String key, JSONObject value) ->
				System.out.println(key + " : " + value.toJSONString());
		objsJson.forEach(bc);
		return new JSONScenario(null);
	}

	abstract protected ArrayList<BaseObject> getBaseObjs();

	public static BaseScenario getRandomScenario() {
		return new FarmScenario();
	}
}
