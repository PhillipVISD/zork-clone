package org.vashonsd.zork.Scenarios;

import org.vashonsd.zork.Objects.BaseObject;
import org.vashonsd.zork.Objects.House;

import java.util.ArrayList;

public class FarmScenario extends BaseScenario {
	/**
	 * The constructor for the FarmScenario.
	 */
	public FarmScenario() {
		super(null);
	}

	/**
	 * Returns a custom ArrayList of the objects to be in the scenario.
	 * @return An ArrayList of BaseObjects to be used in the scenario.
	 */
	@Override
	protected ArrayList<BaseObject> getBaseObjs() {
		ArrayList<BaseObject> objs = new ArrayList<>();
		objs.add(new House(null));
		return objs;
	}
}
