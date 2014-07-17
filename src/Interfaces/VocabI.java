package Interfaces;

import java.util.Set;

public interface VocabI {
	public void add_word(String word);
	public int num_words();
	public Set<String> distinct_words();
	public void refine();
	public void printToFile();
}
