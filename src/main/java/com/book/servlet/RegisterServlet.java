package com.book.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String query = "INSERT INTO book(NAME, EDITION, PRICE) VALUES(?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get PrintWriter
		PrintWriter printWriter = resp.getWriter();
		// set content type
		resp.setContentType("text/html");
		// get the book info
		String bookName = req.getParameter("bookName");
		String bookEdition = req.getParameter("bookEdition");
		Float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		// load JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// generate the connection
		try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book_shop", "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, bookName);
			preparedStatement.setString(2, bookEdition);
			preparedStatement.setFloat(3, bookPrice);
			int count = preparedStatement.executeUpdate();
			if (count==1) {
				printWriter.println("<h2>Reords is inserted successfully.</h2>");
			} else {
				printWriter.println("<h2>Reords not inserted successfully.</h2>");
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
