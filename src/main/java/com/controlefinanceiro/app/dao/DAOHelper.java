package com.controlefinanceiro.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;


public class DAOHelper {
    private static final String url = "jdbc:postgresql://postgre:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "password";

    private static Connection connection = null;
    
    public static Connection connect() throws SQLException, ClassNotFoundException {
        if(connection != null && !connection.isClosed()) {
            return connection;
        }
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void disconnect() throws SQLException {
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
