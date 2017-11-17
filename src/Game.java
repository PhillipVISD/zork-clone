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

	/**
	 * This is the main function it load all other required classes and get into an interactive game of Zork.
	 * @param args Command-line arguments, -json can be passed to use a JSON file as the story source.
	 * @throws IOException Exception when reading IO input.
	 * @throws ParseException Exception while parsing JSON.
	 * @throws org.apache.commons.cli.ParseException Exception when parsing CLI arguments.
	 * @throws ClassNotFoundException Exception while looking for custom behaviour class.
	 */

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

		TextInterpreter ti = new TextInterpreter(new Player( scenario, "Joe"), true);

		System.out.println("Welcome to Zork Clone\n");

		TextParser tp = new TextParser();

		System.out.println("Type 'exit' to exit the game.");

		System.out.println("\n" + ti.getPlayer().scenario.describe() + "\n");

//		PythonInterpreter interpreter = new PythonInterpreter();
		// ^ Only works because of Lib in C:\Users\cutter.phillip\.m2\repository\org\python\jython\2.7.0

		while (true) {
			String input = tp.read("> ").trim();
			if (input.equals("exit")) {
				break;
			}
			else {
//				PyObject resp = interpreter.eval(input);
//				String pyResp = (String) resp.__str__().__tojava__(String.class);
//				System.out.println(pyResp);
				String response = ti.interpret(input);
				if (response != null) {
					System.out.println(response);
				}
			}
		}
	}


	/**
	 * Converts a InputStream object to a string.
	 * @param is The input stream to be converted.
	 * @return The String version of the InputStream.
	 */
	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
