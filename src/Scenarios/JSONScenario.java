package Scenarios;

import Objects.BaseObject;

import java.util.ArrayList;

public class JSONScenario extends BaseScenario {
	public JSONScenario(ArrayList<BaseObject> objs) {
		super(objs);
	}

	@Override
	protected ArrayList<BaseObject> getBaseObjs() {
		return this.objs;
	}
}
