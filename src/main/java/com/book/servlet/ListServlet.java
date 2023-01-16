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
 * Servlet implementation class ListServlet
 */
@WebServlet("/list")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String query = "SELECT * FROM book;";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// get PrintWriter
		PrintWriter printWriter = resp.getWriter();
		// set content type
		resp.setContentType("text/html");
		// load JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// generate the connection
		try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book_shop", "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet resultset = preparedStatement.executeQuery();
			printWriter.println("<h2 align='center'>Book List</h2>");
			printWriter.println("<table border='1' align='center'>");
			printWriter.println("<tr>");
			printWriter.println("<th>Bood Id</th>");
			printWriter.println("<th>Bood Name</th>");
			printWriter.println("<th>Bood Edition</th>");
			printWriter.println("<th>Bood Price</th>");
			printWriter.println("<th>Edit</th>");
			printWriter.println("<th>Delete</th>");
			printWriter.println("</tr>");
			while (resultset.next()) {
				printWriter.println("<tr>");
				printWriter.println("<td>"+resultset.getInt(1)+"</td>");
				printWriter.println("<td>"+resultset.getString(2)+"</td>");
				printWriter.println("<td>"+resultset.getString(3)+"</td>");
				printWriter.println("<td>"+resultset.getFloat(4)+"</td>");
				printWriter.println("<td><a href = 'edit?id="+resultset.getInt(1)+"'>Edit</a></td>");
				printWriter.println("<td><a href = 'delete?id="+resultset.getInt(1)+"'>Delete</a></td>");
				printWriter.println("</tr>");
			}
			printWriter.println("</table>");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			printWriter.println("<h1>"+e.getMessage()+"</h1>");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			printWriter.println("<h1>"+e.getMessage()+"</h1>");
		}
		printWriter.println("<a href=\"home.html\">Home</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
