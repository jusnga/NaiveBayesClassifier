import java.io.File;

import Implementations.NaiveBayes;
import Implementations.Vocab;
import Interfaces.VocabI;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String error_message = "Invalid structure, please refer to README";
		
		if (args.length != 2) {
			System.out.println("Please provide 2 arguements, First: Directory to be learned, Second: File/Directory to be classified");
		} else {
			File training_data = new File(args[0]);
			File testing_data = new File(args[1]);
			
			if (training_data.isDirectory()) {
				File[] subdirectories = training_data.listFiles();
				if (subdirectories.length == 0) {
					System.out.println(error_message);
					return;
				}
				for (File subdirectory : subdirectories) {
					if (subdirectory.isDirectory()) {
						File[] files = subdirectory.listFiles();
						if (files.length == 0) {
							System.out.println(error_message);
							return;
						}
						for (File file : files) {
							if (!file.isFile()) {
								System.out.println(error_message);
								return;
							}
						}
					} else {
						System.out.println(error_message);
						return;
					}
				}
			} else {
				System.out.println(error_message);
				return;
			}
			
			NaiveBayes classifier = new NaiveBayes();
			int correct = 0;
			int wrong = 0;
			VocabI vocab = new Vocab();
			
			classifier.learn(training_data.toString(), vocab);
			if (testing_data.isDirectory()) {
				for (File file : testing_data.listFiles()) {
					if (classifier.classify(file.toString())) {
						correct++;
					} else {
						wrong++;
					}
				}
			} else {
				classifier.classify(testing_data.toString());
			}
	
			System.out.println("Correctly classified: " + correct);
			System.out.println("Wrongly classified: " + wrong);
		}
	}

}
