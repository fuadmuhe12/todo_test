package com.todo.todo_test.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
@Getter
@Setter
public class DBConnectionManager {

    private String url = "jdbc:mysql://localhost:3306/TodoDB_test";
    private String username = "root";
    private String password = "fuad"; // Replace with your own password

    private Connection connection;

    public void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL Driver not found!", e);
            }
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
