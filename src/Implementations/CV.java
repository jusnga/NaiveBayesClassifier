package Implementations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CV {

	private String dir;
	
	public CV(String dir) {
		this.dir = dir;
	}
	
	public Map<String, List<File>> get_split_data(double percent) {
		Map<String, List<File>> split_data = new HashMap<String, List<File>>();
		List<File> testing_set = new ArrayList<File>();
		
		File directory = new File(dir);
		File[] subdirectories = directory.listFiles();
		
		for (File subdirectory : subdirectories) {
			List<File> training_set = new ArrayList<File>();
			File[] files = subdirectory.listFiles();
			int subdirectory_size = files.length;
			int training_size = (int)(subdirectory_size * percent);
			for (File file : files) {
				if (training_size != 0) {
					training_set.add(file);
					training_size--;
				} else {
					testing_set.add(file);
				}
			}
			split_data.put(subdirectory.toString(), training_set);
		}
		split_data.put("Testing", testing_set);
		
		return split_data;
	}
}
