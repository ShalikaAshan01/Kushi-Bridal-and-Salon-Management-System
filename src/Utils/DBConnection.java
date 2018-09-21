/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Shalika Ashan
 */
public class DBConnection {

//    private final static String DBNAME = "SalonManagementSystem.sqlite";
    
    private static Connection connection;
    private final static String DBNAME="kushisalon.db";

    private DBConnection() {
    }

    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        if (connection == null || connection.isClosed()) {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBNAME);
        }
        return connection;
    }
}