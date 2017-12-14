package org.vashonsd.zork.Player;

import org.vashonsd.zork.Objects.BaseObject;
import org.vashonsd.zork.Scenarios.BaseScenario;
import org.vashonsd.zork.Text.EnglishFactory;

import java.util.ArrayList;

public class Player {
	public BaseScenario scenario;
	public String name;

	/**
	 * Initialize the Player object with a scenario of the player and name.
	 * @param scenario The starting scenario.
	 * @param name The name of the player.
	 */
	public Player(BaseScenario scenario, String name) {
		this.inventory = new ArrayList<>();
		this.scope = null;
		this.scenario = scenario;
		this.name = name;
	}

	public ArrayList<BaseObject> inventory; // Inventory of objects
	public BaseObject scope; // Scope of player

	/**
	 * Returns the current scope of the player, being a scenario or object.
	 * @return A BaseObject which represents the current scope of the player.
	 */
	public BaseObject getScope() {
		if (this.scope != null) {
			return this.scope;
		}
		else {
			return this.scenario;
		}
	}

	/**
	 * Describes what objects are in the player's inventory using EnglishFactory.
	 * @return String of what objects are in the player's inventory.
	 * @see EnglishFactory
	 */
	public String describeInventory() {
		return EnglishFactory.fromGameObjs(this.inventory, true);
	}

	/**
	 * Adds an object to the players inventory.
	 * @param object The BaseObject to be added.
	 */
	public void addToInventory(BaseObject object) {
		if (this.inventory == null) {
			this.inventory = new ArrayList<>();
			this.inventory.add(object);
		}
		else {
			this.inventory.add(object);
		}
	}
}
