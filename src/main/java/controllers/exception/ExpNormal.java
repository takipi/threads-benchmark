package controllers.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/exception/normal")
public class ExpNormal extends HttpServlet {
	private static final long serialVersionUID = -3237172798612997391L;

	private static Random random = new Random();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int rand = random.nextInt();
		int k = rand % 14;
		switch (k) {
		case 0:
			throw new ArithmeticException();
		case 1:
			throw new ArrayIndexOutOfBoundsException();
		case 2:
			throw new ArrayStoreException();
		case 3:
			throw new ClassCastException();
		case 4:
			throw new IllegalArgumentException();
		case 5:
			throw new IllegalMonitorStateException();
		case 6:
			throw new IllegalStateException();
		case 7:
			throw new IllegalThreadStateException();
		case 8:
			throw new IndexOutOfBoundsException();
		case 9:
			throw new NegativeArraySizeException();
		case 10:
			throw new NullPointerException();
		case 11:
			throw new NumberFormatException();
		case 12:
			throw new SecurityException();
		case 13:
			throw new UnsupportedOperationException();
		}

		PrintWriter out = response.getWriter();
		out.println("Hello World");
	}
}
