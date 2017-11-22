package Text.Parsing;

import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NlpParser {

	public boolean error = false;
	public String errorMessage = null;
	public String verb = null;
	public ArrayList<String> subjects = null;

	public NlpParser(String interpretStr, Tokenizer tokenizer, POSTaggerME tagger) {
		String[] words = tokenizer.tokenize(interpretStr.toLowerCase());
		String[] tags = tagger.tag(words);

		Map<String, String> posMap = new HashMap<String, String>();

		String verb = null;
		ArrayList<String> subjects = null;

		Boolean wasNoun = false;

		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String tag = tags[i];

			if (tag.toLowerCase().startsWith("vb")) {
				if (verb != null) {
					this.error = true;
					this.errorMessage = "There is more than 1 verb in that command. Please only provide one.";
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
			}
			else {
				wasNoun = false;
			}

			posMap.put(word, tag);
		}

		this.verb = verb;
		this.subjects = subjects;
	}
}
