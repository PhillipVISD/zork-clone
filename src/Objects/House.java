package Objects;

import java.util.ArrayList;

public class House extends BaseObject {
	/**
	 * An example object implemented into the games src.
	 * @param objs What object the house should have like a chair or table.
	 */
	public House(ArrayList<BaseObject> objs) {
		super("house", "a", false, true, objs);
		this.preDesc = "large red";
//		this.postDesc = "";
		this.canContain = true;
	}
}
