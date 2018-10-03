package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

public class ServletUtils
{

	public static void onError(HttpServletResponse response, Exception e) throws IOException {
		response.setContentType("text/html");
		Writer out = response.getWriter();
		String html = "<html><body>";
		html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		html += "<p>Error " + e.getMessage() + "</p>";
		out.write(html);
	}
	
	public static void onError(HttpServletResponse response, String e) throws IOException {
		response.setContentType("text/html");
		Writer out = response.getWriter();
		String html = "<html><body>";
		html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		html += "<p>Error " + e + "</p>";
		out.write(html);
	}
	
	public static String fillHtml(String inner) {
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>");
		builder.append("<p><a href='Dash.do'>Back to Dashboard</a></p>");
		builder.append(inner);
		builder.append("</body></html>");
		return builder.toString();
	}
	
	public static void setAsHtml(HttpServletResponse r) {
		r.setContentType("text/html");
	}
}