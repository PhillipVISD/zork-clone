package Text.Parsing.NLP;

import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;

/**
 * A class that can be passed to other methods to give them necessary object like a Tokenizer and a POSTaggerME.
 */
public class Context {

	public Tokenizer tokenizer = null;
	public POSTaggerME tagger =  null;

	public Context() {
		this.tokenizer = NlpUtil.getTokenizer();
		this.tagger = NlpUtil.getTagger();
	}
}
