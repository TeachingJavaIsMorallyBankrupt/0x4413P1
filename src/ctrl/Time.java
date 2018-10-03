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
 * Servlet implementation class Time
 */
@WebServlet("/Time.do")
public class Time extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Time()
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
		if (request.getParameter("calc") == null)
		{
			this.getServletContext().getRequestDispatcher("/Time.html").forward(request, response);
		} else
		{
			try
			{
				String time = Brain.doTime();
				ServletUtils.setAsHtml(response);
				Writer out = response.getWriter();
				String html = "<p>Server Time: " + time + "</p>";
				out.write(ServletUtils.fillHtml(html));
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
