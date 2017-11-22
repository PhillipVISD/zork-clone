package Text.Parsing;

import Objects.BaseObject;

import java.util.ArrayList;

public class LangParser {
	public static String getResponse(Player player, String verb, ArrayList<String> subjects,) {
		StringBuilder endResponse = new StringBuilder();
		Boolean responded = false;

		BaseObject scope = this.getPlayer().getScope();

		if (verb != null && (verb.equals("exit") || verb.equals("leave"))) {
			endResponse.append(this.player.getScope().exit(player));
			responded = true;
		}

		if (verb != null && subjects != null && !responded) {
			for (String subject : subjects) {
				String response = null;
				BaseObject subjectObj = scope.getObjectByName(subject);

				if (subjectObj != null) {
					response = this.actionOnObj(subjectObj, verb.trim(), this.player);
//					System.out.println(this.player.describeInventory());
//					response = subjectObj.action(verb.trim(), this.player);
				} else {
					response = "There is nothing called \"" + subject + "\" in the scene.";
				}

				if (subjects.size() > 1) {
					String capitalizedSubject = subject.substring(0, 1).toUpperCase() + subject.substring(1).toLowerCase();
					response = capitalizedSubject + ": " + response;
				}

				endResponse.append(response).append("\n");
			}
		} else if (verb == null) {
			if (subjects.size() == 1 && subjects.get(0).equals("inventory")) {
				endResponse.append(this.player.describeInventory());
			}
			else {
				endResponse.append("There is no verb in this action.");
			}
		} else if (!responded) {
			String response = this.player.getScope().action(verb);
			if (response != null) {
				endResponse.append(response);
			} else {
				endResponse.append("There are no subjects in this action.");
			}
		}


		return endResponse.toString();
	}
}
