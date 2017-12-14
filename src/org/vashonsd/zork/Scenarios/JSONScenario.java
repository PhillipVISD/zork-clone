package org.vashonsd.zork.Scenarios;

import org.vashonsd.zork.Objects.BaseObject;

import java.util.ArrayList;

public class JSONScenario extends BaseScenario {
	/**
	 * The constructor for the BaseJSONScenario
	 * @param objs What objs the scenario should be initialized with.
	 */
	public JSONScenario(ArrayList<BaseObject> objs) {
		super(objs);
	}

	/**
	 * Gets the BaseObjects to be used in the scenario.
	 * @return This.objs is returned, which is of type ArrayList<BaseObject>
	 */
	@Override
	protected ArrayList<BaseObject> getBaseObjs() {
		return this.objs;
	}
}
