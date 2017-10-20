import Player.Player;
import Scenarios.BaseScenario;
import Text.TextInterpreter;
import Text.TextParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;

public class Game {

	public static void main(String[] args) throws IOException, ParseException, org.apache.commons.cli.ParseException, ClassNotFoundException {

		Options options = new Options();
		options.addOption("json", true, "pass a valid JSON file to run your own story.");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse( options, args);

		String jsonLoc = cmd.getOptionValue("json");

		BaseScenario scenario;

		if (jsonLoc != null) {
			JSONParser jsonParser = new JSONParser();
			InputStream jsonIS = Game.class.getResourceAsStream(jsonLoc);

			Object obj = jsonParser.parse(convertStreamToString(jsonIS));
			JSONObject jsonObj = (JSONObject) obj;

			scenario = (BaseScenario) BaseScenario.fromJson(jsonObj);
		}
		else {
			scenario = BaseScenario.getRandomScenario();
		}

		TextInterpreter ti = new TextInterpreter(new Player( scenario, "Joe"));

		System.out.println("Welcome to Zork Clone\n");

		TextParser tp = new TextParser();

		System.out.println("Type 'exit' to exit the game.");

		System.out.println("\n" + ti.getPlayer().scenario.describe() + "\n");

		while (true) {
			String input = tp.read("> ").trim();
			if (input.equals("exit")) {
				break;
			}
			else {
				String response = ti.interpret(input);
				if (response != null) {
					System.out.println(response);
				}
			}
		}
	}

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
