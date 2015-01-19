package services;


/** Some simple time savers. Static methods. */

public class ExceptionTest {
	static int k = 0;

	public static void testException() throws Exception {
		System.out.println(k++);
		test1();
	}

	public static void test1() throws Exception {
		try {
			test2();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	public static void test2() throws InterruptedException {
		try {
			test3();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new InterruptedException();
		}
	}

	public static void test3() throws ClassNotFoundException {
		try {
			test4();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}

	public static void test4() throws NoSuchFieldException {
		throw new NoSuchFieldException();
	}
}
