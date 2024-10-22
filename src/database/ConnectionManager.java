package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static String URL = "jdbc:sqlite:controle_finaceiro.db";

    private static Connection con;

    private ConnectionManager(){

    }

    public static Connection getConnection(){

        try {
            if(con==null || con.isClosed()){
                con = DriverManager.getConnection(URL);
            }
            System.out.println(con);
            return con;
        } catch (SQLException ex) {
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if(con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            return;
        }
    }
}
