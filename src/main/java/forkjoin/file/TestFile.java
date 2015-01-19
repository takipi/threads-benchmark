package forkjoin.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ForkJoinPool;

import forkjoin.Utils;

public class TestFile {
	public static void testFile(int request, long lineNum) throws Exception{
		Utils.lineNum = lineNum;
		for (int i = 0; i < 10; i++) {
			switch (request) {
			case 1:
				testForkJoin();
				Thread.sleep(2000);
				break;
			case 2:
				testParalleStreaming();
				Thread.sleep(2000);
				break;
			case 3:
				testLocalQueue();
				Thread.sleep(2000);
				break;
			case 4:
				testSingleThread();
				Thread.sleep(2000);
				break;

			}
		}
		
	}

	private static void testSingleThread() throws Exception {
		long t1 = Calendar.getInstance().getTimeInMillis();
		SerializeThread mc = new SerializeThread();
		mc.process();
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ", (t2 - t1)));
		
	}

	private static void testLocalQueue() throws Exception {
		long t1 = Calendar.getInstance().getTimeInMillis();
		QueuePool s = new QueuePool(new File(Utils.fileLocation), 1024);
		s.processAll(8);
		long t2 = Calendar.getInstance().getTimeInMillis();		
		System.out.println(String.format("Time = %s ms ", (t2 - t1)));		
	}

	private static void testParalleStreaming() {
		long t1 = Calendar.getInstance().getTimeInMillis();
		long countLineNumber = Utils.countLineNumber(Utils.fileLocation);
		ParallelStream mc = new ParallelStream(countLineNumber);
		mc.process();
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ", (t2 - t1)));
	}

	private static void testForkJoin() {
		long t1 = Calendar.getInstance().getTimeInMillis();
		long countLineNumber = Utils.countLineNumber(Utils.fileLocation);
		PureForkJoin fb = new PureForkJoin(0L, countLineNumber - 1);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(fb);
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ", (t2 - t1)));
	}

	public static void createTestFile() {
		try {
			String data = "The Project Gutenberg EBook of The Outline of Science. This eBook is for the use of anyone anywhere at no cost and with almost no restrictions whatsoever.  You may copy it, give it away or re-use it under the terms of the Project Gutenberg License included with this eBook or online at www.gutenberg.org. Was it not the great philosopher and mathematician Leibnitz who said that the more knowledge advances the more it becomes possible to condense it into little books? Now this \"Outline of Science\" is certainly not a little book, and yet it illustrates part of the meaning of Leibnitz's wise saying. For here within reasonable compass there is a library of little books--an outline of many sciences. What then is the aim of this book? It is to give the intelligent student-citizen, otherwise called \"the man in the street,\" a bunch of intellectual keys by which to open doors which have been hitherto shut to him, partly because he got no glimpse of the treasures behind the doors, and partly because the portals were win\n";
			File file = new File("/tmp/test.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			long lineNum = Utils.countLineNumber(Utils.fileLocation);
			FileWriter fileWritter = new FileWriter("/tmp/test.txt", false);
			BufferedWriter bw = new BufferedWriter(fileWritter);
			for (long i = 0L; i < lineNum; i++) {
				bw.write(data);
			}
			bw.close();
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
