package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Brain;

/**
 * Servlet implementation class Http
 */
@WebServlet("/Http.do")
public class Http extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Http()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String country = request.getParameter("country");
		String query = request.getParameter("query");
		if(country == null || query == null || !(query.equals("pop") || query.equals("capital"))) {
			this.getServletContext().getRequestDispatcher("/Http.html").forward(request, response);
		} else {
			try {
				String resp = Brain.doHttp(country,  query);
				ServletUtils.setAsHtml(response);
				Writer out = response.getWriter();
				out.write(ServletUtils.fillHtml(resp));
			} catch (Exception e) {
				ServletUtils.onError(response, e);
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
		doGet(request, response);
	}

}
