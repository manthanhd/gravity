/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra;

import DataStoreHandlers.MySQLTwitterDBHandler;
import analyzers.GeoDistAnalyzer;
import datasources.TwitterDataSource;
import helpers.Country;
import helpers.CountryCodeMap;
import helpers.TwitterDatum;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author manthanhd
 */
public class Netra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Welcome to Netra...");
//        analyzeLiveTweets();
//        retrieveTweetsToFile();
//        geoAnalyzeTweetsFromFile();
//        mergeFiles();
//        readCountryCodes();
//        retrieveTweetsToDatabase() ;
//        pushTweetsFromFileToDatabase();
        retrieveTweetsFromDatabase();
        System.out.println("Bye bye!");
    }
    
    public static void geoAnalyzeTweetsFromFile(){
        System.out.println("Will analyse tweets from file...");
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            ArrayList<TwitterDatum> statuses = TwitterDataSource.getInstance().readFromFile(chooser.getSelectedFile().getAbsolutePath());
            
            GeoDistAnalyzer analyser = new GeoDistAnalyzer();
            analyser.analyze(statuses);
            for(String key : analyser.getAnalysis().keySet()){
                System.out.println("Country: " + key + ", Tweets: " + analyser.getAnalysis().get(key).size());
            }
        }
        System.out.println("Done");
    }
    
    public static void pushTweetsFromFileToDatabase(){
        System.out.println("Will analyse tweets from file...");
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            ArrayList<TwitterDatum> statuses = TwitterDataSource.getInstance().readFromFile(chooser.getSelectedFile().getAbsolutePath());
            String hostname = "mysql.cms.gre.ac.uk", dbname="mdb_dm014", username="dm014";
            String password = JOptionPane.showInputDialog("Please input password for " + username + ":");
            String tablename = "netra_twitterdb_incoming";
            MySQLTwitterDBHandler handler = new MySQLTwitterDBHandler(hostname, dbname, username, password);
            for(TwitterDatum datum : statuses){
                handler.insert(datum, tablename);
            }
        }
        System.out.println("Done");
    }
    
    public static void analyzeLiveTweets(){
        System.out.println("Will analyse live tweets...");
        String searchQuery = JOptionPane.showInputDialog("Enter your twitter search query");
        ArrayList<TwitterDatum> tweets = TwitterDataSource.getInstance().searchDatums(searchQuery);
        GeoDistAnalyzer analyser = new GeoDistAnalyzer();
        analyser.analyze(tweets);
        for(String key : analyser.getAnalysis().keySet()){
            System.out.println("Country: " + key + ", Tweets: " + analyser.getAnalysis().get(key).size());
        }
        System.out.println("Done.");
    }
    
    public static void retrieveTweetsToFile(){
        System.out.println("Will retrieve tweets to a file...");
        String searchQuery = JOptionPane.showInputDialog("Enter your twitter search query");
        if(searchQuery == null /*|| searchQuery.equals("")*/){
            System.out.println("Cancelled!");
            return;
        }
        ArrayList<TwitterDatum> tweets = TwitterDataSource.getInstance().searchDatums(searchQuery);
        System.out.println("Tweets acquired: " + tweets.size());
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            String filename = chooser.getSelectedFile().getAbsolutePath();
            TwitterDataSource.getInstance().dumpToFile(filename, tweets);
            System.out.println("Done. Dumped to " + filename);
        } else {
            System.out.println("Process Cancelled.");
        }
        System.out.println("Done.");
    }
    
    public static void retrieveTweetsFromDatabase(){
//        MySQLTwitterDBHandler handler = new MySQLTwitterDBHandler(null, null, null, null)
        System.out.println("Will retrieve tweets to a file...");
//        String searchQuery = JOptionPane.showInputDialog("Enter your twitter search query");
//        if(searchQuery == null /*|| searchQuery.equals("")*/){
//            System.out.println("Cancelled!");
//            return;
//        }
//        ArrayList<TwitterDatum> tweets = TwitterDataSource.getInstance().searchDatums(searchQuery);
//        System.out.println("Tweets acquired: " + tweets.size());
        String tablename = "netra_twitterdb_incoming";
        String username = "dm014";
        String password = JOptionPane.showInputDialog("Enter password for " + username + ":");
        String hostname = "mysql.cms.gre.ac.uk";
        if(password != null){
//            MySQLTwitterDBHandler handler = new MySQLTwitterDBHandler("mysql.cms.gre.ac.uk", "mdb_dm014", username, password);
//            for(TwitterDatum datum : tweets){
//                handler.insert(datum, tablename);
//            }
            MySQLTwitterDBHandler handler = new MySQLTwitterDBHandler(hostname, "mdb_dm014", username, password);
            TwitterDatum[] tweets = handler.retrieveAll(tablename);
            System.out.println("Retrieved and resurrected: " + tweets.length); 
        }else{
            System.out.println("Cancelled!");
        }
        System.out.println("Done.");
    }
    
    public static void retrieveTweetsToDatabase(){
        System.out.println("Will retrieve tweets to a file...");
        String searchQuery = JOptionPane.showInputDialog("Enter your twitter search query");
        if(searchQuery == null /*|| searchQuery.equals("")*/){
            System.out.println("Cancelled!");
            return;
        }
        ArrayList<TwitterDatum> tweets = TwitterDataSource.getInstance().searchDatums(searchQuery);
        System.out.println("Tweets acquired: " + tweets.size());
        String tablename = "netra_twitterdb_incoming";
        String username = "dm014";
        String password = JOptionPane.showInputDialog("Enter password for " + username + ":");
        if(password != null){
            MySQLTwitterDBHandler handler = new MySQLTwitterDBHandler("mysql.cms.gre.ac.uk", "mdb_dm014", username, password);
            for(TwitterDatum datum : tweets){
                handler.insert(datum, tablename);
            }
        }else{
            System.out.println("Cancelled!");
        }
        System.out.println("Done.");
    }
    

    private static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }
    
    private static void mergeFiles(){
        System.out.println("Will merge files...");
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File file1 = chooser.getSelectedFile();
            File file2 = null;
            int res2 = chooser.showOpenDialog(null);
            if(res2 == JFileChooser.APPROVE_OPTION){
                file2 = chooser.getSelectedFile();
            }
            
            if(file1 != null && file2 != null){
                TwitterDataSource.getInstance().mergeFiles(file1.getAbsolutePath(), file2.getAbsolutePath());
            }else{
                System.out.println("Process cancelled!");
            }
        } else {
            System.out.println("Process cancelled.");
        }
        System.out.println("Done.");
    }
    
    private static void readCountryCodes() {
        System.out.println("Will read country codes...");
        try {
            CountryCodeMap ccm = new CountryCodeMap();
    //        ccm.startParse();
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader reader = saxParser.getXMLReader();
            reader.setContentHandler(ccm);
            try {
                reader.parse(convertToFileURL("countryInfo.xml"));
            } catch (IOException ex) {
                Logger.getLogger(Netra.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("Found: " + ccm.map.keySet().size());
            for(String key : ccm.map.keySet()){
                Country country = ccm.map.get(key);
                System.out.println(key + " : " + country.name);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Netra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Netra.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done.");
    }
    
}
