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
import java.sql.SQLException;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String query = "DELETE FROM book WHERE ID = ?;";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteServlet() {
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
			preparedStatement.setInt(1, Integer.parseInt(req.getParameter("id")));
			int count = preparedStatement.executeUpdate();
			if (count==1) {
				printWriter.println("<h2>Reords is deleted successfully.</h2>");
			} else {
				printWriter.println("<h2>Reords is not deleted successfully.</h2>");
			}
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
		printWriter.println("<br>");
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
