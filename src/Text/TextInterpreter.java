package Text;

import Dynamic.DynamicPL;
import Objects.BaseJSONObject;
import Objects.BaseObject;
import Player.Player;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextInterpreter {
	public Player player;
	public Tokenizer tokenizer = null;
	public POSTaggerME tagger = null;

	/**
	 * The constructor for the TextInterpreter.
	 * @param player The player object that is used.
	 * @param initVars Whether or not it should initialize the Tokenizer and POSTagger.
	 * @throws IOException An exception that can occur when the Tokenizer or Tagger are not found.
	 */
	public TextInterpreter(Player player, Boolean initVars) throws IOException {
		this.player = player;

		if (initVars) {
			System.out.print("Loading NLP file... ");

			this.tagger = this.getTagger();
			this.tokenizer = this.getTokenizer();
			System.out.println("Done");
		}
	}

	/**
	 * Returns a Tokenizer object after being loaded from the filesystem.
	 * @return The loaded Tokenizer object.
	 */
	public static Tokenizer getTokenizer() {
		Tokenizer tokenizer = null;

		try (InputStream modelIn = TextInterpreter.class.getResourceAsStream("en-token.bin")) {
			TokenizerModel tokenizerModel = new TokenizerModel(modelIn);
			tokenizer = new TokenizerME(tokenizerModel);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tokenizer;
	}

	/**
	 * Returns a POSTaggerME object after being loaded from the filesystem.
	 * @return The loaded POSTaggerME object.
	 */
	public static POSTaggerME getTagger() {
		POSTaggerME tagger = null;

		try (InputStream modelIn = TextInterpreter.class.getResourceAsStream("en-pos-perceptron.bin")) {
			POSModel model = new POSModel(modelIn);
			tagger = new POSTaggerME(model);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tagger;
	}

	/**
	 * Takes a string and interprets it in the game's context. This is used whn you don't wnat every TextInterpeter
	 * object to have an instance of a POSTaggerME and a Tokenizer.
	 * @param interpretStr The string to interpret.
	 * @param tokenizer The Tokenizer to be used.
	 * @param tagger The POSTaggerME to be used.
	 * @param player The Player object to be interpreted.
	 * @return A string 
	 * @throws IOException
	 */
	public static String interpret(String interpretStr, Tokenizer tokenizer, POSTaggerME tagger, Player player) throws IOException {
		TextInterpreter ti = new TextInterpreter(player, false);
		ti.tokenizer = tokenizer;
		ti.tagger = tagger;
		return ti.interpret(interpretStr);
	}

	/**
	 * Takes a string of input from the user, parses it, acts upon it, and returns a response as a String.
	 * @param interpretStr The string to be interpreted.
	 * @return The response from the command as a String.
	 */
	public String interpret(String interpretStr) {
		String[] words = this.tokenizer.tokenize(interpretStr.toLowerCase());
		String[] tags = this.tagger.tag(words);

		Map<String, String> posMap = new HashMap<String, String>();

		String verb = null;
		ArrayList<String> subjects = null;

		Boolean wasNoun = false;

		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String tag = tags[i];

			if (tag.toLowerCase().startsWith("vb")) {
//				System.out.println("VERB: " + word + " : " + tag);
				if (verb != null) {
					return "There is more than 1 verb in that command. Please only probide one.";
				} else {
					verb = word;
				}
				wasNoun = false;
			} else if (tag.toLowerCase().startsWith("nn")) {
				if (wasNoun) {
					subjects.set(subjects.size() - 1, subjects.get(subjects.size() - 1) + " " + word);
				}
				else if (subjects != null) {
					subjects.add(word);
				} else {
					subjects = new ArrayList<String>();
					subjects.add(word);
				}
				wasNoun = true;
//				System.out.println(word);
			}
			else {
				wasNoun = false;
			}

//			System.out.println(word + " : " + tag);

			posMap.put(word, tag);
		}

//		String verb = "";

//		posMap.get

//		String verb = words[0];

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

	/**
	 * Perform an action on a specific object.
	 * @param subjectObj The object to be have the action to be applied to.
	 * @param verb The action to be performed.
	 * @param player The player that may be modified by the action.
	 * @return The string response from the object.
	 */
	private String actionOnObj(BaseObject subjectObj, String verb, Player player) {
		verb = verb.toLowerCase();
		String response;

		BaseJSONObject jsonObj = (BaseJSONObject) subjectObj;
		DynamicPL pl = jsonObj.verbAction(verb, player);

		this.player = pl.player;

		return pl.response;
	}

	/**
	 * Returns a player
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
