package Objects;

import java.util.ArrayList;

public class House extends BaseObject {
	public House(ArrayList<BaseObject> objs) {
		super("house", "a", false, true, objs);
		this.preDesc = "large red";
//		this.postDesc = "";
		this.canContain = true;
	}
}
