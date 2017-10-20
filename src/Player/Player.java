package Player;

import Objects.BaseObject;
import Scenarios.BaseScenario;

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
}
