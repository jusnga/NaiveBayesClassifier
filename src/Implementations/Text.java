package Implementations;


import java.util.HashMap;
import java.util.Map;

import Interfaces.TextI;
import Interfaces.VocabI;

/**
 * 
 * @author Justin
 *An extension of the Document class to represent a concatenation of documents that represents a category. 
 *
 *Adds some functionality to allow for the calculation of the probability of a word given this 'category'
 */
public class Text extends Document implements TextI {
	private int num_docs;
	private Map<String, Double> pwv_map;
	
	public Text(VocabI vocab) {
		super(vocab);
		this.num_docs = 0;
		this.pwv_map = new HashMap<String, Double>();
	}
	@Override
	public void read_file(String file, boolean learn) {
		super.read_file(file, true);
		num_docs++;
	}

	/**
	 * Formula to calculate the probability of a word given this class (formula as according to Mitchell)
	 */
	@Override
	public double pwv(String word) {
		if (this.pwv_map.containsKey(word)) {
			return pwv_map.get(word);
		}
		double num_occurences_of_word = 0;
		double num_distinct_words = words_in_document.keySet().size();
		if (words_in_document.containsKey(word)) {
			num_occurences_of_word = words_in_document.get(word);
		}
		double vocab_size = vocab.num_words();
		double pwv = (num_occurences_of_word + 1)/(num_distinct_words + vocab_size);
		this.pwv_map.put(word, pwv);
		return pwv;
	}

	@Override
	public int num_docs() {
		return num_docs;
	}


}
