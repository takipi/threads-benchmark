package controllers.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ServletUtilities;

@WebServlet("/exception/trade")
public class ExpStackTrace extends HttpServlet {
	private static final long serialVersionUID = -2076640996856662490L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletUtilities.testException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\n" + "<html>\n"
				+ "<head><title>A Test Servlet</title></head>\n"
				+ "<body bgcolor=\"#fdf5e6\">\n" + "<h1>Test</h1>\n"
				+ "<p>Simple servlet for testing.</p>\n" + "</body></html>");
	}
}
