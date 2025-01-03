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

@WebServlet(name = "DeteletTasksServlet", urlPatterns = { "/deleteTask" })
public class DeteletTasksServlet extends HttpServlet {

    private final DBConnectionManager dbManager;

    public DeteletTasksServlet(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            String sql = "DELETE FROM Tasks WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id));

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                out.println("<h2>Task with ID " + id + " was deleted successfully</h2>");
            } else {
                out.println("<h2>Task with ID " + id + " was not found</h2>");
            }

            statement.close();

        } catch (SQLException e) {
            throw new ServletException("Error deleting task", e);
        } finally {
            try {
                dbManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


}