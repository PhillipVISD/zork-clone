package Player;

import Objects.BaseObject;
import Scenarios.BaseScenario;
import Text.EnglishFactory;

import java.util.ArrayList;

public class Player {
	public BaseScenario scenario;
	public String name;

	public Player(BaseScenario scenario, String name) {
		this.inventory = new ArrayList<>();
		this.scope = null;
		this.scenario = scenario;
		this.name = name;
	}

	public ArrayList<BaseObject> inventory; // Inventory of objects
	public BaseObject scope; // Scope of player

	public BaseObject getScope() {
		if (this.scope != null) {
			return this.scope;
		}
		else {
			return this.scenario;
		}
	}

	public String describeInventory() {
		return EnglishFactory.fromGameObjs(this.inventory, true);
	}

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
