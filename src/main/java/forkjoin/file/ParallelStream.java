package forkjoin.file;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

import forkjoin.Utils;

public class ParallelStream {
	long countLineNumber;

	public ParallelStream(long countLineNumber) {
		this.countLineNumber = countLineNumber;
	}

	public void process() {

		LongStream.range(0, countLineNumber).parallel()
				.filter(i -> i % Utils.numberLinePerThread == 0)
				.mapToObj(from -> {
					long to = from + Utils.numberLinePerThread - 1;
					Map<String, List<WordIndex>> result = null;
					try {
						Utils.processPart(from, to);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return result;

				}).count();

	}

	public static void main(String args[]) throws InterruptedException {
		long countLineNumber = Utils.countLineNumber(Utils.fileLocation);
		ParallelStream mc = new ParallelStream(countLineNumber);
		System.out.println("line number " + countLineNumber);
		for (int i = 0; i < 5; i++) {
			long t1 = Calendar.getInstance().getTimeInMillis();
			mc.process();
			long t2 = Calendar.getInstance().getTimeInMillis();
			System.out.println(String.format("Time = %s ms ", (t2 - t1)));
			Thread.sleep(5000);
		}
	}
}
