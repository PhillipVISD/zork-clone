package Dynamic;

import Objects.BaseObject;
import Player.Player;

public class DynamicPL {
	public String verb;
	public BaseObject object;
	public Player player;
	public String response;

	public void setIn(String verb, Player player, BaseObject object) {
		this.verb = verb;
		this.player = player;
		this.object = object;
	}

	public void setOut(String response, Player player) {
		this.player = player;
		this.response = response;
	}
}
