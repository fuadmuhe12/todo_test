package com.todo.todo_test.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

import com.todo.todo_test.db.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "TaskRegistrationServlet", urlPatterns = { "/createTask" })
public class TaskRegistrationServlet extends HttpServlet {

    private DBConnectionManager dbManager;

    @Override
    public void init() throws ServletException {
        super.init();
        dbManager = new DBConnectionManager();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String dueDateStr = request.getParameter("duedate");

        Date dueDate = null;
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            dueDate = Date.valueOf(dueDateStr);
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            String sql = "INSERT INTO Tasks (description, status, duedate) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, description);
            statement.setString(2, status);
            statement.setDate(3, dueDate);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<html><body>");
                out.println("<h3>Task created successfully!</h3>");
                out.println("<p><a href=\"index.html\">Go Back</a></p>");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>Error creating task: " + e.getMessage() + "</h3>");
            out.println("<p><a href=\"index.html\">Go Back</a></p>");
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
