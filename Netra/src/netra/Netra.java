/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra;

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
//        analyzeLiveTweets();
        retrieveTweetsToFile();
//        analyzeTweetsFromFile();
//        mergeFiles();
//        readCountryCodes();
    }
    
    public static void analyzeTweetsFromFile(){
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
    }
    
    public static void analyzeLiveTweets(){
        String searchQuery = JOptionPane.showInputDialog("Enter your twitter search query");
        ArrayList<TwitterDatum> tweets = TwitterDataSource.getInstance().searchDatums(searchQuery);
        GeoDistAnalyzer analyser = new GeoDistAnalyzer();
        analyser.analyze(tweets);
        for(String key : analyser.getAnalysis().keySet()){
            System.out.println("Country: " + key + ", Tweets: " + analyser.getAnalysis().get(key).size());
        }
    }
    
    public static void retrieveTweetsToFile(){
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
    }
    
    private static void readCountryCodes() {
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
    }
    
}
