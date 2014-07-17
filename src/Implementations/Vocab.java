package Implementations;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import Interfaces.VocabI;
/**
 * 
 * @author Justin
 *A class to represent the vocabulary of the NaiveBayes classifier, represented as a bag of words by storing words in a HashMap
 */
public class Vocab implements VocabI {
	private Map<String, Integer> vocab;
	private int num_words;
	
	public Vocab() {
		this.vocab = new HashMap<String, Integer>();
		this.num_words = 0;
	}
	
	@Override
	public void add_word(String word) {
		num_words++;
		if (vocab.containsKey(word)) {
			vocab.put(word, vocab.get(word) + 1);
		} else {
			vocab.put(word, 1);
		}
	}

	@Override
	public int num_words() {
		return num_words;
	}

	/**
	 * Returns the set of distinct words in the vocabulary
	 */
	@Override
	public Set<String> distinct_words() {
		return vocab.keySet();
	}

	@Override
	public void printToFile() {
		Writer writer = null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("vocab" + "dictionary" + ".txt"), "utf-8"));
		    writer.write("Number of words: " + num_words + '\n');
		    writer.write("Number of distinct words: " + vocab.keySet().size() + '\n');
		    for (Entry<String, Integer> entryset : vocab.entrySet()) {
		    	writer.write(entryset.getKey() + " = " + entryset.getValue() + '\n');
		    }
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
	}
	
	/**
	 * Refines the vocabulary by removing statistically irrelevant words(i.e. words that occur less than 3 times) and common words such as
	 * 'the, 'and' and etc by removing words that occur more than 100 times
	 */
	@Override
	public void refine() {
		Iterator<Map.Entry<String, Integer>> iter = vocab.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Integer> entry = iter.next();
			if (entry.getValue() < 3 || entry.getValue() > 100) {
				iter.remove();
			}
		}
	}

}
