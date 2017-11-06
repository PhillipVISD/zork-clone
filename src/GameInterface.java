import Player.Player;
import Scenarios.BaseScenario;
import Text.TextInterpreter;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameInterface {

	POSTaggerME tagger = null;
	Tokenizer tokenizer = null;

	Map<String, Player> players = new HashMap<String, Player>();

	public GameInterface() {
		this.tokenizer = TextInterpreter.getTokenizer();
		this.tagger = TextInterpreter.getTagger();
	}

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
