package org.vashonsd.zork;

import org.vashonsd.zork.Player.Player;
import org.vashonsd.zork.Scenarios.BaseScenario;
import org.vashonsd.zork.Text.TextInterpreter;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameInterface {

	POSTaggerME tagger = null;
	Tokenizer tokenizer = null;

	Map<String, Player> players = new HashMap<String, Player>();

	/**
	 * Initialized the Game Interface with a tokenizer and tagger for text recognition.
	 */
	public GameInterface() {
		this.tokenizer = TextInterpreter.getTokenizer();
		this.tagger = TextInterpreter.getTagger();
	}

	/**
	 * Takes some information about the context and returns a response string. This is similar to the command line
	 * version but allows it to be interacted with from another source like a web server.
	 * @param command The command the user sent.
	 * @param user A unique user id.
	 * @param username The username of the person.
	 * @param context An unused variable that may be used to save and load state from an encoded string.
	 * @return
	 * @throws IOException
	 */
	public String parseCommand(String command, String user, String username, String context) throws IOException {
		Player player;

		if (!this.players.containsKey(user)) {
			BaseScenario scenario = BaseScenario.getRandomScenario();
			player = new Player(scenario, username);
			this.players.put(user, player);
		}
		else {
			player = this.players.get(user);
		}
		String response = TextInterpreter.interpret(command, this.tokenizer, this.tagger, player);
		return response;
	}
}
