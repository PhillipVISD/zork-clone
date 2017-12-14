package org.vashonsd.zork.Dynamic;

import org.vashonsd.zork.Objects.BaseObject;
import org.vashonsd.zork.Player.Player;

public class DynamicPL {
	public String verb;
	public BaseObject object;
	public Player player;
	public String response;

	/**
	 * Helper method to quickly define the variables in the payload before it is processed.
	 * @param verb The verb being used.
	 * @param player The player object being used.
	 * @param object The BaseObject being used.
	 */
	public void setIn(String verb, Player player, BaseObject object) {
		this.verb = verb;
		this.player = player;
		this.object = object;
	}

	/**
	 * Helper method to quickly define the variables in the payload after being processed.
	 * @param response The response string to be shown to the user.
	 * @param player The player object after being manipulated.
	 */
	public void setOut(String response, Player player) {
		this.player = player;
		this.response = response;
	}
}
