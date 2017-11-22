package Text.Parsing;

import Text.TextInterpreter;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple class to load a POSTaggerME and a Tokenizer. Be
 */
public class NlpUtil {
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
}
