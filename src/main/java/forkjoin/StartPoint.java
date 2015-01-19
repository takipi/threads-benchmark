package forkjoin;

import forkjoin.file.TestFile;
import forkjoin.prime.TestPrime;

public class StartPoint {
	public static void main(String[] args) throws Exception {
		int request = Integer.parseInt(args[0]);

		if (request == 0) {
			TestFile.createTestFile();
			return;
		}

		int runCase = Integer.parseInt(args[1]);

		if (request == 1) {
			long lineNum = Long.parseLong(args[2]);
			TestFile.testFile(runCase, lineNum);
			return;
		}

		if (request == 2) {
			TestPrime.testPrime(runCase, args[2], args[3]);
			return;
		}
	}
}
