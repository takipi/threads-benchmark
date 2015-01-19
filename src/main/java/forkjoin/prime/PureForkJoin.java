package forkjoin.prime;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

import forkjoin.Utils;

public class PureForkJoin extends RecursiveTask<Boolean> {
	private static final long serialVersionUID = -1883115459616562727L;
	
	private BigInteger from;
	private BigInteger to;
	boolean isPrime = true;

	public PureForkJoin(BigInteger start, BigInteger to) {
		this.from = start;
		this.to = to;
	}

	private boolean computeDirectly() {
		for (BigInteger i = this.from; i.compareTo(this.to) <= 0; i = i
				.add(BigInteger.ONE)) {
			if (TestPrime.primeNumber.mod(i).equals(BigInteger.ZERO)) {
				isPrime = false;
			}
		}
		return isPrime;
	}

	@Override
	protected Boolean compute() {
		BigInteger tmp = to.subtract(from);
		if (tmp.compareTo(TestPrime.lengthForThread) <= 0) {
			return computeDirectly();
		}
		
		BigInteger middle = to.add(from).divide(Utils.two);
		PureForkJoin leftJoin = new PureForkJoin(this.from, middle);
		PureForkJoin rightJoin = new PureForkJoin(middle.add(BigInteger.ONE),
				this.to);
		
		invokeAll(leftJoin, rightJoin);
		
		return leftJoin.isPrime && rightJoin.isPrime;
	}
}