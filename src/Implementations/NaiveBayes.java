package Implementations;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import Interfaces.DocumentI;
import Interfaces.TextI;
import Interfaces.VocabI;
/**
 * 
 * @author Justin
 *Basic NaiveBayes classifier which learns given a directory of subdirectories(which represent the classes/categories, i.e. the folder comp.graphics
 *will be used as a category)
 */
public class NaiveBayes {
	private int total_docs;
	private VocabI vocab;
	private Map<String, TextI> categories;
	private String directory;
	
	public NaiveBayes() {
		this.total_docs = 0;
		categories = new HashMap<String, TextI>();
	}
	
	/**
	 * 
	 * @param dir is 20_newsgroups directory
	 * @param vocab is the vocabulary of the naive bayes classifier
	 * Currently hard coded to accept 20_newsgroups folder where each sub-folder is treated as a category
	 */
	public void learn(String dir, VocabI vocab) {
		this.directory = dir;
		this.vocab = vocab;
		
		File folder = new File(dir);
		if (!folder.isDirectory()) {
			System.out.println("Please provide directory");
			return;
		}
		File[] subdirectories = folder.listFiles();
		for (File subdirectory : subdirectories) {
			TextI category_text = new Text(vocab);
			if (subdirectory.isDirectory()) {
				File[] files = subdirectory.listFiles();
				for (File file : files) {
					category_text.read_file(file.toString(), true);
					total_docs++;
				}
			}
			categories.put(subdirectory.toString(), category_text);
		}
		vocab.refine();
	}
	
	/**
	 * 
	 * @param file is the file to be classified
	 * @return returns true if correctly classified otherwise false
	 */
	public boolean classify(String file) {
		String category = "";
		double category_prob = 0;
		
		//Read document;
		DocumentI document = new Document(vocab);
		document.read_file(file, false);
		//Mapping for document
		Map<String, Integer> document_dictionary = document.get_words();
		//Distinct words in vocabulary
		Set<String> vocab_distinct_words = vocab.distinct_words();
		
		for (Entry<String, TextI> entry : categories.entrySet()) {
			double pv = (double)entry.getValue().num_docs()/(double)total_docs;
			double pwv = 1;
			double prevpwv = 1;
			for (String word : document_dictionary.keySet()) {
				if (vocab_distinct_words.contains(word)) {
					if ((pwv *= entry.getValue().pwv(word)) == 0) {
						pwv = prevpwv;
						break;
					}
					prevpwv = pwv;
				}
			}
			if (pwv*pv > category_prob) {
				category = entry.getKey();
				category_prob = pwv*pv;
			}
		}
		String predicted_category = category.replace(this.directory + "\\", "");
		String actual_category = document.getCategory();
		if (actual_category.contains(predicted_category)) {
			return true;
		} else {
			return false;
		}
	}
	
}
