package forkjoin.prime.example;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SequentialStreamPrimes {

	public static boolean isPrime(int prime) {
		Set<Integer> collect = IntStream
				.rangeClosed(2, (int) (Math.sqrt(prime))).parallel()
				.filter(j -> prime % j == 0).mapToObj(j -> Integer.valueOf(j))
				.collect(Collectors.toSet());
		System.out.println(collect.size());
		return false;
	}

	public static Set<Integer> findPrimes(int maxPrimeTry) {
		return IntStream
				.rangeClosed(2, maxPrimeTry)
				.map(i -> IntStream.rangeClosed(2, (int) (Math.sqrt(i)))
						.filter(j -> i % j == 0).map(j -> 0).findAny()
						.orElse(i)).filter(i -> i != 0)
				.mapToObj(i -> Integer.valueOf(i)).collect(Collectors.toSet());
	}

	public static void main(String args[]) {
		long startTime = System.currentTimeMillis();
		isPrime(3000);

		long timeTaken = System.currentTimeMillis() - startTime;
		System.out.println("Time taken: " + timeTaken);
	}
}
