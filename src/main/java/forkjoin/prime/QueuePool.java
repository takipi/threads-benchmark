package forkjoin.prime;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public class QueuePool implements Callable<Boolean> {

	private BigInteger from;
	private BigInteger to;
	private BigInteger primeNumber;

	public QueuePool(BigInteger primeNumber, BigInteger from, BigInteger to) {
		this.primeNumber = primeNumber;
		this.from = from;
		this.to = to;
	}

	@Override
	public Boolean call() {
		boolean isPrime = true;
		for (BigInteger i = from; i.compareTo(to) <= 0; i = i
				.add(BigInteger.ONE)) {
			if (this.primeNumber.mod(i).equals(BigInteger.ZERO)) {
				isPrime = false;
			}
		}
		return isPrime;
	}
}
