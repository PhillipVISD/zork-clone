package Objects;

import Player.Player;
import Text.EnglishFactory;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * A base abstract object class to be used by other objects, this will implement base methods that should be implemented
 * by other game objects.
 */
abstract public class BaseObject {
	public BaseObject(String name, String determiner, Boolean animate, Boolean canContain, ArrayList<BaseObject> objArr) {
		this.name = name;
		this.determiner = determiner;
		this.animate = animate;
		this.canContain = canContain;

		if (this.canContain) {
			if (objArr != null) {
				this.setGameObjects(objArr);
			}
			else {
				this.setGameObjects(new ArrayList<BaseObject>());
			}
		}
	}

	public BaseObject(String name, String determiner, Boolean animate, Boolean canContain) {
		this(name, determiner, animate, canContain, null);
	}

	public static BaseObject fromJson(JSONObject jsonObj) {
		return new BaseJSONObject("a", "a", true, true, new ArrayList<BaseObject>(), "a", "a");
	}

	public ArrayList<BaseObject> getGameObjects() {
		return this.gameObjects;
	}



	public BaseObject getObjectByName(String name) {
		for (BaseObject obj : this.gameObjects) {
			if (obj.getName().equals(name)) {
				return obj;
			}
		}
		return null;
	}

	public String describe() {
		return EnglishFactory.fromGameObjs(this.getGameObjects());
	}

	private ArrayList<BaseObject> gameObjects; // Array of game objects this scope contains

	public String action(String verb) {
		verb = verb.toLowerCase();
		if (verb.equals("inspect") ||
				verb.equals("look") ||
				verb.equals("describe")) {
			return this.describe();
		}
		return null;
	}

	public void setGameObjects(ArrayList<BaseObject> objArr) {
		this.gameObjects = objArr;
		if (objArr != null) {
			for (BaseObject obj : this.gameObjects) {
				obj.parent = this;
			}
		}
	}

	public Boolean isPlural = false; // Whether the object should be considered plural or not

	String name; // Name of object in speech
	String determiner; // Determiner like 'a', 'an', or 'the'

	protected String preDesc; // Description of object before name
	protected String postDesc; // Description of object after name

	Boolean animate;  // Whether an object is alive or not

	Boolean canContain; // Whether an object can contain other objects

	public BaseObject parent = null; // The parent of this game object

	public void talk() {
		if (this.animate) {
			System.out.println("Undefined speech of object.");
		} else {
			System.out.println("You cannot talk to an inanimate object.");
		}
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		StringBuilder desc = new StringBuilder();
		desc.append(this.determiner);
		desc.append(" ");
		if (this.preDesc != null) {
			desc.append(preDesc).append(" ");
		}
		desc.append(this.name);
		if (this.postDesc != null) {
			desc.append(" ").append(this.postDesc);
		}
		return desc.toString();
	}

	public String illegal() {
		return "You cannot do that!";
	}

	public String enter(Player player) {
		if (this.canContain) {
			player.scope = this;
			return "You enter " + this.determiner + " " + this.name + "\n" + this.describe();
		}
		else {
			return "You can't enter a " + this.name;
		}
	}

	public String exit(Player player) {
		if (this.parent != null) {
			player.scope = this.parent;
			return "You exit " + this.determiner + " " + this.name + "\n" + this.parent.describe();
		}
		else {
			return "Nice try, you can't escape that easily.";
		}
	}

	public String pickup(Player player) {
		return illegal();
	};
}


