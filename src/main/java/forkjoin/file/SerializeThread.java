package forkjoin.file;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import forkjoin.Utils;

public class SerializeThread {
	static Map<String, List<WordIndex>> result = new HashMap<String, List<WordIndex>>();

	public static void main(String[] args) {
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void process() throws Exception {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(Utils.fileLocation);
			sc = new Scanner(inputStream, "UTF-8");
			int line = 0;
			String s = "";
			String[] words = null;
			while (sc.hasNextLine()) {
				s = sc.nextLine();
				words = s.trim().split("\\s+");
				for (int i = 0; i < words.length; i++) {
					if (!Utils.isEmptyString(words[i])) {
						//if (result.get(words[i]) == null) {
						//	result.put(words[i], new LinkedList<WordIndex>());
						//}
						//result.get(words[i]).add(new WordIndex(line, i));
					}
				}			
				line++;
			}
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
	}

}
