package model;

import java.util.Objects;
import java.util.Random;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.Socket;
import java.time.Instant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.net.URLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import roster.Course;
import roster.Students;

public class Brain
{
	public static final int BITS_PER_DIGIT = 3;
	public static final Random RNG = new Random();
	public static final String TCP_SERVER = "red.eecs.yorku.ca";
	public static final int TCP_PORT = 12345;
	public static final String DB_URL = "jdbc:derby://red.eecs.yorku.ca:64413/EECS;user=student;password=secret";
	public static final String HTTP_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/World.cgi";
	public static final String ROSTER_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/Roster.cgi";

	public static String doPrime(int digits)
	{
		return BigInteger.probablePrime(BITS_PER_DIGIT * digits, RNG).toString();
	}
	
	public static String doHttp(String country, String query) throws Exception {
		Objects.requireNonNull(country);
		Objects.requireNonNull(query);
		StringBuilder queryBuilder = new StringBuilder(HTTP_URL);
		queryBuilder.append("?country=" + country);
		queryBuilder.append("&query=" + query);
		URL url = new URL(queryBuilder.toString());
		URLConnection conn  = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String result = in.readLine();
		in.close();
		return result;
	}

	public static String doTCP(int digits) throws IOException
	{
		Socket client = new Socket(TCP_SERVER, TCP_PORT);
		OutputStream out = client.getOutputStream();
		out.write(("prime " + digits + "\n").getBytes("UTF-8"));
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		String res = in.readLine();
		System.out.println("Read string from tcp server: " + res);
		out.close();
		in.close();
		client.close();
		return res;
	}

	public static String doDB(String itemNo) throws Exception
	{
		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
		Connection con = DriverManager.getConnection(DB_URL);
		con.prepareStatement("set schema roumani").executeUpdate();
		PreparedStatement query = con.prepareStatement("SELECT NAME, PRICE FROM ITEM WHERE number = ?");
		query.setString(1, itemNo);
		ResultSet r = query.executeQuery();
		String result = "Item: ";
		if (r.next())
		{
			result += "$" + r.getDouble("PRICE") + " - " + r.getString("NAME");
		} else
		{
			throw new Exception(itemNo + " not found!");
		}
		r.close();
		query.close();
		con.close();
		return result;
	}
	
	public static String doRoster(String course) throws IOException {

		StringBuilder builder = new StringBuilder(ROSTER_URL);
		builder.append("?course=");
		builder.append(course);
		URL url = new URL(builder.toString());
		URLConnection conn = url.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String xml = in.readLine();

		try {
			JAXBContext jc = JAXBContext.newInstance(Course.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			Course c = (Course) unmarshaller.unmarshal(reader);
			return makeTable(c);
		} catch (JAXBException e) {
			return "XML Error";
		} finally {
			in.close();
		}
	}
	
	private static String makeTable(Course c) {
		StringBuilder builder = new StringBuilder("<table border=\"1\">");
		builder.append("<tr><th>Course ID</th><td colspan=\"6\">" + c.getNumber() + "</td></tr>");
		builder.append("<tr><th>Course Status</th><td colspan=\"6\">" + c.getStatus() + "</td></tr>");
		builder.append("<tr><th>Student ID</th>" + "<th>Last Name</th>" + "<th>First Name</th>" + "<th>City</th>"
				+ "<th>Program</th>" + "<th>Hours</th>" + "<th>GPA</th></tr>");

		for (Students student : c.getStudents()) {
			builder.append("<tr><td>" + student.getId() + "</td>");
			builder.append("<td>" + student.getLastName() + "</td>");
			builder.append("<td>" + student.getFirstName() + "</td>");
			builder.append("<td>" + student.getCity() + "</td>");
			builder.append("<td>" + student.getProgram() + "</td>");
			builder.append("<td>" + student.getHours() + "</td>");
			builder.append("<td>" + student.getGpa() + "</td></tr>");
		}
		builder.append("</table>");
		return builder.toString();
	}

	private Brain()
	{
	}

	public static String doTime()
	{
		return Instant.now().toString();
	}

}
