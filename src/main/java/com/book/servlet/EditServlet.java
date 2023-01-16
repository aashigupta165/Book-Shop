package com.book.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String query = "SELECT * FROM book WHERE id = ?;";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get PrintWriter
		PrintWriter printWriter = resp.getWriter();
		// set content type
		resp.setContentType("text/html");
		// get the id of record
		int id = Integer.parseInt(req.getParameter("id"));
		// load JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// generate the connection
		try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book_shop", "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			printWriter.println("<h2 align='center'>Book Update</h2>");
			printWriter.println("<form action='update?id="+id+"' method='post'>");
			printWriter.println("<table align='center'>");
			printWriter.println("<tr>");
			printWriter.println("<td>Book Name</td>");
			printWriter.println("<td><input type='text' name='name' value='"+resultSet.getString(2)+"'></td>");
			printWriter.println("</tr>");
			printWriter.println("<tr>");
			printWriter.println("<td>Book Edition</td>");
			printWriter.println("<td><input type='text' name='edition' value='"+resultSet.getString(3)+"'></td>");
			printWriter.println("</tr>");
			printWriter.println("<tr>");
			printWriter.println("<td>Book Price</td>");
			printWriter.println("<td><input type='text' name='price' value='"+resultSet.getFloat(4)+"'></td>");
			printWriter.println("</tr>");
			printWriter.println("<td><input type='submit' value='edit'></td>");
			printWriter.println("<td><input type='reset' value='cancel'></td>");
			printWriter.println("</tr>");
			printWriter.println("</table>");
			printWriter.println("</form>");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			printWriter.println("<h1>"+e.getMessage()+"</h1>");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			printWriter.println("<h1>"+e.getMessage()+"</h1>");
		}
		printWriter.println("<a href=\"list\">Book List</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
