package forkjoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import forkjoin.file.WordIndex;
import forkjoin.prime.TestPrime;

public class Utils {
	public static BigInteger two = new BigInteger("2");
	public static BigInteger three = new BigInteger("3");

	public static String fileLocation = "/tmp/test.txt";
	public static long numberLinePerThread = 10000;
	public static long lineNum = 1838200;

	public static BigInteger sqrt(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8"))
				.toString());
		while (b.compareTo(a) >= 0) {
			BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			if (mid.multiply(mid).compareTo(n) > 0)
				b = mid.subtract(BigInteger.ONE);
			else
				a = mid.add(BigInteger.ONE);
		}
		return a.subtract(BigInteger.ONE);
	}

	public static boolean isEmptyString(String s) {
		return s == null || "".equals(s.trim());
	}

	public static long countLineNumber(String fileLocation) {
		long lines = 0;
		try {
			File file = new File(fileLocation);
			LineNumberReader lineNumberReader = new LineNumberReader(
					new FileReader(file));
			lineNumberReader.skip(Long.MAX_VALUE);
			lines = lineNumberReader.getLineNumber();
			lineNumberReader.close();
		} catch (FileNotFoundException e) {
			System.out
					.println("FileNotFoundException Occured" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException Occured" + e.getMessage());
		}
		return lines;
	}

	public static Map<String, List<WordIndex>> processPart(long from, long to)
			throws Exception {
		Map<String, List<WordIndex>> result = new HashMap<String, List<WordIndex>>();
		InputStream is = new FileInputStream(Utils.fileLocation);
		is.skip(from * 1024);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String s = "";
		long line = from;
		while ((s = reader.readLine()) != null && line <= to) {
			String[] words = s.trim().split("\\s+");
			for (int i = 0; i < words.length; i++) {
			}
			line++;
		}
		is.close();
		return result;
	}

	public static Boolean primeProcessPart(BigInteger from) {
		boolean isPrime = true;
		BigInteger to = from.add(TestPrime.lengthForThread);
		for (BigInteger i = from; i.compareTo(to) <= 0; i = i
				.add(BigInteger.ONE)) {
			if (TestPrime.primeNumber.mod(i).equals(BigInteger.ZERO)) {
				isPrime = false;
			}
		}
		return isPrime;
	}

	public static void main(String[] args) {
		BigInteger x = new BigInteger("5");
		BigInteger primeNumber = new BigInteger("10");
		primeNumber = primeNumber.add(x);

		System.out.println(primeNumber);

		BigInteger probablePrime = BigInteger.probablePrime(61, new Random());
		System.out.println(probablePrime);
	}
}
