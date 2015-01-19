package forkjoin.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import forkjoin.Utils;

public class QueuePool {
	private File file;
	private int chunkSize;
	private long fileLength;

	public QueuePool(File file, int chunkSize) {
		this.file = file;
		this.chunkSize = chunkSize;
		this.fileLength = file.length();
		System.out.println("File length = " + this.fileLength);
	}

	public Callable<Map<String, List<WordIndex>>> processPartTask(
			final long start, final long end) {
		return new Callable<Map<String, List<WordIndex>>>() {
			public Map<String, List<WordIndex>> call() throws Exception {
				return Utils.processPart(start, end);
			}
		};
	}

	public Map<String, List<WordIndex>> processAll(int noOfThreads)
			throws Exception {
		int count = (int) ((fileLength + chunkSize - 1) / chunkSize);
		java.util.List<Callable<Map<String, List<WordIndex>>>> tasks = new ArrayList<Callable<Map<String, List<WordIndex>>>>(
				count);
		for (int i = 0; i < count; i += Utils.numberLinePerThread)
			tasks.add(processPartTask(i, (i + Utils.numberLinePerThread)));
		ExecutorService es = Executors.newFixedThreadPool(noOfThreads);
		java.util.List<Future<Map<String, List<WordIndex>>>> invokes = es
				.invokeAll(tasks);
		es.shutdown();

		for (Future<Map<String, List<WordIndex>>> result : invokes) {
			Map<String, List<WordIndex>> map = result.get();
		}
		return null;
	}

	public static void main(String argv[]) throws Exception {
		long t1 = Calendar.getInstance().getTimeInMillis();
		QueuePool s = new QueuePool(new File(Utils.fileLocation), 1024);

		Map<String, List<WordIndex>> processAll = s.processAll(8);

		long t2 = Calendar.getInstance().getTimeInMillis();

		System.out.println(String.format(
				"Time = %s ms ; number of words = %s, number of 'it' = %s",
				(t2 - t1), processAll.size(), processAll.get("it").size()));

		List<WordIndex> list = processAll.get("it");

		System.out.println("Data for words 'it'");
		for (WordIndex wordIndex : list) {
			System.out.println(wordIndex.toString());
		}

	}
}