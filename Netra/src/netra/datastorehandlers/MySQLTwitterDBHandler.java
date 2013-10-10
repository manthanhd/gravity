/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.datastorehandlers;

import netra.helpers.TwitterDatum;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manthanhd
 */
public class MySQLTwitterDBHandler extends TwitterDBHandler {

    MySQLDBConnector connector;
    private Logger logger;
    
    public MySQLTwitterDBHandler(String hostname, String dbName, String username, String password) {
        connector = new MySQLDBConnector(hostname, dbName, username, password);
        logger = Logger.getLogger(MySQLTwitterDBHandler.class.getName());
    }
    
    @Override
    public void insert(TwitterDatum datum, String tablename) {
        try{
            logger.log(Level.INFO, "Establishing connection...");
            connector.connect();
            logger.log(Level.INFO, "Connection established.\nPreparing statement...");
            String query = "INSERT INTO " + tablename + " (username, data, importance, retweet_count, is_retweet, date_posted, country_name) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmt = connector.getConnection().prepareStatement(query);
            Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());  // This is java.sql.Date
//            stmt.setString(1, tablename);
            stmt.setString(1, datum.getUsername());
            stmt.setString(2,datum.getData());
            stmt.setDouble(3, datum.getImportance());
            stmt.setLong(4, datum.getRetweetCount());
            stmt.setBoolean(5, datum.isRetweet());
            stmt.setDate(6, currentDate);
            stmt.setString(7, datum.getCountryName());
            logger.log(Level.INFO, "Done. Executing query...");
            stmt.executeUpdate();
            logger.log(Level.INFO, "Query executed.");
            stmt.close();
        } catch (Exception ex){
            Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.disconnect();
                logger.log(Level.INFO, "Disconnect successful.");
            } catch (SQLException ex) {
                Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public TwitterDatum[] retrieveNew(String tablename) {
        ArrayList<TwitterDatum> datums = new ArrayList<>();
        try{
            logger.log(Level.INFO, "Establishing connection...");
            connector.connect();
            logger.log(Level.INFO, "Connection established.\nPreparing statement...");
            String query = "SELECT * FROM " + tablename + " ORDER BY date_posted DESC;";
            PreparedStatement st = connector.getConnection().prepareStatement(query);
//            st.setString(1, tablename);
            logger.log(Level.INFO, "Done. Executing query...");
            ResultSet res = st.executeQuery();
            logger.log(Level.INFO, "Query executed. Gathering results...");
            while(res.next()){
                String data, userName, country_name;
                boolean isRetweet;
                long retweetCount;
                double importance;
                Date datePosted;
                
                data = res.getString("data");
                userName = res.getString("username");
                country_name = res.getString("country_name");
                isRetweet = res.getBoolean("is_retweet");
                retweetCount = res.getLong("retweet_count");
                importance = res.getDouble("importance");
                datePosted = res.getDate("date_posted");
                datums.add(new TwitterDatum(data, userName, country_name, isRetweet, retweetCount, importance, datePosted));
            }
            logger.log(Level.INFO, "Results gathered: {0}", datums.size());
            res.close();
            st.close();
        } catch (Exception ex){
            Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.disconnect();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(datums.isEmpty()){
            return null;
        } else {
            return datums.toArray(new TwitterDatum[datums.size()]);
        }
    }

    @Override
    public TwitterDatum[] retrieveWithKeyword(String condition, String tablename) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TwitterDatum[] retrieveAll(String tablename) {
        ArrayList<TwitterDatum> datums = new ArrayList<>();
        try{
            logger.log(Level.INFO, "Establishing connection...");
            connector.connect();
            logger.log(Level.INFO, "Connection established.\nPreparing statement...");
            String query = "SELECT * FROM " + tablename + ";";
            PreparedStatement st = connector.getConnection().prepareStatement(query);
//            st.setString(1, tablename);
            logger.log(Level.INFO, "Done. Executing query...");
            ResultSet res = st.executeQuery();
            logger.log(Level.INFO, "Query executed. Gathering results...");
            while(res.next()){
                String data, userName, country_name;
                boolean isRetweet;
                long retweetCount;
                double importance;
                Date datePosted;
                
                data = res.getString("data");
                userName = res.getString("username");
                country_name = res.getString("country_name");
                isRetweet = res.getBoolean("is_retweet");
                retweetCount = res.getLong("retweet_count");
                importance = res.getDouble("importance");
                datePosted = res.getDate("date_posted");
                datums.add(new TwitterDatum(data, userName, country_name, isRetweet, retweetCount, importance, datePosted));
            }
            logger.log(Level.INFO, "Results gathered: {0}", datums.size());
            res.close();
            st.close();
        } catch (Exception ex){
            Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.disconnect();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(datums.isEmpty()){
            return null;
        } else {
            return datums.toArray(new TwitterDatum[datums.size()]);
        }
    }

    @Override
    public void insert(TwitterDatum[] datums, String tablename) {
        try{
            logger.log(Level.INFO, "Establishing connection...");
            connector.connect();
            logger.log(Level.INFO, "Connection established.\nPreparing statement...");
            for(TwitterDatum datum : datums){
                String query = "INSERT INTO " + tablename + " (username, data, importance, retweet_count, is_retweet, date_posted, country_name) VALUES (?,?,?,?,?,?,?);";
                PreparedStatement stmt = connector.getConnection().prepareStatement(query);
                Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());  // This is java.sql.Date
//                stmt.setString(1, tablename);
                stmt.setString(1, datum.getUsername());
                stmt.setString(2,datum.getData());
                stmt.setDouble(3, datum.getImportance());
                stmt.setLong(4, datum.getRetweetCount());
                stmt.setBoolean(5, datum.isRetweet());
                stmt.setDate(6, currentDate);
                stmt.setString(7, datum.getCountryName());
                logger.log(Level.INFO, "Done. Executing query {0}...", query);
                stmt.executeUpdate();
                logger.log(Level.INFO, "Query executed.");
                stmt.close();
            }
        } catch (Exception ex){
            Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connector.disconnect();
                logger.log(Level.INFO, "Disconnect successful.");
            } catch (SQLException ex) {
                Logger.getLogger(MySQLTwitterDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
}
