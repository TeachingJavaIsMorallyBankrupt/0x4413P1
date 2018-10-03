package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OAuth
 */
@WebServlet("/OAuth.do")
public class OAuth extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static String OAUTH_URL = "https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OAuth()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String user = request.getParameter("user");
		String name = request.getParameter("name");
		String hash = request.getParameter("hash");
		if (user == null || name == null || hash == null)
		{
			this.getServletContext().getRequestDispatcher("/OAuth.html").forward(request, response);
		} else
		{
			try
			{
				String html = "<p><b>Authentication Result</b></p>";
				html += "<p>user=" + user + "&name=" + name + "&hash=" + hash + "</p>";
				ServletUtils.setAsHtml(response);
				Writer out = response.getWriter();
				out.write(ServletUtils.fillHtml(html));
			} catch (Exception e)
			{

			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		response.sendRedirect(OAUTH_URL + "?back=http://localhost:4413/ProjB/OAuth.do");
	}

}
