package forkjoin.prime.example;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {
	private static final long serialVersionUID = -1029734293926194419L;

	private int[] mSource;
	private int mStart;
	private int mLength;
	private int[] mDestination;

	// Processing window size; should be odd.
	private int mBlurWidth = 3;

	public ForkBlur(int[] src, int start, int length, int[] dst) {
		mSource = src;
		mStart = start;
		mLength = length;
		mDestination = dst;
	}

	protected void computeDirectly() {
		int sidePixels = (mBlurWidth - 1) / 2;
		for (int index = mStart; index < mStart + mLength; index++) {
			// Calculate average.
			int total = 0, k = 0;
			;
			for (int mi = -sidePixels; mi <= sidePixels; mi++) {
				k++;
				int mindex = Math.min(Math.max(mi + index, 0),
						mSource.length - 1);
				int pixel = mSource[mindex];
				total += pixel;
			}

			// Reassemble destination pixel.
			int dpixel = total / k;
			mDestination[index] = dpixel;
		}
	}

	protected static int sThreshold = 5;

	protected void compute() {
		if (mLength < sThreshold) {
			computeDirectly();
			return;
		}
		int split = mLength / 2;

		invokeAll(new ForkBlur(mSource, mStart, split, mDestination),
				new ForkBlur(mSource, mStart + split, mLength - split,
						mDestination));
	}

	public static void main(String[] args) {
		Random tmp = new Random();
		int size = 4000000;
		int[] src = new int[size];
		StringBuffer sSrc = new StringBuffer();
		StringBuffer sDsc = new StringBuffer();
		for (int i = 0; i < size; i++) {
			int k = tmp.nextInt(100);
			src[i] = k;
			sSrc.append(k + ",");
		}
		int[] dst = new int[src.length];
		ForkBlur fb = new ForkBlur(src, 0, src.length, dst);

		ForkJoinPool pool = new ForkJoinPool();

		int parallelism = pool.getParallelism();
		System.out.println("Number of " + parallelism);

		long t1 = Calendar.getInstance().getTimeInMillis();
		pool.invoke(fb);
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println("Time " + (t2 - t1));
		for (int i = 0; i < size; i++) {
			sDsc.append(dst[i] + ",");
		}
		// System.out.println(sSrc);
		// System.out.println(sDsc);

	}
}