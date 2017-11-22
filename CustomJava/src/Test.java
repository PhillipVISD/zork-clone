import Dynamic.DynamicPL;
import Player.Player;
import Objects.BaseObject;
import Text.TextInterpreter;

import java.util.Random;


public class Test {
	public static void main(String[] args) {
		System.out.println("Hello");
	}

	public static DynamicPL hello(DynamicPL pl) {
		String word = pl.verb;
		pl.setOut("Hello " + word, pl.player);
		return pl;
	}

	public static DynamicPL grab(DynamicPL pl) {
		String word = pl.verb;
		BaseObject object = pl.object;

		String response = "You grab the " + object.getName() + " and feel it loom back and" +
				"forth in your hands. It is now yours.";
		Player player = pl.player;

		player.addToInventory(object);

		pl.setOut(response, player);

		return pl;
	}

	public static DynamicPL enter(DynamicPL pl) {
		BaseObject object = pl.object;

		if (object.getName().equals("window")) {
			Player player = pl.player;
			player.scope = object;
			pl.setOut("You budge the window open enough to stumble inside.", player);
			return pl;
		}
		else if (object.getName().equals("house")) {
			Player player = pl.player;
			player.scope = object;
			pl.setOut("You hear birds chirping on your way to the house.", player);
			return pl;
		}
		else {
			pl.setOut("Invalid object: " + object.getName(), pl.player);
			return pl;
		}
	}

	public static String interpret(String str, TextInterpreter ti, Player player) {
		Random random = new Random();

		int randInt = random.nextInt(10 - 1 + 1) + 1;

		if (Integer.toString(randInt).equals(str)) {
			return "You win!";
		}
		else {
			return "Nope, it was " + Integer.toString(randInt);
		}
	}

	public static DynamicPL play(DynamicPL pl) {
		BaseObject object = pl.object;
		Player player = pl.player;
		player.scope = object;
		pl.setOut("You hear whirrrrr as the old machine comes to life.", player);
		return pl;
	}
}