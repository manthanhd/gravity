/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.datastorehandlers;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author manthanhd
 */
public interface DBConnector {
    
    public void connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException;
    
    public void disconnect() throws SQLException;
    
    public Connection getConnection();
}
