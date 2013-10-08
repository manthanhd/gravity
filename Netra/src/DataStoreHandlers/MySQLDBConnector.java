/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStoreHandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manthanhd
 */
public class MySQLDBConnector implements DBConnector, AutoCloseable {

    private String hostname, dbName, username, password;
    private Connection conn;
//    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String driver = "com.mysql.jdbc.Driver";
    
    public MySQLDBConnector(String hostname, String dbName, String username, String password) {
        this.hostname = hostname;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }    
    
    @Override
    public void connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Class.forName(driver).newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + username + "&password=" + password);
    }

    @Override
    public void disconnect() throws SQLException{
        conn.close();
    }

    @Override
    public void close() throws Exception {
        disconnect();
    }

    @Override
    public Connection getConnection() {
        return conn;
    }
    
    
    
}
