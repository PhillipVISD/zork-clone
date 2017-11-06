package Text;

import Objects.BaseObject;

import java.util.List;

public class EnglishFactory {
	public static String fromGameObjs(List<BaseObject> objects) {

		if (objects.size() <= 0) {
			return "There is nothing around.";
		}

		StringBuilder englishStr = new StringBuilder("There ");
		String item;

		if (objects.get(0).isPlural) {
			englishStr.append("are ");
		}
		else {
			englishStr.append("is ");
		}

		for (final BaseObject object : objects) {
			item = object.getDescription();

			Boolean lastItem = objects.indexOf(object) == (objects.size() -1);

			if (objects.size() == 1) {
				englishStr.append(item);
			}
			else if (lastItem) {
				englishStr.append("and ").append(item);
			}
			else if (objects.size() > 2) {
				englishStr.append(item).append(", ");
			}
			else {
				englishStr.append(item).append(" ");
			}
		}
		englishStr.append(".");
		return englishStr.toString();
	}
}
