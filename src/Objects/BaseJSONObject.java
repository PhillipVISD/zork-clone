package Objects;

import java.util.ArrayList;

public class BaseJSONObject extends BaseObject {
	public BaseJSONObject(String name, String determiner, Boolean animate, Boolean canContain,
	                      ArrayList<BaseObject> objs, String preDesc, String postDesc) {
		super("house", "a", false, true, objs);
		this.preDesc = preDesc;
		this.postDesc = postDesc;
		this.canContain = canContain;
	}
}
