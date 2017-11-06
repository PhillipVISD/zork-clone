package Dynamic;

import Player.Player;

public class DynamicPL {
	public String verb;
	public Player player;
	public String response;

	public void setIn(String verb, Player player) {
		this.verb = verb;
		this.player = player;
	}

	public void setOut(String response, Player player) {
		this.player = player;
		this.response = response;
	}
}
