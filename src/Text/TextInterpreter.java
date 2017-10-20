package Text;

import Objects.BaseObject;
import Player.Player;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextInterpreter {
	private Player player;
	private Tokenizer tokenizer = null;
	private POSTaggerME tagger = null;

	public TextInterpreter(Player player) throws IOException {
		this.player = player;

		System.out.print("Loading NLP file... ");

		POSTaggerME tagger = null;
		Tokenizer tokenizer = null;

		try (InputStream modelIn = new FileInputStream("en-token.bin")) {
			TokenizerModel tokenizerModel = new TokenizerModel(modelIn);
			tokenizer = new TokenizerME(tokenizerModel);
		}

		try (InputStream modelIn = new FileInputStream("en-pos-maxent.bin")) {
			POSModel model = new POSModel(modelIn);
			tagger = new POSTaggerME(model);
			System.out.println("Done");
		}

		this.tagger = tagger;
		this.tokenizer = tokenizer;
	}

	public String interpret(String interpretStr) {
		String[] words = this.tokenizer.tokenize(interpretStr.toLowerCase());
		String[] tags = this.tagger.tag(words);

		Map<String, String> posMap = new HashMap<String, String>();

		String verb = null;
		ArrayList<String> subjects = null;

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
			} else if (tag.toLowerCase().startsWith("nn")) {
				if (subjects != null) {
					subjects.add(word);
				} else {
					subjects = new ArrayList<String>();
					subjects.add(word);
				}
//				System.out.println(word);
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
			endResponse.append("There is no verb in this action.");
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

	private String actionOnObj(BaseObject subjectObj, String verb, Player player) {
		verb = verb.toLowerCase();
		String response;

		if (verb.equals("pick")) {
			response = subjectObj.pickup(player);
		} else if (verb.equals("enter")) {
			response = subjectObj.enter(player);
		}
//		else if (verb.equals("exit") || verb.equals("leave")) {
//			response = subjectObj.exit(player);
//		}
		else {
			response = subjectObj.illegal();
		}
		return response;
	}

	private String[] removeFromStringFromArray(String[] arr, String remove) {
		List<String> result = new ArrayList<>();
		for (String str : arr) {
			if (!str.equals(remove)) {
				result.add(str);
			}
		}
		return result.toArray(new String[0]);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
