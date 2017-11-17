package Objects;

import Dynamic.DynamicManager;
import Dynamic.DynamicPL;
import Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class BaseJSONObject extends BaseObject {

	DynamicManager dm;

	/**
	 * Initializes variables on the object.
	 * @param name The name of the object.
	 * @param determiner What determiner to use like 'a' or 'the'.
	 * @param animate Whether the object is animate (alive).
	 * @param canContain Whether the object can contain other objects inside of it.
	 * @param objs The ArrayList of object it contains, null if none.
	 * @param preDesc The description of the object before the name.
	 * @param postDesc The description of the object after the name.
	 */
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

	/**
	 * Gets and returns a DynamicManager object, it will create one and add it to its cache if it did not exist before.
	 * @param className The name nof the class for the DynamicManager object.
	 * @return The DynamicManager object.
	 * @see DynamicManager
	 */
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

	/**
	 * Performs an action on the object with a verb and player object.
	 * @param verb The verb or action that is being done.
	 * @param player The player object that is being manipulated, usually the player that said the command.
	 * @return A DynamicPL object with the out parameters like Player and a response String.
	 * @see DynamicPL
	 */
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
