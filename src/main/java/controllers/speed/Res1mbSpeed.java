package controllers.speed;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@WebServlet(urlPatterns = "/speed/res1mb", loadOnStartup = 1)
public class Res1mbSpeed extends HttpServlet {
	private static final long serialVersionUID = -5152808640821687260L;

	public byte[] retData;

	public void init() {
		InputStream in;
		try {
			in = new BufferedInputStream(new URL("http",
					"download.thinkbroadband.com", "/1MB.zip").openStream());
			retData = IOUtils.toByteArray(in);
			System.out.println("Get Data OK");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(retData);
	}
}
