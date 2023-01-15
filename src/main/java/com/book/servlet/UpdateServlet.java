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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String query = "UPDATE book SET NAME = ?, EDITION = ?, PRICE = ? WHERE ID = ?;";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateServlet() {
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
		// get the edit data we want to edit
		String name = req.getParameter("name");
		String edition = req.getParameter("edition");
		Float price = Float.parseFloat(req.getParameter("price"));
		// load JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// generate the connection
		try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book_shop", "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, edition);
			preparedStatement.setFloat(3, price);
			preparedStatement.setInt(4, id);
			int count = preparedStatement.executeUpdate();
			if (count==1) {
				printWriter.println("<h2>Reords is updated successfully.</h2>");
			}else {
				printWriter.println("<h2>Reords is not updated successfully.</h2>");
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
