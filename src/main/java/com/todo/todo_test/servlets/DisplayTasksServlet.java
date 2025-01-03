package com.todo.todo_test.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import com.todo.todo_test.db.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DisplayTasksServlet", urlPatterns = { "/viewTasks" })
public class DisplayTasksServlet extends HttpServlet {

    private DBConnectionManager dbManager;

    @Override
    public void init() throws ServletException {
        super.init();
        dbManager = new DBConnectionManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>All Tasks</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Description</th><th>Status</th><th>Due Date</th></tr>");

        try {
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            String sql = "SELECT id, description, status, due_date FROM Tasks";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                String status = rs.getString("status");
                Date dueDate = rs.getDate("due_date");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + description + "</td>");
                out.println("<td>" + status + "</td>");
                out.println("<td>" + (dueDate != null ? dueDate : "") + "</td>");
                out.println("</tr>");
            }
            rs.close();
            stmt.close();

            out.println("</table>");
            out.println("<p><a href=\"index.html\">Go Back</a></p>");
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving tasks: " + e.getMessage() + "</p>");
            out.println("</body></html>");
        } finally {
            try {
                dbManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
