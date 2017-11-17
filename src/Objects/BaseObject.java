package Objects;

import Player.Player;
import Text.EnglishFactory;

import java.util.ArrayList;

/**
 * A base abstract object class to be used by other objects, this will implement base methods that should be implemented
 * by other game objects.
 */
abstract public class BaseObject {

	/**
	 * Initializes a BaseObject.
	 * @param name The name of the object.
	 * @param determiner What determiner should be used like 'a' or 'the'.
	 * @param animate Whether or not the object is animate (alive).
	 * @param canContain Whether or not the object can contain other objects.
	 * @param objArr The ArrayList of the objects to contain, null if none.
	 */
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

	/**
	 * A constructor signature with no need for an objArr to be passed.
	 * @param name The name of the object.
	 * @param determiner What determiner should be used like 'a' or 'the'.
	 * @param animate Whether or not the object is animate (alive).
	 * @param canContain Whether or not the object can contain other objects.
	 */
	public BaseObject(String name, String determiner, Boolean animate, Boolean canContain) {
		this(name, determiner, animate, canContain, null);
	}

	public ArrayList<BaseObject> getGameObjects() {
		return this.gameObjects;
	}


	/**
	 * Attempts to get another BaseObject in this objects direct children.
	 * @param name The name of the object to try to find.
	 * @return The BaseObject if found, otherwise null.
	 */
	public BaseObject getObjectByName(String name) {
		for (BaseObject obj : this.gameObjects) {
			if (obj.getName().equals(name)) {
				return obj;
			}
		}
		return null;
	}

	/**
	 * Describes this object in human readable text using EnglishFactory.
	 * @return A string of text that describes the object.
	 * @see EnglishFactory
	 */
	public String describe() {
		return EnglishFactory.fromGameObjs(this.getGameObjects());
	}

	private ArrayList<BaseObject> gameObjects; // Array of game objects this scope contains

	/**
	 * Perform an action on this specific BaseObject, usually meant to be overridden.
	 * @param verb The verb or action to perform
	 * @return A string of the result.
	 */
	public String action(String verb) {
		verb = verb.toLowerCase();
		if (verb.equals("inspect") ||
				verb.equals("look") ||
				verb.equals("describe")) {
			return this.describe();
		}
		return null;
	}

	/**
	 * Allows the BaseObject child object to easily be changed after it has been instantiated.
	 * @param objArr The objects it should now contain.
	 */
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

	public String getName() {
		return this.name;
	}

	/**
	 * Gets a description string of this object using the name, preDesc, and postDesc.
	 * @return A string describing the object.
	 */
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

	/**
	 * A very simple method that returns a string signifying the action was illegal.
	 * @return A string signifying the action was illegal.
	 */
	public String illegal() {
		return "You cannot do that!";
	}

	/**
	 * Called when trying to exit or leave an object. If you are in a house and you say "leave house" this method will
	 * be called on the house object.
	 * @param player The player that is exiting.
	 * @return A string signifying whether or not the action was valid.
	 */
	public String exit(Player player) {
		if (this.parent != null) {
			player.scope = this.parent;
			return "You exit " + this.determiner + " " + this.name + "\n" + this.parent.describe();
		}
		else {
			return "Nice try, you can't escape that easily.";
		}
	}
}


