package Scenarios;

import Objects.BaseObject;
import Objects.House;

import java.util.ArrayList;

public class FarmScenario extends BaseScenario {
	public FarmScenario() {
		super(null);
	}

	@Override
	protected ArrayList<BaseObject> getBaseObjs() {
		ArrayList<BaseObject> objs = new ArrayList<>();
		objs.add(new House(null));
		return objs;
	}
}
