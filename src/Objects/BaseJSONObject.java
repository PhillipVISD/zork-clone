package Objects;

import Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class BaseJSONObject extends BaseObject {
	public BaseJSONObject(String name, String determiner, Boolean animate, Boolean canContain,
	                      ArrayList<BaseObject> objs, String preDesc, String postDesc) {
		super(name, "a", false, true, objs);
		if (preDesc != null && preDesc.equals("")) {
			preDesc = null;
		}
		if (postDesc != null && postDesc.equals("")) {
			postDesc = null;
		}
		this.preDesc = preDesc;
		this.postDesc = postDesc;
		this.canContain = canContain;
	}

	public HashMap<String, String[]> behaviour = new HashMap<>();

	public void addBehaviour(String action, String[] responses) {
		this.behaviour.put(action, responses);
	}

	public String verbAction(String verb, Player player) {
		if (this.behaviour.containsKey(verb)) {
			String[] responses = behaviour.get(verb);
			if (responses.length >= 1) {
				int i = new Random().nextInt(responses.length);
				return responses[i];
			}
			else {
				return "You " + verb.toLowerCase() + " the " + this.getName() + ".";
			}
		}
		else {
			// JSON Object does not define what to do in this context
			return this.illegal();
		}
	}
}
