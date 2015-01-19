package forkjoin.file;

import java.util.Calendar;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import forkjoin.Utils;

public class PureForkJoin<V> extends RecursiveTask<V> {
	private static final long serialVersionUID = 7926312633273843668L;

	private long from;
	private long to;

	public PureForkJoin(long from, long to) {
		this.from = from;
		this.to = to;
	}

	@Override
	protected V compute() {
		try {
			if (to - from <= Utils.numberLinePerThread) {
				Utils.processPart(from, to);
				return null;
			}
			long middle = (to + from) / 2;

			PureForkJoin<Object> leftJoin = new PureForkJoin<Object>(from,
					middle);
			PureForkJoin<Object> rightJoin = new PureForkJoin<Object>(
					middle + 1L, to);

			invokeAll(leftJoin, rightJoin);

		} catch (Exception e) {

		}
		return null;
	}

	public static void main(String[] args) {
		long countLineNumber = Utils.countLineNumber(Utils.fileLocation);

		for (int i = 0; i < 5; i++) {
			PureForkJoin<Object> fb = new PureForkJoin<Object>(0L,
					countLineNumber - 1);
			ForkJoinPool pool = new ForkJoinPool();

			long t1 = Calendar.getInstance().getTimeInMillis();
			pool.invoke(fb);
			long t2 = Calendar.getInstance().getTimeInMillis();
			
			System.out.println(String.format("Time = %s ms ", (t2 - t1)));
		}
	}
}