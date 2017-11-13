package Objects;

import Dynamic.DynamicManager;
import Dynamic.DynamicPL;
import Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class BaseJSONObject extends BaseObject {

	DynamicManager dm;

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
	public HashMap<String, String> classBehaviour = new HashMap<>();
	private HashMap<String, DynamicManager> dynamicManagers = new HashMap<>();

	public void addBehaviour(String action, String[] responses) {
		this.behaviour.put(action, responses);
	}

	public void addClassBehaviour(String action, String behaviour) {
		this.classBehaviour.put(action, behaviour);
	}

	private DynamicManager getDynamicManager(String className) {
		if (this.dynamicManagers.containsKey(className)) {
			return dynamicManagers.get(className);
		}
		else {
			DynamicManager dm = new DynamicManager(className);
			this.dynamicManagers.put(className, dm);
			return dm;
		}
	}

	public DynamicPL verbAction(String verb, Player player) {
		DynamicPL pl = new DynamicPL();
		pl.setIn(verb, player, this);
		if (this.classBehaviour.containsKey(verb)) {
			DynamicManager dm = this.getDynamicManager(this.classBehaviour.get(verb));
			pl = dm.method(this, verb, player);
		}
		else if (this.behaviour.containsKey(verb)) {
			String[] responses = behaviour.get(verb);
			if (responses.length >= 1) {
				int i = new Random().nextInt(responses.length);
				pl.response = responses[i];
			}
			else {
				pl.response =  "You " + verb.toLowerCase() + " the " + this.getName() + ".";
			}
		}
		else {
			// JSON Object does not define what to do in this context
			pl.response = this.illegal();
		}
		return pl;
	}
}
