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
 * Servlet implementation class Db
 */
@WebServlet("/Db.do")
public class Db extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Db()
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
		String itemno = request.getParameter("itemno");
		if (itemno == null)
		{
			this.getServletContext().getRequestDispatcher("/Db.html").forward(request, response);
		} else
		{
			try
			{
				String outStr = Brain.doDB(itemno);
				ServletUtils.setAsHtml(response);
				Writer out = response.getWriter();
				out.write(ServletUtils.fillHtml(outStr));
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
