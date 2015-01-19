package services;

import javax.servlet.http.HttpServletRequest;

/** Some simple time savers. Static methods. */

public class ServletUtilities {
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

	public static String headWithTitle(String title) {
		return ("<!DOCTYPE html>\n" + "<html>\n" + "<head><title>" + title + "</title></head>\n");
	}

	/**
	 * Read a parameter with the specified name, convert it to an int, and
	 * return it. Return the designated default value if the parameter doesn't
	 * exist or if it is an illegal integer format.
	 */

	public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
		String paramString = request.getParameter(paramName);
		int paramValue;
		try {
			paramValue = Integer.parseInt(paramString);
		} catch (Exception nfe) { // null or bad format
			paramValue = defaultValue;
		}
		return (paramValue);
	}

	/** Reads param and converts to double. Default if problem. */

	public static double getDoubleParameter(HttpServletRequest request, String paramName, double defaultValue) {
		String paramString = request.getParameter(paramName);
		double paramValue;
		try {
			paramValue = Double.parseDouble(paramString);
		} catch (Exception nfe) { // null or bad format
			paramValue = defaultValue;
		}
		return (paramValue);
	}

/** Replaces characters that have special HTML meanings
   *  with their corresponding HTML character entities.
   *  Specifically, given a string, this method replaces all 
   *  occurrences of  
   *  {@literal
   *  '<' with '&lt;', all occurrences of '>' with
   *  '&gt;', and (to handle cases that occur inside attribute
   *  values), all occurrences of double quotes with
   *  '&quot;' and all occurrences of '&' with '&amp;'.
   *  Without such filtering, an arbitrary string
   *  could not safely be inserted in a Web page.
   *  }
   */

	public static String filter(String input) {
		if (!hasSpecialChars(input)) {
			return (input);
		}
		StringBuilder filtered = new StringBuilder(input.length());
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	private static boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i < input.length(); i++) {
				c = input.charAt(i);
				switch (c) {
				case '<':
					flag = true;
					break;
				case '>':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return (flag);
	}

	private ServletUtilities() {
	} // Uninstantiatable class
}
