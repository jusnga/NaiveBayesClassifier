package Implementations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Interfaces.DocumentI;
import Interfaces.VocabI;

/**
 * 
 * @author Justin
 *A document is represented as a word of bags where each word is stored in a HashMap and the number of occurences tracked
 *
 *The document class is the base class where documents can be combined to form a concatenation of documents.
 */
public class Document implements DocumentI {

	protected Map<String, Integer> words_in_document;
	protected VocabI vocab;
	private BufferedReader br;
	protected int num_words;
	private String category;

	/**
	 * Reads a given file and creates a word bag by adding each word to a Map and keeping count of each occurrence. 
	 * If learn is true will add the word to the vocabulary of the classifier.
	 */
	@Override
	public void read_file(String file, boolean learn) {
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			boolean finished = false;
			while ((line = br.readLine()) != null) {
				if (finished) {
					//Removes non word characters and replaces them with empty spaces.
					String[] text = line.toLowerCase().replaceAll("[^a-zA-Z]"," ").split(" ");
					for (String word : text) {
						if (word.matches("") || word.length() <= 2) {
							continue;
						} else {
							num_words++;
							//Add to dictionary
							if (words_in_document.containsKey(word)) {
								words_in_document.put(word, words_in_document.get(word) + 1);
							} else {
								words_in_document.put(word, 1);
							}
							//If learning, add to vocabulary
							if (learn) {
								vocab.add_word(word);
							}
						}
					}
				}
				//Starts reading the file after the first empty line is reached (Done to ignore the header of emails)
				if (line.startsWith("")) {
					finished = true;
				}
				//Stores the category of the file
				if (line.startsWith("Newsgroups: ")) {
					String[] line_content = line.split(" ");
					this.category = line_content[1];
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the word bag representation of the document
	 */
	@Override
	public Map<String, Integer> get_words() {
		return words_in_document;
	}
	
	/**
	 * Returns the category of the document
	 */
	@Override
	public String getCategory() {
		return category;
	}
	
	public Document(VocabI vocab) {
		this.vocab = vocab;
		this.words_in_document = new HashMap<String, Integer>();
		this.num_words = 0;
	}

	
}
