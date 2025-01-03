package com.todo.todo_test.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.todo.todo_test.db.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet(name = "TaskRegistrationServlet", urlPatterns = { "/createTask" })
public class TaskRegistrationServlet extends HttpServlet {

    @Autowired
    private DBConnectionManager dbManager;

    @Override
    public void init() throws ServletException {
        super.init();
        // Enable Spring's dependency injection in this servlet
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String dueDateStr = request.getParameter("duedate"); // Ensure consistency with form

        Date dueDate = null;
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            try {
                dueDate = Date.valueOf(dueDateStr);
            } catch (IllegalArgumentException e) {
                // Handle invalid date format
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
                return;
            }
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
