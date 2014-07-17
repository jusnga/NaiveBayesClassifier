package Interfaces;

import java.util.Map;

public interface DocumentI {
	public void read_file(String file, boolean learn);
	public Map<String, Integer> get_words();
	public String getCategory();
}
