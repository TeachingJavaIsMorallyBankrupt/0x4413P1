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
 * Servlet implementation class Prime
 */
@WebServlet("/Prime.do")
public class Prime extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Prime()
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
		String digitString = request.getParameter("digits");
		if (digitString == null)
		{
			this.getServletContext().getRequestDispatcher("/Prime.html").forward(request, response);
		} else
		{
			try
			{
				int digits = Integer.parseInt(digitString);
				ServletUtils.setAsHtml(response);
				Writer out = response.getWriter();
				out.write(ServletUtils.fillHtml("Prime: " + Brain.doPrime(digits)));
			} catch (Exception e)
			{
				ServletUtils.onError(response, "Error with input string '" + digitString + "'");
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
