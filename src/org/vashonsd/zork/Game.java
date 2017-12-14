package org.vashonsd.zork;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.json.simple.parser.ParseException;
import org.vashonsd.zork.Player.Player;
import org.vashonsd.zork.Scenarios.BaseScenario;
import org.vashonsd.zork.Text.Parsing.TextParser;
import org.vashonsd.zork.Text.TextInterpreter;
import org.vashonsd.zork.Util.JSON;

import java.io.IOException;

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

		BaseScenario scenario = JSON.scenarioFromJsonName(jsonLoc);

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
}
