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
 * Servlet implementation class Roster
 */
@WebServlet("/Roster.do")
public class Roster extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Roster()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String course = request.getParameter("course");
		if (course == null)
		{
			this.getServletContext().getRequestDispatcher("/Roster.html").forward(request, response);
		} else
		{
			try
			{
				String table = Brain.doRoster(course);
				ServletUtils.setAsHtml(response);
				Writer out = response.getWriter();
				out.write(ServletUtils.fillHtml(table));
			} catch (Exception e)
			{
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
