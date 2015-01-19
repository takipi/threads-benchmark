package forkjoin.file.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import forkjoin.Utils;

public class Split {
	private File file;

	public Split(File file) {
		this.file = file;
	}

	public String processPart(long start, long end) throws Exception {
		InputStream is = new FileInputStream(Utils.fileLocation);
		is.skip(start);
		System.out.println("Computing the part from " + start + " to " + end);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str = "";
		while ((str = reader.readLine()) != null) {
			System.out.println(str);
		}

		Thread.sleep(1000);
		System.out.println("Finished the part from " + start + " to " + end);

		is.close();
		return "Some result";
	}

	public Callable<String> processPartTask(final long start, final long end) {
		return new Callable<String>() {
			public String call() throws Exception {
				return processPart(start, end);
			}
		};
	}

	public void processAll(int noOfThreads, int chunkSize) throws Exception {	
		int count = (int) ((file.length() + chunkSize - 1) / chunkSize);	
		java.util.List<Callable<String>> tasks = new ArrayList<Callable<String>>(count);
		for (int i = 0; i < count; i++)
			tasks.add(processPartTask(i * chunkSize, Math.min(file.length(), (i + 1) * chunkSize)));	
		ExecutorService es = Executors.newFixedThreadPool(noOfThreads);
		java.util.List<Future<String>> results = es.invokeAll(tasks);
		es.shutdown();

		// use the results for something
		for (Future<String> result : results)
			System.out.println(result.get());
	}

	public static void main(String argv[]) throws Exception {
		Split s = new Split(new File(Utils.fileLocation));
		s.processAll(8, 1024);
	}
}